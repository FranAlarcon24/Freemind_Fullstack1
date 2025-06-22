package com.freemind.freemind.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.freemind.freemind.model.Actividad;

@Repository
public interface ActividadRepository extends JpaRepository <Actividad, Long> {
    //List<Actividad> findByUsuarioId(Integer usuarioId);

    List<Actividad> findByUsuarioIdAndTipoActividadId(Long usuarioId, Long tipoActividadId);

    
 /*   //Actividad con Estado y TipoActividad y filtra por el nombre del Estado.
    @Query("SELECT a FROM Actividad a JOIN a.estado e JOIN a.tipoActividad t WHERE e.nombre = :nombreEstado")
    List<Actividad> findActividadByNombreEstado(@Param("nombreEstado") String nombreEstado);
*/
    @Query("""
            SELECT a FROM Actividad a
            JOIN a.estado e
            JOIN a.tipoActividad t
            WHERE e.nombre = :nombreEstado
            """)
    List<Actividad>findActividadByNombreEstado(@Param("nombreEstado") String nombreEstado);


/*     //Une Actividad con Usuario y con TipoUsuario, filtrando por el nombre del TipoUsuario.
    @Query("SELECT a FROM Actividad a JOIN a.usuario u JOIN u.tipoUsuario tu WHERE tu.nombre = :nombreTipoUsuario")
    List<Actividad> findActividadByNombreTipoUsuario(@Param("nombreTipoUsuario") String nombreTipoUsuario);
*/
    @Query("""
            SELECT a FROM Actividad a
            JOIN a.usuario u
            JOIN u.tipoUsuario tu
            WHERE tu.nombre = :nombreTipoUsuario
            """)
    List<Actividad>findActividadByNombreTipoUsuario(@Param("nombreTipoUsuario") String nombreTipoUsuario);

    @Query("""
            SELECT a FROM Actividad a
            JOIN a.usuario u
            JOIN u.tipoUsuario tu
            JOIN a.estado e
            WHERE tu.nombre = :nombreTipoUsuario AND e.nombre= :nombreEstado
            """)
    List<Actividad> findActividadByEstadoTipoUsuario(@Param("nombreEstado")String nombreEstado, @Param("nombreTipoUsuario")String nombreTipoUsuario);
}
