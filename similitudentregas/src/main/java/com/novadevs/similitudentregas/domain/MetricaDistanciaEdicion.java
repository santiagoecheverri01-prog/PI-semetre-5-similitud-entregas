package com.novadevs.similitudentregas.domain;

public class MetricaDistanciaEdicion implements MetricaSimilitud {

    @Override
    public double calcularPorcentaje(String texto1, String texto2) {
        // 1. Validar nulos
        if (texto1 == null || texto2 == null) {
            return 0.0;
        }

        // Si ambos están vacíos, son 100% iguales
        if (texto1.isEmpty() && texto2.isEmpty()) {
            return 100.0;
        }

        // 2. Calcular la distancia pura de Levenshtein
        int distancia = calcularLevenshtein(texto1, texto2);

        // 3. Obtener la longitud del texto más largo para la escala
        int maxLongitud = Math.max(texto1.length(), texto2.length());

        // 4. Fórmula: (1 - (distancia / maxLongitud)) * 100
        double similitud = (1.0 - ((double) distancia / maxLongitud)) * 100.0;

        // Redondear a dos decimales
        return Math.round(similitud * 100.0) / 100.0;
    }

    // <-- ¡Agregamos este método que exigía la interfaz!
    @Override
    public String getNombre() {
        return "Distancia Edicion (Levenshtein)";
    }

    private int calcularLevenshtein(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }
}