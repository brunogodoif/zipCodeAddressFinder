package br.com.brunogodoif.zipcodeaddressfinder.adapters.configs.dataloaders;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Log4j2
public class AsyncBatchService {

    @Async("taskExecutor")
    public <T> CompletableFuture<Void> persistBatchAsync(List<T> batch, BaseDataLoader<T> loader) {
        log.info("Persistindo lote de {} registros em thread: {}", batch.size(), Thread.currentThread().getName());
        try {
            loader.persistBatch(batch); // Agora o tipo genérico é compatível
        } catch (Exception e) {
            log.error("Erro ao persistir lote de dados: {}", e.getMessage(), e);
        }
        return CompletableFuture.completedFuture(null);
    }
}