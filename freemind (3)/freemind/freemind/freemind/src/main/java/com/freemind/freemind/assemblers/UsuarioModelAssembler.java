package com.freemind.freemind.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.freemind.freemind.controller.UsuarioControllerV2;
import com.freemind.freemind.model.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>>{

    @SuppressWarnings("null")
    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(usuario.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withRel("usuarios"),
                linkTo(methodOn(UsuarioControllerV2.class).updateUsuario(usuario.getId(), usuario)).withRel("actualizar"),
                linkTo(methodOn(UsuarioControllerV2.class).deleteUsuario(usuario.getId())).withRel("eliminar"),
                linkTo(methodOn(UsuarioControllerV2.class).patchUsuario(usuario.getId(), usuario)).withRel("actualizar-parcial")
        );
    }
}
