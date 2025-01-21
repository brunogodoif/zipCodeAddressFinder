package br.com.brunogodoif.zipcodeaddressfinder.adapters.configs.dataloaders;

import br.com.brunogodoif.zipcodeaddressfinder.core.ports.outbound.ScriptExecutionAdapterPort;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
public abstract class BaseDataLoader<T> {

    private static final int BATCH_SIZE = 5000;

    @Autowired protected ScriptExecutionAdapterPort scriptExecutionAdapterPort;

    private final AtomicInteger totalProcessed = new AtomicInteger(0);
    private final AtomicInteger totalPersisted = new AtomicInteger(0);
    private final AtomicInteger totalFailed = new AtomicInteger(0);

    protected abstract String getScriptName();

    protected abstract Resource[] getCsvResources();

    protected abstract T convertToEntity(CSVRecord record);

    protected abstract void persistBatch(List<T> batch);

    protected abstract void loadCaches();

    protected abstract boolean validateCaches();

    @Autowired private AsyncBatchService asyncBatchService;

    public void loadData() {
        try {
            if (!validatePreConditions()) {
                return;
            }

            processCsvFiles();
            logFinalResults();
            scriptExecutionAdapterPort.markScriptAsExecuted(getScriptName());

        } catch (Exception e) {
            log.error("Erro fatal durante o carregamento de dados de {}: {}", getScriptName(), e.getMessage(), e);
        }
    }

    private boolean validatePreConditions() {
        if (scriptExecutionAdapterPort.hasScriptBeenExecuted(getScriptName())) {
            log.info("O script {} já foi executado. Ignorando.", getScriptName());
            return false;
        }

        loadCaches();

        if (!validateCaches()) {
            log.error("Falha na validação dos caches necessários. Encerrando execução.");
            return false;
        }

        if (getCsvResources() == null) {
            log.warn("O arquivo CSV não foi encontrado. Encerrando execução.");
            return false;
        }

        log.info("Iniciando o carregamento dos dados de {}...", getScriptName());
        return true;
    }

    private void processCsvFiles() {
        for (Resource resource : getCsvResources()) {
            if (resource == null) {
                log.warn("Recurso CSV nulo encontrado, ignorando...");
                continue;
            }

            try (Reader reader = new InputStreamReader(resource.getInputStream());
                 CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader().withDelimiter(';'))) {

                List<T> batch = new ArrayList<>();
                List<CompletableFuture<Void>> futures = new ArrayList<>();

                for (CSVRecord record : parser) {
                    try {
                        T entity = convertToEntity(record);
                        batch.add(entity);
                        totalProcessed.incrementAndGet();

                        if (batch.size() >= BATCH_SIZE) {
                            futures.add(asyncBatchService.persistBatchAsync(new ArrayList<>(batch), this));
                            batch.clear();
                        }
                    } catch (Exception e) {
                        totalFailed.incrementAndGet();
                        log.error("Erro ao processar a linha {}: {}", totalProcessed.get(), e.getMessage());
                    }
                }

                if (!batch.isEmpty()) {
                    futures.add(asyncBatchService.persistBatchAsync(new ArrayList<>(batch), this));
                }

                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

            } catch (Exception e) {
                log.error("Erro ao processar arquivo CSV {}: {}", resource.getFilename(), e.getMessage(), e);
                throw new RuntimeException("Falha ao processar arquivo CSV: " + resource.getFilename(), e);
            }
        }
    }


    protected void incrementPersisted(int count) {
        totalPersisted.addAndGet(count);
        log.info("Lote de {} registros persistido com sucesso. Total persistido: {}", count, totalPersisted.get());
    }

    private void logFinalResults() {
        log.info("Carregamento de {} finalizado.", getScriptName());
        log.info("Total de registros processados: {}", totalProcessed.get());
        log.info("Total de registros persistidos com sucesso: {}", totalPersisted.get());
        log.info("Total de registros com falha: {}", totalFailed.get());
    }
}