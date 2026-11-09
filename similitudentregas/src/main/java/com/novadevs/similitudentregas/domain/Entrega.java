package com.novadevs.similitudentregas.domain;

// Guardamos el texto original y el ya normalizado para optimizar el rendimiento[cite: 13]
public record Entrega(String autor, String textoOriginal, String textoNormalizado) {
}