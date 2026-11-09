package com.novadevs.similitudentregas.repository;

import com.novadevs.similitudentregas.domain.ResultadoSimilitudEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultadoSimilitudRepository extends JpaRepository<ResultadoSimilitudEntity, Long> {
}