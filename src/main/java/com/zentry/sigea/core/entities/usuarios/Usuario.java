package com.zentry.sigea.core.entities.usuarios;

import java.time.LocalDateTime;

public class Usuario {
    private Long id;
    private String nombres;
    private String apellidos;
    private String correo;
    private String passwordHash;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Usuario() {
    }
}
