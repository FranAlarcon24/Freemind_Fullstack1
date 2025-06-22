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
import com.freemind.freemind.repository.TipoUsuarioRepository;

@SpringBootTest
public class TipoUsuarioServiceTest {

    @Autowired
    private TipoUsuarioService tipoUsuarioService;

    @MockBean 
    private TipoUsuarioRepository tipoUsuarioRepository;

    private TipoUsuario createTipoUsuario() {
        return new TipoUsuario(
            1,
            "Cliente"
        );
    }

    @Test
    public void testFindAll() {
        when(tipoUsuarioRepository.findAll()).thenReturn(List.of(createTipoUsuario()));
        List<TipoUsuario> tipoUsuarios = tipoUsuarioService.findAll();
        assertNotNull(tipoUsuarios);
        assertEquals(1, tipoUsuarios.size());
    }

    @Test
    public void testFindById() {
        when(tipoUsuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(createTipoUsuario()));
        TipoUsuario tipoUsuario = tipoUsuarioService.findById(1);
        assertNotNull(tipoUsuario);
        assertEquals(1, tipoUsuario.getId());
    }

    @Test
    public void testSave() {
        TipoUsuario tipoUsuario = createTipoUsuario();
        when(tipoUsuarioRepository.save(tipoUsuario)).thenReturn(tipoUsuario);
        TipoUsuario savedTipoUsuario = tipoUsuarioService.save(tipoUsuario);
        assertNotNull(savedTipoUsuario);
        assertEquals(1, savedTipoUsuario.getId());
    }

    @Test
    public void testPatchTipoUsuario() {
        TipoUsuario existingTipoUsuario = createTipoUsuario();
        TipoUsuario patchData = new TipoUsuario();
        patchData.setNombre("Administrador");

        when(tipoUsuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(existingTipoUsuario));
        when(tipoUsuarioRepository.save(any(TipoUsuario.class))).thenReturn(existingTipoUsuario);

        TipoUsuario updatedTipoUsuario = tipoUsuarioService.patchTipoUsuario(1, patchData);
        assertNotNull(updatedTipoUsuario);
        assertEquals("Administrador", updatedTipoUsuario.getNombre());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(tipoUsuarioRepository).deleteById(1L);
        tipoUsuarioService.deleteById(1);
        verify(tipoUsuarioRepository, times(1)).deleteById(1L);
    }
}
