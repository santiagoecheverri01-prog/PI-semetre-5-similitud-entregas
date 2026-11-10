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

        if (entregas == null || entregas.isEmpty()) {
            return List.of();
        }

        // 1. Crear las entidades y asegurar la normalización de una vez
        List<EntregaEntity> entregasEntities = entregas.stream()
                .map(e -> EntregaEntity.builder()
                        .autor(e.autor() != null ? e.autor() : "Desconocido")
                        .textoOriginal(e.textoOriginal() != null ? e.textoOriginal() : "")
                        .textoNormalizado(e.textoNormalizado() != null ? e.textoNormalizado() :
                                (e.textoOriginal() != null ? e.textoOriginal().toLowerCase() : ""))
                        .build())
                .toList();

        entregaRepository.saveAll(entregasEntities);

        // 2. ¡IMPORTANTE! Reconvertimos las entidades guardadas (o normalizadas) a objetos de dominio
        // para que el comparador reciba el texto normalizado y no llegue nulo.
        List<Entrega> entregasNormalizadasParaComparar = entregasEntities.stream()
                .map(ent -> new Entrega(ent.getAutor(), ent.getTextoOriginal(), ent.getTextoNormalizado()))
                .toList();

        // 3. Ejecutar el comparador con los datos ya limpios y normalizados
        ComparadorMasivo comparador = new ComparadorMasivo(new MetricaDistanciaEdicion());
        List<ParejaSimilitud> resultadosCalculados = comparador.compararTodas(entregasNormalizadasParaComparar);

        // 4. Guardar resultados
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