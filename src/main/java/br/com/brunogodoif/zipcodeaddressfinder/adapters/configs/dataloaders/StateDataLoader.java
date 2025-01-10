package br.com.brunogodoif.zipcodeaddressfinder.adapters.configs.dataloaders;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.StateAdapterPortImpl;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.RegionAdapterPortImpl;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.RegionDomain;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.StateDomain;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Log4j2
public class StateDataLoader extends BaseDataLoader<StateDomain> {

    @Autowired
    private StateAdapterPortImpl stateAdapterPort;

    @Autowired
    private RegionAdapterPortImpl regionAdapterPort;

    private Map<Integer, RegionDomain> regionCache;

    @Value("classpath:data/states.csv")
    private Resource statesCsvResource;

    @Override
    protected String getScriptName() {
        return "STATES";
    }

    @Override
    protected Resource getCsvResource() {
        return statesCsvResource;
    }

    @Override
    protected StateDomain convertToEntity(CSVRecord record) {
        Integer stateId = Integer.parseInt(record.get("id"));
        Integer regionId = Integer.parseInt(record.get("id_regiao"));

        RegionDomain regionDomain = regionCache.get(regionId);
        if (regionDomain == null) {
            throw new IllegalArgumentException("Região com ID " + regionId + " não encontrada no cache.");
        }

        return new StateDomain(
                stateId,
                record.get("estado"),
                record.get("capital"),
                record.get("uf"),
                new BigDecimal(record.get("latitude")),
                new BigDecimal(record.get("longitude")),
                regionDomain,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Override
    protected void persistBatch(List<StateDomain> batch) {
        stateAdapterPort.persistBatch(batch);
        incrementPersisted(batch.size());
    }

    @Override
    protected void loadCaches() {
        List<RegionDomain> regions = regionAdapterPort.findAll();
        if (!regions.isEmpty()) {
            regionCache = regions.stream().collect(Collectors.toMap(RegionDomain::getId, region -> region));
            log.info("Cache de regiões carregado: {} regiões encontradas.", regionCache.size());
        }
    }

    @Override
    protected boolean validateCaches() {
        return regionCache != null && !regionCache.isEmpty();
    }
}
