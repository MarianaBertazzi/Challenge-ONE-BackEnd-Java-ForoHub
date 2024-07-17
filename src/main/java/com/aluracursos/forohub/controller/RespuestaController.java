package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.model.Respuesta;
import com.aluracursos.forohub.repository.RespuestaRepository;
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
@RequestMapping("/respuestas")
public class RespuestaController {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @PostMapping
    public ResponseEntity<Respuesta> crearRespuesta(@RequestBody @Validated Respuesta respuesta) {
        Respuesta nuevaRespuesta = respuestaRepository.save(respuesta);
        return ResponseEntity.ok(nuevaRespuesta);
    }

    @GetMapping
    public List<Respuesta> listarRespuestas() {
        return respuestaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Respuesta> obtenerRespuesta(@PathVariable Long id) {
        return respuestaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Respuesta> actualizarRespuesta(@PathVariable Long id, @RequestBody @Validated Respuesta respuesta) {
        return respuestaRepository.findById(id)
                .map(existingRespuesta -> {
                    existingRespuesta.setMensaje(respuesta.getMensaje());
                    existingRespuesta.setSolucion(respuesta.getSolucion());
                    Respuesta updatedRespuesta = respuestaRepository.save(existingRespuesta);
                    return ResponseEntity.ok(updatedRespuesta);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRespuesta(@PathVariable Long id) {
        return respuestaRepository.findById(id)
                .map(respuesta -> {
                    respuestaRepository.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
