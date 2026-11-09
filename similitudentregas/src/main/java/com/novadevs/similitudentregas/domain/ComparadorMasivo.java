package com.novadevs.similitudentregas.domain;

import java.util.ArrayList;
import java.util.List;

public class ComparadorMasivo {

    private final MetricaSimilitud metrica;
    private final FiltroNGramas filtro; // Inyectamos el filtro (RF-07)

    public ComparadorMasivo(MetricaSimilitud metrica) {
        this.metrica = metrica;
        this.filtro = new FiltroNGramas();
    }

    public List<ParejaSimilitud> compararTodas(List<Entrega> entregas) {
        List<ParejaSimilitud> resultados = new ArrayList<>();

        for (int i = 0; i < entregas.size(); i++) {
            for (int j = i + 1; j < entregas.size(); j++) {
                Entrega a = entregas.get(i);
                Entrega b = entregas.get(j);

                double similitud = 0.0;

                // Solo ejecutamos Levenshtein si superan el filtro rápido de n-gramas
                if (filtro.sonCandidatos(a.textoNormalizado(), b.textoNormalizado())) {
                    similitud = metrica.calcularPorcentaje(a.textoNormalizado(), b.textoNormalizado());
                }

                resultados.add(new ParejaSimilitud(a, b, similitud));
            }
        }

        resultados.sort((p1, p2) -> Double.compare(p2.porcentajeSimilitud(), p1.porcentajeSimilitud()));
        return resultados;
    }
}