package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.model.Perfil;
import com.aluracursos.forohub.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/perfiles")
public class PerfilController {

    @Autowired
    private PerfilRepository perfilRepository;

    @PostMapping
    public ResponseEntity<Perfil> crearPerfil(@RequestBody @Validated Perfil perfil) {
        Perfil nuevoPerfil = perfilRepository.save(perfil);
        return ResponseEntity.ok(nuevoPerfil);
    }

    @GetMapping
    public List<Perfil> listarPerfiles() {
        return perfilRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Perfil> obtenerPerfil(@PathVariable Long id) {
        return perfilRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Perfil> actualizarPerfil(@PathVariable Long id, @RequestBody @Validated Perfil perfil) {
        return perfilRepository.findById(id)
                .map(existingPerfil -> {
                    existingPerfil.setNombre(perfil.getNombre());
                    Perfil updatedPerfil = perfilRepository.save(existingPerfil);
                    return ResponseEntity.ok(updatedPerfil);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPerfil(@PathVariable Long id) {
        return perfilRepository.findById(id)
                .map(perfil -> {
                    perfilRepository.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
