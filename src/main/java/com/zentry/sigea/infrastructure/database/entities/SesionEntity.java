package com.zentry.sigea.infrastructure.database.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "sesion" , 
    indexes = {
        @Index(name = "idx_sesion" , columnList = "actividad_id,fecha_sesion" , unique = true)
    }
)
@Getter
@Setter
public class SesionEntity {
    
    @Id
    @GeneratedValue
    @Column(
        name = "id_sesion" , updatable = false , nullable = false , 
        columnDefinition = "UUID DEFAULT gen_random_uuid()"
    )
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actividad_id" , nullable = false)
    private ActividadEntity actividad;

    @Column(name = "fecha_sesion" , nullable = false , columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaSesion;

    @Column(name = "titulo" , nullable = true , length = 150)
    private String titulo;
}
