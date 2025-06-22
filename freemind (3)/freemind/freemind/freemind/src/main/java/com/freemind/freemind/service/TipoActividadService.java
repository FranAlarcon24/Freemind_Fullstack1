package com.freemind.freemind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.freemind.freemind.model.TipoActividad;
import com.freemind.freemind.repository.TipoActividadRepository;
import com.freemind.freemind.service.TipoActividadService;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class TipoActividadService {
    
    @Autowired
    private TipoActividadRepository tipoActividadRepository;

    public List<TipoActividad> findAll() {
        return tipoActividadRepository.findAll();
    }

    public TipoActividad findById(Integer id) {
        return tipoActividadRepository.findById(id.longValue())
                .orElseThrow(() -> new RuntimeException("TipoActividad not found"));
    }

    public TipoActividad save(TipoActividad tipoActividad) {
        return tipoActividadRepository.save(tipoActividad);
    }

    public void deleteById(Integer id) {
        TipoActividad tipoActividad = tipoActividadRepository.findById(id.longValue())
            .orElseThrow(() -> new RuntimeException("Tipo Actividad no encontrada"));
        tipoActividadRepository.delete(tipoActividad);
    }

    public TipoActividad updateTipoActividad(TipoActividad tipoActividad) {
        return tipoActividadRepository.save(tipoActividad);
    }

    public TipoActividad patchTipoActividad(Integer id, TipoActividad tipoActividad) {
        TipoActividad existingTipoActividad = findById(id);
        if (existingTipoActividad != null) {
            if (tipoActividad.getNombre() != null) {
                existingTipoActividad.setNombre(tipoActividad.getNombre());
            }
            return save(existingTipoActividad);
        }
        return null;
    }
}
