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

        // Validar que la lista no venga vacía o nula para evitar sorpresas
        if (entregas == null || entregas.isEmpty()) {
            return List.of();
        }

        // 1. Convertir la petición web a Entidades y guardarlas en PostgreSQL
        List<EntregaEntity> entregasEntities = entregas.stream()
                .map(e -> EntregaEntity.builder()
                        .autor(e.autor() != null ? e.autor() : "Desconocido")
                        .textoOriginal(e.textoOriginal() != null ? e.textoOriginal() : "")
                        .textoNormalizado(e.textoNormalizado() != null ? e.textoNormalizado() :
                                (e.textoOriginal() != null ? e.textoOriginal().toLowerCase() : ""))
                        .build())
                .toList();

        entregaRepository.saveAll(entregasEntities);

        // 2. Ejecutar tu motor de comparación inyectando la métrica correcta
        ComparadorMasivo comparador = new ComparadorMasivo(new MetricaDistanciaEdicion());
        List<ParejaSimilitud> resultadosCalculados = comparador.compararTodas(entregas);

        // 3. Convertir los resultados algorítmicos a Entidades y guardarlos
        List<ResultadoSimilitudEntity> resultadosEntities = resultadosCalculados.stream()
                .map(r -> ResultadoSimilitudEntity.builder()
                        .autor1(r.entregaA().autor())
                        .autor2(r.entregaB().autor())
                        .porcentajeSimilitud(r.porcentajeSimilitud())
                        .build())
                .toList();

        return resultadoRepository.saveAll(resultadosEntities);
    }
}