package com.freemind.freemind.controller;     //CODIGO NO COMPLETO

import com.freemind.freemind.assemblers.EstadoModelAssembler;
import com.freemind.freemind.model.Estado;
import com.freemind.freemind.service.EstadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/estados")
public class EstadoControllerV2 {

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private EstadoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Estado>> getAllEstados() {
        List<EntityModel<Estado>> estados = estadoService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(estados,
                linkTo(methodOn(EstadoControllerV2.class).getAllEstados()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Estado>> getEstadoById(@PathVariable Integer id) {
        Estado estado = estadoService.findById(id);
        if (estado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(estado));
    }

    @PostMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Estado>> createEstado(@RequestBody Estado estado) {
        Estado newEstado = estadoService.save(estado);
        return ResponseEntity
                .created(linkTo(methodOn(EstadoControllerV2.class).getEstadoById(Integer.valueOf(newEstado.getId()))).toUri())
                .body(assembler.toModel(newEstado));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Estado>> updateEstado(@PathVariable Integer id, @RequestBody Estado estado) {
        estado.setId(id);
        Estado updated = estadoService.save(estado);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Estado>> patchEstado(@PathVariable Integer id, @RequestBody Estado estado) {
        Estado patched = estadoService.patchEstado(id, estado);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteEstado(@PathVariable Integer id) {
        Estado existing = estadoService.findById(id);
        if(existing == null) {
            return ResponseEntity.notFound().build();
        }
        estadoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
