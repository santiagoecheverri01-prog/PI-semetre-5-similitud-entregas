package com.novadevs.similitudentregas.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resultados_similitud")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoSimilitudEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "autor_1", nullable = false) // <-- Ajustado con guion bajo
    private String autor1;

    @Column(name = "autor_2", nullable = false) // <-- Ajustado con guion bajo
    private String autor2;

    @Column(name = "porcentaje_similitud", nullable = false)
    private double porcentajeSimilitud;
}