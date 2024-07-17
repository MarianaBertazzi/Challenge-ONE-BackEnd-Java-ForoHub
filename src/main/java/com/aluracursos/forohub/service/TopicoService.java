package com.aluracursos.forohub.service;

import com.aluracursos.forohub.model.Topico;
import com.aluracursos.forohub.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    public Topico crearTopico(Topico topico) {
        return topicoRepository.save(topico);
    }

    public List<Topico> listarTopicos() {
        return topicoRepository.findAll();
    }

    public Topico obtenerTopico(Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        return topico.orElseThrow(() -> new RuntimeException("Topico no encontrado"));
    }

    public Topico actualizarTopico(Long id, Topico topicoActualizado) {
        Topico topico = obtenerTopico(id);
        topico.setTitulo(topicoActualizado.getTitulo());
        topico.setMensaje(topicoActualizado.getMensaje());
        topico.setStatus(topicoActualizado.getStatus());
        return topicoRepository.save(topico);
    }

    public void eliminarTopico(Long id) {
        topicoRepository.deleteById(id);
    }
}
