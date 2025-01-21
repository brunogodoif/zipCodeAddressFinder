package br.com.brunogodoif.zipcodeaddressfinder.adapters.configs.dataloaders;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.RegionAdapterPortImpl;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.RegionDomain;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Log4j2
public class RegionDataLoader extends BaseDataLoader<RegionDomain> {

    @Autowired
    private RegionAdapterPortImpl regionAdapterPort;

    @Value("classpath:data/regions.csv")
    private Resource[] regionsCsvResource;

    @Override
    protected String getScriptName() {
        return "REGIONS";
    }

    @Override
    protected Resource[] getCsvResources() {
        return regionsCsvResource;
    }

    @Override
    protected RegionDomain convertToEntity(CSVRecord record) {
        return new RegionDomain(
                Integer.parseInt(record.get("id")),
                record.get("regiao"),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Override
    protected void persistBatch(List<RegionDomain> batch) {
        regionAdapterPort.persistBatch(batch);
        incrementPersisted(batch.size());
    }

    @Override
    protected void loadCaches() {
        // Não há dependências de cache para regiões
        log.info("Não há caches para carregar para REGIONS.");
    }

    @Override
    protected boolean validateCaches() {
        // Sempre válido, pois REGIONS não depende de outros caches
        return true;
    }
}
