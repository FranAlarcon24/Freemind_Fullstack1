package com.freemind.freemind.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.freemind.freemind.model.Actividad;
import com.freemind.freemind.model.Estado;
import com.freemind.freemind.model.TipoActividad;
import com.freemind.freemind.model.TipoUsuario;
import com.freemind.freemind.model.Usuario;
import com.freemind.freemind.repository.ActividadRepository;

@SpringBootTest
public class ActividadServiceTest {

    @Autowired
    private ActividadService actividadService;

    @MockBean
    private ActividadRepository actividadRepository;

    private Actividad createActividad() {
        return new Actividad(
            1,
            "Taller 1",
            new TipoActividad(1, "Taller"),
            new Usuario(1, "13678390-5", "Juan", "Ramirez", "juanramirez@gmail.com", "924556786", new TipoUsuario(1, "Estudiante")),
            new Estado(1, "Pendiente")
        );
    }
    
    @Test
    public void testFindAll() {
        when(actividadRepository.findAll()).thenReturn(List.of(createActividad()));
        List<Actividad> actividades = actividadService.findAll();
        assertNotNull(actividades);
        assertEquals(1, actividades.size());
    }

    @Test
    public void testFindById() {
        when(actividadRepository.findById(1L)).thenReturn(java.util.Optional.of(createActividad()));
        Actividad actividad = actividadService.findById(1);
        assertNotNull(actividad);
        assertEquals(1, actividad.getId());
    }

    @Test
    public void testSave() {
        Actividad actividad = createActividad();
        when(actividadRepository.save(actividad)).thenReturn(actividad);
        Actividad savedActividad = actividadService.save(actividad);
        assertNotNull(savedActividad);
        assertEquals(1, savedActividad.getId());
    }

    @Test
    public void testPatchActividad() {
        Actividad existingActividad = createActividad();
        Actividad patchData = new Actividad();
        patchData.setNombre("Charla");

        when(actividadRepository.findById(1L)).thenReturn(java.util.Optional.of(existingActividad));
        when(actividadRepository.save(any(Actividad.class))).thenReturn(existingActividad);

        Actividad updatedActividad = actividadService.patchActividad(1, patchData);
        assertNotNull(updatedActividad);
        assertEquals("Charla", updatedActividad.getNombre());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(actividadRepository).deleteById(1L);
        actividadService.deleteById(1);
        verify(actividadRepository, times(1)).deleteById(1L);
    }

}
