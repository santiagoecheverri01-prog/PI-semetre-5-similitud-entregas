package com.novadevs.similitudentregas.domain;

import jakarta.persistence.*;
import lombok.*;

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

    private String autor1;
    private String autor2;
    private double porcentajeSimilitud;
}