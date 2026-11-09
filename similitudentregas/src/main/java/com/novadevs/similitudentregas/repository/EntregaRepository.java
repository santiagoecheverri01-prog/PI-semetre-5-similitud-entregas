package com.novadevs.similitudentregas.repository;

import com.novadevs.similitudentregas.domain.EntregaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntregaRepository extends JpaRepository<EntregaEntity, Long> {
}