package com.zentry.sigea.core.entities.inscripciones;

import java.time.LocalDate;

import com.zentry.sigea.core.entities.actividades.Actividad;
import com.zentry.sigea.core.entities.usuarios.Usuario;

public class Inscripcion {
    private Long id;
    private LocalDate fechaInscripcion;
    private Usuario usuario;
    private Actividad actividad;
    private EstadoInscripcion estadoInscripcion;

}
