package com.novadevs.similitudentregas.service;

import com.novadevs.similitudentregas.domain.*;
import com.novadevs.similitudentregas.repository.EntregaRepository;
import com.novadevs.similitudentregas.repository.ResultadoSimilitudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SimilitudService {

    private final EntregaRepository entregaRepository;
    private final ResultadoSimilitudRepository resultadoRepository;

    // Inyección de dependencias por constructor
    public SimilitudService(EntregaRepository entregaRepository, ResultadoSimilitudRepository resultadoRepository) {
        this.entregaRepository = entregaRepository;
        this.resultadoRepository = resultadoRepository;
    }

    @Transactional
    public List<ResultadoSimilitudEntity> analizarYGuardar(List<Entrega> entregas) {

        // 1. Convertir la petición web a Entidades y guardarlas en PostgreSQL
        List<EntregaEntity> entregasEntities = entregas.stream()
                .map(e -> EntregaEntity.builder()
                        .autor(e.autor()) // Getter del record Entrega
                        .textoOriginal(e.textoOriginal()) // Getter del record Entrega
                        .textoNormalizado(e.textoNormalizado() != null ? e.textoNormalizado() : e.textoOriginal().toLowerCase())
                        .build())
                .toList();

        entregaRepository.saveAll(entregasEntities);

        // 2. Ejecutar tu motor de comparación inyectando la métrica correcta
        ComparadorMasivo comparador = new ComparadorMasivo(new MetricaDistanciaEdicion());
        List<ParejaSimilitud> resultadosCalculados = comparador.compararTodas(entregas);

        // 3. Convertir los resultados algorítmicos a Entidades y guardarlos
        List<ResultadoSimilitudEntity> resultadosEntities = resultadosCalculados.stream()
                .map(r -> ResultadoSimilitudEntity.builder()
                        .autor1(r.entregaA().autor()) // Extraemos el autor del primer objeto Entrega
                        .autor2(r.entregaB().autor()) // Extraemos el autor del segundo objeto Entrega
                        .porcentajeSimilitud(r.porcentajeSimilitud()) // Getter del record ParejaSimilitud
                        .build())
                .toList();

        return resultadoRepository.saveAll(resultadosEntities);
    }
}