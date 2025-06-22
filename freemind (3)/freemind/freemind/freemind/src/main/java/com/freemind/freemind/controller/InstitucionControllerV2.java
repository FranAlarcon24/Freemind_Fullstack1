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

import com.freemind.freemind.assemblers.InstitucionModelAssembler;
import com.freemind.freemind.model.Institucion;
import com.freemind.freemind.service.InstitucionService;



@RestController
@RequestMapping ("/api/v2/instituciones")
public class InstitucionControllerV2 {

    @Autowired
    private InstitucionService institucionService;

    @Autowired
    private InstitucionModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Institucion>>> getAllInstituciones(){
        List<EntityModel<Institucion>> instituciones = institucionService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        
        if(instituciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                instituciones,
                linkTo(methodOn(InstitucionControllerV2.class).getAllInstituciones()).withSelfRel()
        ));
    }

    //Query: EstadoInsti / Estado de la Institucion
    @GetMapping(value = "/estadoinstis/{estadoinsti}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Institucion>>> getEstadoInsti(@RequestParam String EstadoInsti) {
        List<EntityModel<Institucion>> estadoInstis = institucionService.findInstitucionByEstadoInsti(EstadoInsti).stream()
            .map (assembler::toModel)
            .collect(Collectors.toList());
        if(estadoInstis.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(estadoInstis));
    }

    //Query: NombreTipoActividad - Nombre del Tipo de Actividad
    @GetMapping(value = "/nombretipoactividades/{nombretipoactividad}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Institucion>>> getNombreTipoActividad (@RequestParam String nombreTipoActividad) {
        List<EntityModel<Institucion>> nombreTipoActividades = institucionService.findInstitucionByNombreTipoActividad(nombreTipoActividad).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (nombreTipoActividades.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(nombreTipoActividades));
    }

    //Query institutcion por estado yt tipo actividad
    @GetMapping(value = "/estadotipoactividad", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Institucion>>> getInstitucionEstadoTipoActividad (@RequestParam String nombre, @RequestParam String nombreTipo) {
        List<EntityModel<Institucion>> nombreTipoActividades = institucionService.findByInstitucionEstadoTipoActividad(nombre, nombreTipo).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (nombreTipoActividades.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(nombreTipoActividades));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Institucion>> getInstitucionById(@PathVariable Integer id) {
        Institucion institucion = institucionService.findById(id);
        if (institucion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(institucion));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Institucion>> createInstitucion(@RequestBody Institucion institucion) {
        Institucion newInstitucion = institucionService.save(institucion);
        return ResponseEntity
                .created(linkTo(methodOn(InstitucionControllerV2.class).getInstitucionById(newInstitucion.getId())).toUri())
                .body(assembler.toModel(newInstitucion));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Institucion>> updateInstitucion(@PathVariable Integer id, @RequestBody Institucion institucion) {
        institucion.setId(id);
        Institucion updatedInstitucion = institucionService.save(institucion);
        return ResponseEntity.ok(assembler.toModel(updatedInstitucion));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Institucion>> patchInstitucion(@PathVariable Integer id, @RequestBody Institucion institucion) {
        Institucion updatedInstitucion = institucionService.patchInstitucion(id, institucion);
        if (updatedInstitucion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedInstitucion));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteInstitucion(@PathVariable Integer id) {
        Institucion institucion = institucionService.findById(id);
        if (institucion == null) {
            return ResponseEntity.notFound().build();
        }
        institucionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
