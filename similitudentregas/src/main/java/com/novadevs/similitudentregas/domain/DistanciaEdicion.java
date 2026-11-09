package com.novadevs.similitudentregas.domain;

public class DistanciaEdicion {

    public int calcular(String s1, String s2) {
        if (s1 == null || s2 == null) {
            throw new IllegalArgumentException("Los textos no pueden ser nulos");
        }

        int len1 = s1.length();
        int len2 = s2.length();

        // Optimización: en vez de una matriz completa, usamos un solo arreglo para la fila anterior[cite: 14]
        int[] dp = new int[len2 + 1];

        // Llenamos la fila base (distancia contra un texto vacío)
        for (int j = 0; j <= len2; j++) {
            dp[j] = j;
        }

        for (int i = 1; i <= len1; i++) {
            int prev = dp[0];
            dp[0] = i;

            for (int j = 1; j <= len2; j++) {
                int temp = dp[j];

                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    // Caracteres iguales: costo 0
                    dp[j] = prev;
                } else {
                    // Caracteres distintos: 1 + mínimo entre sustituir, insertar o borrar
                    dp[j] = 1 + Math.min(prev, Math.min(dp[j], dp[j - 1]));
                }
                prev = temp;
            }
        }

        return dp[len2];
    }
}