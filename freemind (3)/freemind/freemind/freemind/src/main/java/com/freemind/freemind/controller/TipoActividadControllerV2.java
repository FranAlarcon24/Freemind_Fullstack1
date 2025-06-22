package com.freemind.freemind.controller;

import com.freemind.freemind.assemblers.TipoActividadModelAssembler;
import com.freemind.freemind.model.TipoActividad;
import com.freemind.freemind.service.TipoActividadService;


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
@RequestMapping("/api/v2/tipoActividades")
public class TipoActividadControllerV2 {

    @Autowired
    private TipoActividadService tipoActividadService;

    @Autowired
    private TipoActividadModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<TipoActividad>>> getAllTipoActividades() {
        List<EntityModel<TipoActividad>> tipoActividades = tipoActividadService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if(tipoActividades.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                tipoActividades,
                linkTo(methodOn(TipoActividadControllerV2.class).getAllTipoActividades()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TipoActividad>> getTipoActividadById(@PathVariable Integer id) {
        TipoActividad tipoActividad = tipoActividadService.findById(id);
        if (tipoActividad == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(tipoActividad));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TipoActividad>> createTipoActividad (@RequestBody TipoActividad tipoActividad) {
        TipoActividad newTipoActividad = tipoActividadService.save(tipoActividad);
        return ResponseEntity
                .created(linkTo(methodOn(TipoActividadControllerV2.class).getTipoActividadById(newTipoActividad.getId())).toUri())
                .body(assembler.toModel(newTipoActividad));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TipoActividad>> updateTipoActividad(@PathVariable Integer id, @RequestBody TipoActividad tipoActividad) {
        tipoActividad.setId(id);
        TipoActividad updatedTipoActividad = tipoActividadService.save(tipoActividad);
        return ResponseEntity.ok(assembler.toModel(updatedTipoActividad));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TipoActividad>> patchTipoActividad(@PathVariable Integer id, @RequestBody TipoActividad tipoActividad) {
        TipoActividad updatedTipoActividad = tipoActividadService.patchTipoActividad(id, tipoActividad);
        if (updatedTipoActividad == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedTipoActividad));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteTipoActividad (@PathVariable Integer id) {
        TipoActividad tipoActividad = tipoActividadService.findById(id);
        if (tipoActividad == null) {
            return ResponseEntity.notFound().build();
        }
        tipoActividadService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
