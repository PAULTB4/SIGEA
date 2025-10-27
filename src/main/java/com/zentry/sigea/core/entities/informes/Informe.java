package com.zentry.sigea.core.entities.informes;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.zentry.sigea.core.entities.actividades.Actividad;

public class Informe {

    private Long id;
    private Actividad actividad;
    private TipoInforme tipoInforme;
    private LocalDate fechaSubidad;
    private String archivoUrl;
    private String observaciones;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
