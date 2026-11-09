package com.novadevs.similitudentregas.domain;

public class NormalizadorTexto {

    public String normalizar(String texto) {
        if (texto == null) {
            return "";
        }
        return texto
                .toLowerCase() // 1. Pasar a minúsculas
                .replaceAll("\\p{P}", "") // 2. Quitar TODOS los signos de puntuación (ASCII y Unicode)
                .replaceAll("\\s+", " ") // 3. Colapsar múltiples espacios
                .trim();
    }
}