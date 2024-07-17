package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.model.Perfil;
import com.aluracursos.forohub.model.Usuario;
import com.aluracursos.forohub.payload.request.LoginRequest;
import com.aluracursos.forohub.payload.request.SignupRequest;
import com.aluracursos.forohub.payload.response.JwtResponse;
import com.aluracursos.forohub.payload.response.MessageResponse;
import com.aluracursos.forohub.repository.PerfilRepository;
import com.aluracursos.forohub.repository.UsuarioRepository;
import com.aluracursos.forohub.security.jwt.JwtUtils;
import com.aluracursos.forohub.service.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PerfilRepository perfilRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getCorreoElectronico(), loginRequest.getContrasena()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (usuarioRepository.existsByCorreoElectronico(signUpRequest.getCorreoElectronico())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: El correo electrónico ya está en uso!"));
        }

        List<Perfil> perfiles = new ArrayList<>();
        // Crear nuevo usuario
        Usuario usuario = new Usuario(signUpRequest.getNombre(),
                signUpRequest.getCorreoElectronico(),
                encoder.encode(signUpRequest.getContrasena()));
        usuario.setPerfiles(perfiles); // Asignar perfiles después de crear el usuario


        Set<String> strRoles = signUpRequest.getRoles();
        //Set<Perfil> perfiles = new ArrayList<>();

        if (strRoles == null) {
            Perfil userRole = perfilRepository.findByNombre("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Error: El rol no se encuentra."));
            perfiles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Perfil adminRole = perfilRepository.findByNombre("ROLE_ADMIN")
                                .orElseThrow(() -> new RuntimeException("Error: El rol no se encuentra."));
                        perfiles.add(adminRole);

                        break;
                    default:
                        Perfil userRole = perfilRepository.findByNombre("ROLE_USER")
                                .orElseThrow(() -> new RuntimeException("Error: El rol no se encuentra."));
                        perfiles.add(userRole);
                }
            });
        }

        usuario.setPerfiles((List<Perfil>) perfiles);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(new MessageResponse("Usuario registrado exitosamente!"));
    }
}
