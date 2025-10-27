package com.zentry.sigea.core.entities.notificaciones;

import java.time.LocalDateTime;

import com.zentry.sigea.core.entities.actividades.Actividad;
import com.zentry.sigea.core.entities.usuarios.Usuario;

public class Notificacion {
    private Long id;
    private Usuario usuario;
    private Actividad actividad;

    private TipoNotificacion tipoNotificacion;

    private String mensaje;

    private LocalDateTime fechaEnvio;

    private EstadoNotificacion estadoNotificacion;

}
