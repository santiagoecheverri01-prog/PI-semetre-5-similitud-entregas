package com.novadevs.similitudentregas.domain;

import java.util.HashSet;
import java.util.Set;

public class FiltroNGramas {

    private static final int N = 3; // RF-07: Secuencias de 3 palabras
    private static final double UMBRAL_MINIMO = 0.2; // Requiere al menos 20% de coincidencia para pasar al algoritmo pesado

    public boolean sonCandidatos(String texto1, String texto2) {
        if (texto1.isEmpty() || texto2.isEmpty()) return false;

        Set<String> nGramas1 = generarNGramas(texto1);
        Set<String> nGramas2 = generarNGramas(texto2);

        if (nGramas1.isEmpty() || nGramas2.isEmpty()) return false;

        // Calculamos la intersección
        Set<String> interseccion = new HashSet<>(nGramas1);
        interseccion.retainAll(nGramas2);

        // Índice de Jaccard: (Intersección) / (Unión)
        Set<String> union = new HashSet<>(nGramas1);
        union.addAll(nGramas2);

        double coeficienteJaccard = (double) interseccion.size() / union.size();
        return coeficienteJaccard >= UMBRAL_MINIMO;
    }

    private Set<String> generarNGramas(String texto) {
        Set<String> ngramas = new HashSet<>();
        String[] palabras = texto.split(" ");

        if (palabras.length < N) {
            ngramas.add(texto);
            return ngramas;
        }

        for (int i = 0; i <= palabras.length - N; i++) {
            ngramas.add(palabras[i] + " " + palabras[i+1] + " " + palabras[i+2]);
        }
        return ngramas;
    }
}