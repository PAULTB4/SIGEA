package com.zentry.sigea.core.entities.pagos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.zentry.sigea.core.entities.inscripciones.Inscripcion;

public class Pago {

    private Long id;
    private Inscripcion inscripcion;
    private BigDecimal monto;
    private String moneda;
    private LocalDateTime fechaPago;

    private String comprobante;

    private MetodoPago metodoPago;

    private EstadoPago estadoPago;

    private String refenciaExt;

}
