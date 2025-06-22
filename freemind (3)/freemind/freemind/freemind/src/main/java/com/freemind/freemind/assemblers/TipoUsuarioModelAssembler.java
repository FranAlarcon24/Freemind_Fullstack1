package com.freemind.freemind.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.freemind.freemind.controller.TipoUsuarioControllerV2;
import com.freemind.freemind.model.TipoUsuario;

@Component
public class TipoUsuarioModelAssembler implements RepresentationModelAssembler<TipoUsuario, EntityModel<TipoUsuario>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<TipoUsuario> toModel(TipoUsuario tipoUsuario) {
        return EntityModel.of(tipoUsuario,
                linkTo(methodOn(TipoUsuarioControllerV2.class).getTipoUsuarioById(tipoUsuario.getId())).withSelfRel(),
                linkTo(methodOn(TipoUsuarioControllerV2.class).getAllTipoUsuarios()).withRel("tipo de usuarios"),
                linkTo(methodOn(TipoUsuarioControllerV2.class).updateTipoUsuario(tipoUsuario.getId(), tipoUsuario)).withRel("atualizar"),
                linkTo(methodOn(TipoUsuarioControllerV2.class).deleteTipoUsuario(tipoUsuario.getId())).withRel("elimianr"),
                linkTo(methodOn(TipoUsuarioControllerV2.class).patchTipoUsuario(tipoUsuario.getId(), tipoUsuario)).withRel("actualizar-parcial")
        );
    }
}
