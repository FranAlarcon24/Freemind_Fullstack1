package com.freemind.freemind.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.freemind.freemind.model.Institucion;

public interface InstitucionRepository extends JpaRepository<Institucion, Long> {
    List<Institucion> findByNombre(String nombre);
    List<Institucion> findById(Integer id);


    @Query("""
            SELECT i FROM Institucion i
            JOIN i.actividad a
            JOIN a.estado e
            JOIN a.tipoActividad t
            WHERE a.estado = :nombre AND t.nombre = :nombreTipo
            """)
    List<Institucion> findByInstitucionEstadoTipoActividad(@Param("nombre")String nombre, @Param("nombreTipo")String nombreTipo);

    //Une Institucion con Actividad y con Estado, filtrando por el nombre del Estado.
    @Query("SELECT i FROM Institucion i JOIN i.actividad a JOIN a.estado e WHERE e.nombre = :estadoInsti")
    List<Institucion> findInstitucionByEstadoInsti(@Param("estadoInsti") String estadoInsti);

    //Une institucion con Actividad y TipoActividad, filtrando por el nombre del TipoActividad y usando DISTINCT para no repetir instituciones.
    @Query("SELECT i FROM Institucion i JOIN i.actividad a JOIN a.tipoActividad t WHERE t.nombre = :nombreTipoActividad")
    List<Institucion> findInstitucionByNombreTipoActividad(@Param("nombreTipoActividad") String nombreTipoActividad);
}