package com.novadevs.similitudentregas.domain;

public class MetricaDistanciaEdicion implements MetricaSimilitud {

    // Instanciamos el motor matemático puro que ya probamos
    private final DistanciaEdicion motorDistancia = new DistanciaEdicion();

    @Override
    public String getNombre() {
        return "Distancia de Edición (Levenshtein)";
    }

    @Override
    public double calcularPorcentaje(String texto1, String texto2) {
        // Si ambos textos están vacíos, son 100% idénticos
        if (texto1.isEmpty() && texto2.isEmpty()) {
            return 100.0;
        }

        int distancia = motorDistancia.calcular(texto1, texto2);
        int longitudMaxima = Math.max(texto1.length(), texto2.length());

        // Fórmula de porcentaje:
        // Si la distancia es 0, da 100%. Si la distancia es igual a la longitud máxima, da 0%.
        return (1.0 - ((double) distancia / longitudMaxima)) * 100.0;
    }
}