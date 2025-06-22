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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freemind.freemind.assemblers.ActividadModelAssembler;
import com.freemind.freemind.model.Actividad;
import com.freemind.freemind.service.ActividadService;


@RestController
@RequestMapping("/api/v2/actividades")
public class ActividadControllerV2 {

    @Autowired
    private ActividadService actividadService;

    @Autowired
    private ActividadModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Actividad>>> getallActividades(){
        List<EntityModel<Actividad>> actividades = actividadService.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        if (actividades.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                actividades,
                linkTo(methodOn(ActividadControllerV2.class).getallActividades()).withSelfRel()
        ));
    }

    //Query: NombreEstado / Estado de la Actividad
    @GetMapping(value = "/nombreestados/{nombrestado}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Actividad>>> getNombreEstado(@RequestParam String nombreEstado) {
        List<EntityModel<Actividad>> nombreEstados = actividadService.findActividadByNombreEstado(nombreEstado).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (nombreEstados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(nombreEstados));
    }

    @GetMapping(value = "/nombresytipos", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Actividad>>> getctividadByEstadoTipoUsuario (@RequestParam String nombreEstado, @RequestParam String tipoUsuario) {
        List<EntityModel<Actividad>> nombreTipoUsuarios = actividadService.findActividadByEstadoTipoUsuario(nombreEstado, tipoUsuario).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (nombreTipoUsuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(nombreTipoUsuarios));
    }

    @GetMapping(value = "/estadotipousuario", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Actividad>>> getUsuarioTipoActividad (@PathVariable Long usuarioId, @PathVariable Long tipoActividadId) {
        List<EntityModel<Actividad>> nombreTipoUsuarios = actividadService.findByUsuarioTipoActividad(usuarioId, tipoActividadId).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (nombreTipoUsuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(nombreTipoUsuarios));
    }


    @GetMapping(value = "/nombretipousuarios/{nombretipousuario}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Actividad>>> getNombreTipoUsuario (@RequestParam String nombreTipoUsuario) {
        List<EntityModel<Actividad>> nombreTipoUsuarios = actividadService.findActividadByNombreTipoUsuario(nombreTipoUsuario).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (nombreTipoUsuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(nombreTipoUsuarios));
    }
    
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Actividad>> getActividadById(@PathVariable Integer id){
        Actividad actividad = actividadService.findById(id);
        if (actividad == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actividad));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Actividad>> createActividad(@RequestBody Actividad actividad){
        Actividad newActividad = actividadService.save(actividad);
        return ResponseEntity
                .created(linkTo(methodOn(ActividadControllerV2.class).getActividadById(newActividad.getId())).toUri())
                .body(assembler.toModel(newActividad));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Actividad>> updateActividad(@PathVariable Integer id, @RequestBody Actividad actividad){
        actividad.setId(id);
        Actividad updatedActividad = actividadService.save(actividad);
        return ResponseEntity.ok(assembler.toModel(updatedActividad));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Actividad>> patchActividad(@PathVariable Integer id, @RequestBody Actividad actividad){
        Actividad updatedActividad = actividadService.patchActividad(id, actividad);
        if(updatedActividad == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedActividad));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteActividad(@PathVariable Integer id) {
        Actividad actividad = actividadService.findById(id);
        if(actividad == null) {
            return ResponseEntity.notFound().build();
        }
        actividadService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}