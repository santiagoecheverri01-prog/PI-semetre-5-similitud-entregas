package com.novadevs.similitudentregas.domain;

// Guardamos el texto original y el ya normalizado para optimizar el rendimiento
public record Entrega(String autor, String textoOriginal, String textoNormalizado) {
}