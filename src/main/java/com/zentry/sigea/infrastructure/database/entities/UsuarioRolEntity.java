package com.zentry.sigea.infrastructure.database.entities;

import java.time.LocalDateTime;

import com.zentry.sigea.infrastructure.database.embedded.UsuarioRolId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "usuario_rol",
    indexes = {
        @Index(name = "idx_usuario_rol" , columnList = "usuario_id,rol_id" , unique = true)
    }
)
@Getter
@Setter
public class UsuarioRolEntity {

    @EmbeddedId
    private UsuarioRolId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idUsuario")
    @JoinColumn(name = "usuario_id" , nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne(fetch = FetchType.LAZY) // carga la entidad relacionada solo cuando se necesita.
    @MapsId("idRol")
    @JoinColumn(name = "rol_id" , nullable = false) // define la FK
    private RolEntity rol;

    @Column(name = "asignado_en" , nullable = false , columnDefinition = "TIMESTAMP")
    private LocalDateTime asignadoEn = LocalDateTime.now();
}
