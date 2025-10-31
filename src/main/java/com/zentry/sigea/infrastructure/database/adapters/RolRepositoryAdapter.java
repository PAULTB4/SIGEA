package com.zentry.sigea.infrastructure.database.adapters;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.zentry.sigea.core.entities.RolDomainEntity;
import com.zentry.sigea.core.repositories.IRolRepository;
import com.zentry.sigea.infrastructure.database.mappers.RolMapper;
import com.zentry.sigea.infrastructure.repositories.RolJPARepository;

@Repository
public class RolRepositoryAdapter implements IRolRepository{
    
    private final RolJPARepository rolJPARepository;

    public RolRepositoryAdapter(RolJPARepository rolJPARepository){
        this.rolJPARepository = rolJPARepository;
    }

    public List<RolDomainEntity> findAll(){
        return rolJPARepository.findAll()
            .stream()
            .map(RolMapper::toDomain)
            .collect(Collectors.toList());
    }

    public Optional<RolDomainEntity> findById(String id){
        return rolJPARepository.findById(UUID.fromString(id))
            .map(r -> RolMapper.toDomain(r));
    }
}
