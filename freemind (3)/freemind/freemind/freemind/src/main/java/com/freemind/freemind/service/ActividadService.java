package com.freemind.freemind.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freemind.freemind.model.Actividad;
import com.freemind.freemind.repository.ActividadRepository;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class ActividadService {

    @Autowired
    private ActividadRepository actividadRepository;

    public List<Actividad> findAll() {
        return actividadRepository.findAll();
    }

    public Actividad findById(Integer id) {
        return actividadRepository.findById(id.longValue())
                .orElseThrow(() -> new RuntimeException("Actividad not found"));
    }

    public Actividad save(Actividad actividad) {
        return actividadRepository.save(actividad);
    }

    /*public void deleteById(Integer id) {
        actividadRepository.deleteById(id.longValue());
    }*/

    public void deleteById(Integer id) {
        Actividad actividad = actividadRepository.findById(id.longValue())
            .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));
        actividadRepository.delete(actividad);
    }


    public Actividad updateActividad(Actividad actividad) {
        return actividadRepository.save(actividad);
    }

    public Actividad patchActividad(Integer id, Actividad actividad) {
        Actividad existingActividad = findById(id);
        if (existingActividad != null) {
            if (actividad.getNombre() != null) {
                existingActividad.setNombre(actividad.getNombre());
            }
            return save(existingActividad);
        }
        return null;
    }

    public List<Actividad> findActividadByEstadoTipoUsuario(String nombreEstado, String nombreTipoUsuario) {
        List<Actividad> actividades = actividadRepository.findActividadByEstadoTipoUsuario(nombreEstado, nombreTipoUsuario);
        if(actividades != null) {
            return actividadRepository.findActividadByEstadoTipoUsuario(nombreEstado, nombreTipoUsuario);
        }
        return null;
    }

    public List<Actividad> findActividadByNombreEstado (String nombreEstado) {
        List<Actividad> nombreEstados = actividadRepository.findActividadByNombreEstado(nombreEstado);
        if (nombreEstados != null) {
            return actividadRepository.findActividadByNombreEstado(nombreEstado);
        }
        return null;
    }

    public List<Actividad> findActividadByNombreTipoUsuario (String nombreTipoUsuario) {
        List<Actividad> nombreTipoUsuarios = actividadRepository.findActividadByNombreTipoUsuario(nombreTipoUsuario);
        if(nombreTipoUsuarios != null) {
            return actividadRepository.findActividadByNombreTipoUsuario(nombreTipoUsuario);
        }
        return null;
    }

    public List<Actividad> findByUsuarioTipoActividad(Long usuarioId, Long tipoActividadId){
        List<Actividad> actividadPorNombreYtipo = actividadRepository.findByUsuarioIdAndTipoActividadId(usuarioId, tipoActividadId);
        if(actividadPorNombreYtipo != null) {
            return actividadRepository.findByUsuarioIdAndTipoActividadId(usuarioId, tipoActividadId);
        }
        return null;
    }

}


