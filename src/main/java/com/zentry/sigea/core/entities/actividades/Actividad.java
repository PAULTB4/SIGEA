package com.zentry.sigea.core.entities.actividades;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.zentry.sigea.core.entities.usuarios.Usuario;

public class Actividad {

    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    private EstadoActividad estadoActividad;
    private Usuario organizador;
    private TipoActividad tipoActividad;
    private String lugar;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Actividad() {
    }

    

}
