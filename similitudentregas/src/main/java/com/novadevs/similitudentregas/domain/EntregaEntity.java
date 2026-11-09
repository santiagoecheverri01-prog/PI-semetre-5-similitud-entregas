package com.novadevs.similitudentregas.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "entregas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntregaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String autor;

    @Column(columnDefinition = "TEXT")
    private String textoOriginal;

    @Column(columnDefinition = "TEXT")
    private String textoNormalizado;
}