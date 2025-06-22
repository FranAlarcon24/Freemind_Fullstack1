package com.freemind.freemind.assemblers;

import com.freemind.freemind.controller.EstadoControllerV2;
import com.freemind.freemind.model.Estado;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EstadoModelAssembler implements RepresentationModelAssembler<Estado, EntityModel<Estado>>{

    @SuppressWarnings("null")
    @Override
    public EntityModel<Estado> toModel(Estado estado) {
        return EntityModel.of(estado,
                linkTo(methodOn(EstadoControllerV2.class).getEstadoById(Integer.valueOf(estado.getId()))).withSelfRel(),
                linkTo(methodOn(EstadoControllerV2.class).deleteEstado(Integer.valueOf(estado.getId()))).withRel("eliminar"),
                linkTo(methodOn(EstadoControllerV2.class).updateEstado(Integer.valueOf(estado.getId()), estado)).withRel("actualizar")
        );
    }
}
