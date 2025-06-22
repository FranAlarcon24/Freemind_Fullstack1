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

import com.freemind.freemind.model.Estado;
import com.freemind.freemind.repository.EstadoRepository;


@SpringBootTest
public class EstadoServiceTest {

    @Autowired
    private EstadoService estadoService;

    @MockBean
    private EstadoRepository estadoRepository;

    private Estado createEstado() {
        return new Estado(
            1,
            "Activo"
        );
    }

    @Test
    public void testFindAll() {
        when(estadoRepository.findAll()).thenReturn(List.of(createEstado()));
        List<Estado> estados = estadoService.findAll();
        assertNotNull(estados);
        assertEquals(1, estados.size());
    }

    @Test
    public void testFindById() {
        when(estadoRepository.findById(1L)).thenReturn(java.util.Optional.of(createEstado()));
        Estado estado = estadoService.findById(1);
        assertNotNull(estado);
        assertEquals(1, estado.getId());
    }

    @Test
    public void testSave() {
        Estado estado = createEstado();
        when(estadoRepository.save(estado)).thenReturn(estado);
        Estado savedEstado = estadoService.save(estado);
        assertNotNull(savedEstado);
        assertEquals(1, savedEstado.getId());
    }

    @Test
    public void testPatchEstado() {
        Estado existingEstado = createEstado();
        Estado patchData = new Estado();
        patchData.setNombre("Inactivo");

        when(estadoRepository.findById(1L)).thenReturn(java.util.Optional.of(existingEstado));
        when(estadoRepository.save(any(Estado.class))).thenReturn(existingEstado);

        Estado updatedEstado = estadoService.patchEstado(1, patchData);
        assertNotNull(updatedEstado);
        assertEquals("Inactivo", updatedEstado.getNombre());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(estadoRepository).deleteById(1L);
        estadoService.deleteById(1);
        verify(estadoRepository, times(1)).deleteById(1L);
    }
}
