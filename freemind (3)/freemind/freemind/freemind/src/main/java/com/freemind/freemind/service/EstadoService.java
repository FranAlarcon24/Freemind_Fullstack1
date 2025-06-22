package com.freemind.freemind.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.freemind.freemind.model.Estado;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.freemind.freemind.repository.EstadoRepository;

@Service
@Transactional
public class EstadoService {
    
    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> findAll() {
        return estadoRepository.findAll();
    }

    public Estado findById(Integer id) {
        return estadoRepository.findById(id.longValue())
                .orElseThrow(() -> new RuntimeException("Estado not found"));
    }

    public Estado save(Estado estado) {
        return estadoRepository.save(estado);
    }

    /*public void deleteById(Integer id) {
        estadoRepository.deleteById(id.longValue());
    }*/

    public void deleteById(Integer id) {
        Estado estado = estadoRepository.findById(id.longValue())
            .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
        estadoRepository.delete(estado);
    }

    public Estado updateEstado(Estado estado) {
        return estadoRepository.save(estado);
    }

    public Estado patchEstado(Integer id, Estado estado) {
        Estado existingEstado = findById(id);
        if (existingEstado != null) {
            if (estado.getNombre() != null) {
                existingEstado.setNombre(estado.getNombre());
            }
            return save(existingEstado);
        }
        return null;
    }
}
