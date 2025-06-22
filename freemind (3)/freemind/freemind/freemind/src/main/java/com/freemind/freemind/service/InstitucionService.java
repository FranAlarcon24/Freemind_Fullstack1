package com.freemind.freemind.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freemind.freemind.model.Institucion;
import com.freemind.freemind.repository.InstitucionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class InstitucionService {
    
    @Autowired
    private InstitucionRepository institucionRepository;

    public List<Institucion> findAll() {
        return institucionRepository.findAll();
    }

    public Institucion findById(Integer id) {
        return institucionRepository.findById(id.longValue())
                .orElseThrow(() -> new RuntimeException("Institucion not found"));
    }

    public Institucion save(Institucion institucion) {
        return institucionRepository.save(institucion);
    }

    public void deleteById(Integer id) {
        Institucion institucion = institucionRepository.findById(id.longValue())
            .orElseThrow(() -> new RuntimeException("Institucion no encontrada"));
        institucionRepository.delete(institucion);
    }

    public Institucion updateInstitucion(Institucion institucion) {
        return institucionRepository.save(institucion);
    }

    public Institucion patchInstitucion(Integer id, Institucion institucion) {
        Institucion existingInstitucion = findById(id);
        if (existingInstitucion != null) {
            if (institucion.getNombre() != null) {
                existingInstitucion.setNombre(institucion.getNombre());
            }
            return save(existingInstitucion);
        }
        return null;
    }

    public List<Institucion> findInstitucionByEstadoInsti (String estadoInsti) {
        List<Institucion> estadoInstis = institucionRepository.findInstitucionByEstadoInsti(estadoInsti);
        if (estadoInstis != null) {
            return institucionRepository.findInstitucionByEstadoInsti(estadoInsti);
        }
        return null;
    }

    public List<Institucion> findInstitucionByNombreTipoActividad (String nombreTipoActividad) {
        List<Institucion> nombreTipoActividades = institucionRepository.findInstitucionByNombreTipoActividad(nombreTipoActividad);
        if (nombreTipoActividades != null) {
            return institucionRepository.findInstitucionByNombreTipoActividad(nombreTipoActividad);
        }
        return null;
    }

    public List<Institucion> findByInstitucionEstadoTipoActividad (String nombre, String nombreTipo) {
        List<Institucion> nombreTipoActividades = institucionRepository.findByInstitucionEstadoTipoActividad(nombre, nombreTipo);
        if (nombreTipoActividades != null) {
            return institucionRepository.findByInstitucionEstadoTipoActividad(nombre, nombreTipo);
        }
        return null;
    }
}
