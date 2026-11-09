package com.novadevs.similitudentregas.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NormalizadorTextoTest {

    private final NormalizadorTexto normalizador = new NormalizadorTexto();

    @Test
    void debeNormalizarSegunRF02() {
        // RF-02 exige pasar a minúsculas, colapsar espacios y quitar puntuación[cite: 5]
        String textoOriginal = "¡Hola,   Mundo!\nEsto es un salto.";
        String textoEsperado = "hola mundo esto es un salto";

        assertEquals(textoEsperado, normalizador.normalizar(textoOriginal));
    }

    @Test
    void textosIgualesSalvoMayusculasYEspaciosDebenQuedarIdenticos() {
        // Dos entregas iguales salvo por mayúsculas y espacios dobles dan 100% de similitud[cite: 5]
        String entregaA = "EL SISTEMA, funciona   perfecto.";
        String entregaB = "el sistema funciona perfecto";

        assertEquals(normalizador.normalizar(entregaA), normalizador.normalizar(entregaB));
    }
}