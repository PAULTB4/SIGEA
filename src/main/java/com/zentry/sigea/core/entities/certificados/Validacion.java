package com.zentry.sigea.core.entities.certificados;

import java.time.LocalDateTime;

public class Validacion {

    private Long id;

    private Certificado certificado;
    private TipoValidador tipoValidacion;

    private LocalDateTime fechaValidacion;
    private String resultado;
    private String detalle;
}
