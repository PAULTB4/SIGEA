package com.zentry.sigea.infrastructure.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zentry.sigea.infrastructure.database.entities.UsuarioEntity;

public interface UsuarioJPARepository extends JpaRepository<UsuarioEntity , UUID>{
    public Optional<UsuarioEntity> findById(UUID id);
}
