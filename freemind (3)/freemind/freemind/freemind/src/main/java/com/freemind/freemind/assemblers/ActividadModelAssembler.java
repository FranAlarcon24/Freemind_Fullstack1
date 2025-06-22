package com.freemind.freemind.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*; //COMPLETAR

import com.freemind.freemind.controller.ActividadControllerV2;
import com.freemind.freemind.model.Actividad;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ActividadModelAssembler implements RepresentationModelAssembler <Actividad, EntityModel<Actividad>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Actividad> toModel (Actividad actividad){
        return EntityModel.of(actividad,
                linkTo(methodOn(ActividadControllerV2.class).getActividadById(actividad.getId())).withSelfRel(),
                linkTo(methodOn(ActividadControllerV2.class).getallActividades()).withRel("actividades"),
                linkTo(methodOn(ActividadControllerV2.class).updateActividad(actividad.getId(), actividad)).withRel("actualizar"),
                linkTo(methodOn(ActividadControllerV2.class).deleteActividad(actividad.getId())).withRel("eliminar"),
                linkTo(methodOn(ActividadControllerV2.class).patchActividad(actividad.getId(), actividad)).withRel("actualizar-parcial")
        );
    }
}
