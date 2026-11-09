package com.novadevs.similitudentregas.web;

import com.novadevs.similitudentregas.domain.ComparadorMasivo;
import com.novadevs.similitudentregas.domain.Entrega;
import com.novadevs.similitudentregas.domain.MetricaDistanciaEdicion;
import com.novadevs.similitudentregas.domain.NormalizadorTexto;
import com.novadevs.similitudentregas.domain.ParejaSimilitud;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/similitud")
public class SimilitudController {

    private final ComparadorMasivo comparador;
    private final NormalizadorTexto normalizador;

    public SimilitudController() {
        // Inicializamos nuestro motor optimizado
        this.comparador = new ComparadorMasivo(new MetricaDistanciaEdicion());
        this.normalizador = new NormalizadorTexto();
    }

    @PostMapping("/comparar")
    public List<ParejaSimilitud> compararEntregas(@RequestBody List<EntregaRequest> peticiones) {
        // Mapeamos el JSON de entrada a nuestro modelo de dominio puro, normalizando de una vez
        List<Entrega> entregas = peticiones.stream()
                .map(req -> new Entrega(req.autor(), req.texto(), normalizador.normalizar(req.texto())))
                .collect(Collectors.toList());

        // Ejecutamos la comparación masiva con el filtro de N-Gramas activado
        return comparador.compararTodas(entregas);
    }
}