package com.freemind.freemind.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.freemind.freemind.controller.TipoActividadControllerV2;
import com.freemind.freemind.model.TipoActividad;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class TipoActividadModelAssembler implements RepresentationModelAssembler<TipoActividad, EntityModel<TipoActividad>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<TipoActividad> toModel(TipoActividad tipoActividad) {
        return EntityModel.of(tipoActividad,
                linkTo(methodOn(TipoActividadControllerV2.class).getTipoActividadById(tipoActividad.getId())).withSelfRel(),
                linkTo(methodOn(TipoActividadControllerV2.class).getAllTipoActividades()).withRel("tipo de actividades"),
                linkTo(methodOn(TipoActividadControllerV2.class).updateTipoActividad(tipoActividad.getId(), tipoActividad)).withRel("actualizar"),
                linkTo(methodOn(TipoActividadControllerV2.class).deleteTipoActividad(tipoActividad.getId())).withRel("eliminar"),
                linkTo(methodOn(TipoActividadControllerV2.class).patchTipoActividad(tipoActividad.getId(), tipoActividad)).withRel("actualizar")
        );
    }    
}
