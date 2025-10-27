package com.zentry.sigea.core.entities.certificados;

import java.time.LocalDate;

import com.zentry.sigea.core.entities.inscripciones.Asistencia;

public class Certificado {

    private Long id;
    private Asistencia asistencia;
    private String codigoValidacion;
    private LocalDate fechaEmision;


    private EstadoCertificado estadoCertificado;
    private String urlPdf;


    private LocalDate createdAt;
    private LocalDate updatedAt;

}
