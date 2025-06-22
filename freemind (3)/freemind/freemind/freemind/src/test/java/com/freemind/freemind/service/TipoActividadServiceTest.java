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

import com.freemind.freemind.model.TipoActividad;
import com.freemind.freemind.repository.TipoActividadRepository;

@SpringBootTest
public class TipoActividadServiceTest {

    @Autowired
    private TipoActividadService tipoActividadService;

    @MockBean
    private TipoActividadRepository tipoActividadRepository;

    private TipoActividad createTipoActividad() {
        return new TipoActividad(
            1,
            "Taller"
        );
    }

    @Test
    public void testFindAll() {
        when(tipoActividadRepository.findAll()).thenReturn(List.of(createTipoActividad()));
        List<TipoActividad> tipoActividades = tipoActividadService.findAll();
        assertNotNull(tipoActividades);
        assertEquals(1, tipoActividades.size());
    }

    @Test
    public void testFindById() {
        when(tipoActividadRepository.findById(1L)).thenReturn(java.util.Optional.of(createTipoActividad()));
        TipoActividad tipoActividad = tipoActividadService.findById(1);
        assertNotNull(tipoActividad);
        assertEquals(1, tipoActividad.getId());
    }

    @Test
    public void testSave() {
        TipoActividad tipoActividad= createTipoActividad();
        when(tipoActividadRepository.save(tipoActividad)).thenReturn(tipoActividad);
        TipoActividad savedTipoActividad = tipoActividadService.save(tipoActividad);
        assertNotNull(savedTipoActividad);
        assertEquals(1, savedTipoActividad.getId());
    }

    @Test
    public void testPatchEstado() {
        TipoActividad existingTipoActividad = createTipoActividad();
        TipoActividad patchData = new TipoActividad();
        patchData.setNombre("Taller");

        when(tipoActividadRepository.findById(1L)).thenReturn(java.util.Optional.of(existingTipoActividad));
        when(tipoActividadRepository.save(any(TipoActividad.class))).thenReturn(existingTipoActividad);

        TipoActividad updatedTipoActividad = tipoActividadService.patchTipoActividad(1, patchData);
        assertNotNull(updatedTipoActividad);
        assertEquals("Taller", updatedTipoActividad.getNombre());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(tipoActividadRepository).deleteById(1L);
        tipoActividadService.deleteById(1);
        verify(tipoActividadRepository, times(1)).deleteById(1L);
    }
}
