package com.novadevs.similitudentregas.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComparadorMasivoTest {

    private final NormalizadorTexto normalizador = new NormalizadorTexto();
    private final MetricaSimilitud metrica = new MetricaDistanciaEdicion();
    // Inyectamos la métrica en el comparador (RQ-01)[cite: 11]
    private final ComparadorMasivo comparador = new ComparadorMasivo(metrica);

    // Método auxiliar para no repetir código al crear los mocks
    private Entrega crearEntrega(String autor, String texto) {
        return new Entrega(autor, texto, normalizador.normalizar(texto)); // Normalizamos al crear[cite: 13]
    }

    @Test
    void debeCumplirCursoDeEjemplo() {
        //[cite: 16] 1. Dos idénticas
        Entrega e1 = crearEntrega("Juan", "Este es el texto original del ensayo.");
        Entrega e2 = crearEntrega("Pedro", "Este es el texto original del ensayo.");

        //[cite: 16] 2. Dos con los párrafos cambiados de orden
        Entrega e3 = crearEntrega("Maria", "Parrafo uno. Parrafo dos.");
        Entrega e4 = crearEntrega("Ana", "Parrafo dos. Parrafo uno.");

        //[cite: 16] 3. Dos sin relación
        Entrega e5 = crearEntrega("Luis", "Las bases de datos relacionales usan SQL.");
        Entrega e6 = crearEntrega("Carlos", "El clima en Colombia es muy variado.");

        //[cite: 16] 4. Una copiada a medias
        Entrega e7 = crearEntrega("Sofia", "El microservicio es independiente y escalable.");
        Entrega e8 = crearEntrega("Laura", "El microservicio es independiente y muy lento.");

        List<ParejaSimilitud> resultados = comparador.compararTodas(List.of(e1, e2, e3, e4, e5, e6, e7, e8));

        // Verificar que se ordenan de mayor a menor similitud (RF-05)[cite: 5]
        assertTrue(resultados.get(0).porcentajeSimilitud() >= resultados.get(resultados.size() - 1).porcentajeSimilitud());

        // La pareja 1 y 2 debe estar de primera y tener 100% de similitud
        ParejaSimilitud top1 = resultados.get(0);
        assertEquals(100.0, top1.porcentajeSimilitud());
    }

    @Test
    void medirDesempenoConFiltroNGramasRealista() {
        List<Entrega> entregas = new ArrayList<>();

        // Simulamos 40 estudiantes honestos con ensayos 100% únicos (sin palabras compartidas)
        for (int i = 1; i <= 40; i++) {
            StringBuilder sb = new StringBuilder();
            // Generamos 1000 palabras distintas por estudiante
            for(int w = 0; w < 1000; w++) {
                sb.append("estudiante").append(i).append("palabra").append(w).append(" ");
            }
            entregas.add(crearEntrega("Autor " + i, sb.toString()));
        }

        long inicio = System.currentTimeMillis();
        List<ParejaSimilitud> resultados = comparador.compararTodas(entregas);
        long fin = System.currentTimeMillis();

        long tiempoTotal = fin - inicio;

        int parejasDescartadas = 0;
        int parejasAnalizadas = 0;
        for (ParejaSimilitud pareja : resultados) {
            if (pareja.porcentajeSimilitud() == 0.0) {
                parejasDescartadas++;
            } else {
                parejasAnalizadas++;
            }
        }

        System.out.println("====== DESEMPEÑO CON FILTRO N-GRAMAS (CORREGIDO) ======");
        System.out.println("Total Parejas: " + resultados.size());
        System.out.println("Parejas Descartadas por Filtro: " + parejasDescartadas);
        System.out.println("Parejas Analizadas (Levenshtein): " + parejasAnalizadas);
        System.out.println("Tiempo total: " + tiempoTotal + " ms (" + (tiempoTotal / 1000.0) + " segundos)");
        System.out.println("=======================================================");

        // Validamos
        assertEquals(780, resultados.size());
    }
}