package com.freemind.freemind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freemind.freemind.model.Usuario;
import com.freemind.freemind.repository.UsuarioRepository;
import com.freemind.freemind.service.UsuarioService;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id.longValue())
                .orElseThrow(() -> new RuntimeException("Usuario not found"));
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deleteById(Integer id) {
        Usuario usuario = usuarioRepository.findById(id.longValue())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuarioRepository.delete(usuario);
    }

    public Usuario updateUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario patchUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario patchUsuario(Integer id, Usuario usuario) {
        Usuario existingUsuario = findById(id);
        if (existingUsuario != null) {
            if (usuario.getNombres() != null) {
                existingUsuario.setNombres(usuario.getNombres());
            }
            return save(existingUsuario);
        }
        return null;
    }

}
