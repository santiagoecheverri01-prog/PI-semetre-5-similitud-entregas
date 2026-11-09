package com.novadevs.similitudentregas.domain;

public interface MetricaSimilitud {

    /**
     * @return El nombre de la métrica para mostrar en el informe.
     */
    String getNombre();

    /**
     * @param texto1 Texto ya normalizado
     * @param texto2 Texto ya normalizado
     * @return Porcentaje de similitud entre 0.0 y 100.0
     */
    double calcularPorcentaje(String texto1, String texto2);
}