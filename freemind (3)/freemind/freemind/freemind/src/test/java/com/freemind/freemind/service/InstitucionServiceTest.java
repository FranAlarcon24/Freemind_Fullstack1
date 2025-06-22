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

import com.freemind.freemind.model.Actividad;
import com.freemind.freemind.model.Institucion;
import com.freemind.freemind.repository.InstitucionRepository;

@SpringBootTest
public class InstitucionServiceTest {

    @Autowired
    private InstitucionService institucionService;

    @MockBean
    private InstitucionRepository institucionRepository;

    private Institucion createInstitucion() {
        return new Institucion(
            1,
            "Colegio Jose Maria",
            "Av.Santelices 123",
            new Actividad()
        );
    }

    @Test
    public void testFindAll() {
        when(institucionRepository.findAll()).thenReturn(List.of(createInstitucion()));
        List<Institucion> instituciones = institucionService.findAll();
        assertNotNull(instituciones);
        assertEquals(1, instituciones.size());
    }

    @Test
    public void testFindById() {
        when(institucionRepository.findById(1L)).thenReturn(java.util.Optional.of(createInstitucion()));
        Institucion institucion = institucionService.findById(1);
        assertNotNull(institucion);
        assertEquals(1, institucion.getId());
    }

    @Test
    public void testSave() {
        Institucion institucion = createInstitucion();
        when(institucionRepository.save(institucion)).thenReturn(institucion);
        Institucion savedInstitucion = institucionService.save(institucion);
        assertNotNull(savedInstitucion);
        assertEquals(1, savedInstitucion.getId());
    }

    @Test
    public void testPatchInstitucion() {
        Institucion existingInstitucion = createInstitucion();
        Institucion patchData = new Institucion();
        patchData.setNombre("Colegio Jose Maria de la Cruz");

        when(institucionRepository.findById(1L)).thenReturn(java.util.Optional.of(existingInstitucion));
        when(institucionRepository.save(any(Institucion.class))).thenReturn(existingInstitucion);

        Institucion updatedInstitucion = institucionService.patchInstitucion(1, patchData);
        assertNotNull(updatedInstitucion);
        assertEquals("Colegio Jose Maria de la Cruz", updatedInstitucion.getNombre());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(institucionRepository).deleteById(1L);
        institucionService.deleteById(1);
        verify(institucionRepository, times(1)).deleteById(1L);
    }

}
