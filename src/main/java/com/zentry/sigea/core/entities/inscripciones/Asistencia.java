package com.zentry.sigea.core.entities.inscripciones;

import java.time.LocalDateTime;

import com.zentry.sigea.core.entities.actividades.Sesion;

public class Asistencia {

    private Long id;

    private Sesion sesion;
    private Inscripcion inscripcion;

    private Boolean presente;

    private LocalDateTime registradoEn;

}
