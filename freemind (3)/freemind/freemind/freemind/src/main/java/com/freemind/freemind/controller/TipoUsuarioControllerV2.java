package com.freemind.freemind.controller;

import com.freemind.freemind.assemblers.TipoUsuarioModelAssembler;
import com.freemind.freemind.model.TipoUsuario;
import com.freemind.freemind.service.TipoUsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/tipoUsuarios")
public class TipoUsuarioControllerV2 {

    @Autowired
    private TipoUsuarioService tipoUsuarioService;

    @Autowired
    private TipoUsuarioModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<TipoUsuario>>> getAllTipoUsuarios() {
        List<EntityModel<TipoUsuario>> tipoUsuarios = tipoUsuarioService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if(tipoUsuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                tipoUsuarios,
                linkTo(methodOn(TipoUsuarioControllerV2.class).getAllTipoUsuarios()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TipoUsuario>> getTipoUsuarioById(@PathVariable Integer id) {
        TipoUsuario tipoUsuario = tipoUsuarioService.findById(id);
        if (tipoUsuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(tipoUsuario));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TipoUsuario>> createTipoUsuario(@RequestBody TipoUsuario tipoUsuario) {
        TipoUsuario newTipoUsuario = tipoUsuarioService.save(tipoUsuario);
        return ResponseEntity
                .created(linkTo(methodOn(TipoUsuarioControllerV2.class).getTipoUsuarioById(newTipoUsuario.getId())).toUri())
                .body(assembler.toModel(newTipoUsuario));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TipoUsuario>> updateTipoUsuario(@PathVariable Integer id, @RequestBody TipoUsuario tipoUsuario) {
        tipoUsuario.setId(id);
        TipoUsuario updatedTipoUsuario = tipoUsuarioService.save(tipoUsuario);
        return ResponseEntity.ok(assembler.toModel(updatedTipoUsuario));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TipoUsuario>> patchTipoUsuario(@PathVariable Integer id, @RequestBody TipoUsuario tipoUsuario) {
        TipoUsuario updatedTipoUsuario = tipoUsuarioService.patchTipoUsuario(id, tipoUsuario);
        if (updatedTipoUsuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedTipoUsuario));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteTipoUsuario(@PathVariable Integer id) {
        TipoUsuario tipoUsuario = tipoUsuarioService.findById(id);
        if (tipoUsuario == null) {
            return ResponseEntity.notFound().build();
        }
        tipoUsuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
