package com.freemind.freemind.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.freemind.freemind.controller.InstitucionControllerV2;
import com.freemind.freemind.model.Institucion;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class InstitucionModelAssembler implements RepresentationModelAssembler<Institucion, EntityModel<Institucion>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Institucion> toModel(Institucion institucion){
        return EntityModel.of(institucion,
                linkTo(methodOn(InstitucionControllerV2.class).getInstitucionById(institucion.getId())).withSelfRel(),
                linkTo(methodOn(InstitucionControllerV2.class).getAllInstituciones()).withRel("instituciones"),
                linkTo(methodOn(InstitucionControllerV2.class).updateInstitucion(institucion.getId(), institucion)).withRel("actualizar"),
                linkTo(methodOn(InstitucionControllerV2.class).deleteInstitucion(institucion.getId())).withRel("eliminar"),
                linkTo(methodOn(InstitucionControllerV2.class).patchInstitucion(institucion.getId(), institucion)).withRel("actualizar-parcial")
        );
    }
}
