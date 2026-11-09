package com.novadevs.similitudentregas.web;

import com.novadevs.similitudentregas.domain.Entrega;
import com.novadevs.similitudentregas.domain.ResultadoSimilitudEntity;
import com.novadevs.similitudentregas.service.SimilitudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/similitud")
public class SimilitudController {

    private final SimilitudService similitudService;

    public SimilitudController(SimilitudService similitudService) {
        this.similitudService = similitudService;
    }

    @PostMapping("/comparar")
    public ResponseEntity<List<ResultadoSimilitudEntity>> compararEntregas(@RequestBody List<Entrega> entregas) {
        // Le pasamos la pelota al servicio para que guarde en BD y procese
        List<ResultadoSimilitudEntity> resultados = similitudService.analizarYGuardar(entregas);

        return ResponseEntity.ok(resultados);
    }
}