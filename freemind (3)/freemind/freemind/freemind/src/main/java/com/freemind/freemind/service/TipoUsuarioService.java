package com.freemind.freemind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freemind.freemind.model.TipoUsuario;
import com.freemind.freemind.repository.TipoUsuarioRepository;
import com.freemind.freemind.service.TipoUsuarioService;
import jakarta.transaction.Transactional;

import java.util.List;


@Service
@Transactional
public class TipoUsuarioService {
    
    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    public List<TipoUsuario> findAll() {
        return tipoUsuarioRepository.findAll();
    }

    public TipoUsuario findById(Integer id) {
        return tipoUsuarioRepository.findById(id.longValue())
                .orElseThrow(() -> new RuntimeException("TipoUsuario not found"));
    }

    public TipoUsuario save(TipoUsuario tipoUsuario) {
        return tipoUsuarioRepository.save(tipoUsuario);
    }

    public void deleteById(Integer id) {
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(id.longValue())
            .orElseThrow(() -> new RuntimeException("Tipo Usuario no encontrado"));
        tipoUsuarioRepository.delete(tipoUsuario);
    }

    public TipoUsuario updateTipoUsuario(TipoUsuario tipoUsuario) {
        return tipoUsuarioRepository.save(tipoUsuario);
    }

    public TipoUsuario patchTipoUsuario(Integer id, TipoUsuario tipoUsuario) {
        TipoUsuario existingTipoUsuario = findById(id);
        if (existingTipoUsuario != null) {
            if (tipoUsuario.getNombre() != null) {
                existingTipoUsuario.setNombre(tipoUsuario.getNombre());
            }
            return save(existingTipoUsuario);
        }
        return null;
    }

    
}
