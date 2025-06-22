package com.freemind.freemind.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.freemind.freemind.model.TipoUsuario;
import com.freemind.freemind.model.Usuario;
import com.freemind.freemind.repository.UsuarioRepository;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    private Usuario createUsuario() {
        return new Usuario(
            1,
            "12345567-9",
            "Juan Alberto", 
            "Perez Ahumada", 
            "juan.perez11@gmail.com", 
            "956747894", 
            new TipoUsuario(1, "administrador")
        );
    }

    @Test
    public void testFindAll() {
        when(usuarioRepository.findAll()).thenReturn(List.of(createUsuario()));
        List<Usuario> usuarios = usuarioService.findAll();
        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
    }

    @Test
    public void testFindById() {
        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(createUsuario()));
        Usuario usuario = usuarioService.findById(1);
        assertNotNull(usuario);
        assertEquals(1, usuario.getId());
    }

    @Test
    public void testSave() {
        Usuario usuario = createUsuario();
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        Usuario savedUsuario = usuarioService.save(usuario);
        assertNotNull(savedUsuario);
        assertEquals(1, savedUsuario.getId());
    }

    @Test
    public void testPatchUsuario() {
        Usuario existingUsuario = createUsuario();
        Usuario patchData = new Usuario();
        patchData.setNombres("Eduardo Roberto");

        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(existingUsuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(existingUsuario);

        Usuario updatedUsuario = usuarioService.patchUsuario(1, patchData);
        assertNotNull(updatedUsuario);
        assertEquals("Administrador", updatedUsuario.getNombres());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(usuarioRepository).deleteById(1L);
        usuarioService.deleteById(1);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }
}
