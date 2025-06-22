package com.freemind.freemind.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freemind.freemind.assemblers.UsuarioModelAssembler;
import com.freemind.freemind.model.Usuario;
import com.freemind.freemind.service.UsuarioService;

@RestController
@RequestMapping("/api/v2/usuarios")
public class UsuarioControllerV2 {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> getAllUsuarios() {
        List<EntityModel<Usuario>> usuarios = usuarioService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                usuarios,
                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> getUsuarioById(@PathVariable Integer id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(usuario));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> createUsuario(@RequestBody Usuario usuario) {
        Usuario newUsuario = usuarioService.save(usuario);
        return ResponseEntity
                .created(linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(newUsuario.getId())).toUri())
                .body(assembler.toModel(newUsuario));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> updateUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        Usuario updatedUsuario = usuarioService.save(usuario);
        return ResponseEntity.ok(assembler.toModel(updatedUsuario));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> patchUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        Usuario updatedUsuario = usuarioService.patchUsuario(id, usuario);
        if (updatedUsuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedUsuario));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
