package br.com.brunogodoif.zipcodeaddressfinder.adapters.configs.dataloaders;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.CityAdapterPortImpl;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.StateAdapterPortImpl;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.CityDomain;
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
public class CityDataLoader extends BaseDataLoader<CityDomain> {


    @Autowired private CityAdapterPortImpl cityAdapterPort;

    @Autowired private StateAdapterPortImpl stateAdapterPort;


    private Map<Integer, StateDomain> stateCache;

    @Value("classpath:data/cities.csv") private Resource[] citiesCsvResource;

    @Override protected String getScriptName() {
        return "CITIES";
    }

    @Override protected Resource[] getCsvResources() {
        return citiesCsvResource;
    }

    @Override protected CityDomain convertToEntity(CSVRecord record) {
        Integer cityId = Integer.parseInt(record.get("id"));
        Integer stateId = Integer.parseInt(record.get("id_estado"));

        StateDomain stateDomain = stateCache.get(stateId);
        if (stateDomain == null) {
            throw new IllegalArgumentException("Estado com ID " + stateId + " não encontrado no cache.");
        }

        return new CityDomain(cityId, record.get("cidade"), new BigDecimal(record.get("latitude")),
                              new BigDecimal(record.get("longitude")), Integer.parseInt(record.get("cod_ddd")),
                              stateDomain, LocalDateTime.now(), LocalDateTime.now());
    }

    @Override protected void persistBatch(List<CityDomain> batch) {
        cityAdapterPort.persistBatch(batch);
        incrementPersisted(batch.size());
    }

    @Override protected void loadCaches() {
        List<StateDomain> states = stateAdapterPort.findAll();
        if (!states.isEmpty()) {
            stateCache = states.stream().collect(Collectors.toMap(StateDomain::getId, state -> state));
            log.info("Cache de estados carregado: {} estados encontradas.", stateCache.size());
        }
    }

    @Override protected boolean validateCaches() {
        return stateCache != null && !stateCache.isEmpty();
    }
}