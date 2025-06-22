package com.freemind.freemind.model;

//import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "actividad")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String nombre;

    /*@ManyToOne
    @JoinColumn(name = "usuario_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Usuario usuario;*/

    /*@OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL)
    private List<TipoActividad> tiposActividad;*/

    @ManyToOne
    @JoinColumn (name = "tipo_actividad_id", nullable = false)
    private TipoActividad tipoActividad;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    private Estado estado;
}
