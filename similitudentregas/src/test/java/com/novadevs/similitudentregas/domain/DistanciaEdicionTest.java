package com.novadevs.similitudentregas.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DistanciaEdicionTest {

    private final DistanciaEdicion comparador = new DistanciaEdicion();

    @Test
    void propiedadReflexivaEnCero() {
        //
        String texto = "Matematicas Discretas UCEVA";
        // La distancia de un texto consigo mismo debe ser cero
        assertEquals(0, comparador.calcular(texto, texto), "Medir un texto consigo mismo debe dar 0");
    }

    @Test
    void propiedadSimetrica() {
        //[cite: 7]
        String a = "casa";
        String b = "calle";
        // Medir A contra B debe dar lo mismo que B contra A
        assertEquals(comparador.calcular(a, b), comparador.calcular(b, a), "El orden de los factores no debe alterar la distancia");
    }

    @Test
    void propiedadAcotadaPorLongitudMayor() {
        //[cite: 7]
        String a = "algoritmo";
        String b = "programacion dinamica";
        int maxLen = Math.max(a.length(), b.length());

        // Nunca supera la longitud del texto más largo
        assertTrue(comparador.calcular(a, b) <= maxLen, "La distancia no puede ser mayor que la longitud del texto más largo");
    }

    @Test
    void desigualdadTriangular() {
        //[cite: 7]
        String a = "texto inicial";
        String b = "texto intermedio";
        String c = "texto final";

        int distAC = comparador.calcular(a, c);
        int distAB = comparador.calcular(a, b);
        int distBC = comparador.calcular(b, c);

        // La distancia directa de A a C es menor o igual a la suma de pasar por B
        assertTrue(distAC <= (distAB + distBC), "Debe cumplir la desigualdad triangular matemática");
    }
}