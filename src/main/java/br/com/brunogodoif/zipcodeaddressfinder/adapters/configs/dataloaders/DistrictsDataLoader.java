package br.com.brunogodoif.zipcodeaddressfinder.adapters.configs.dataloaders;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.CityAdapterPortImpl;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.DistrictAdapterPortImpl;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.CityDomain;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.DistrictDomain;
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
public class DistrictsDataLoader extends BaseDataLoader<DistrictDomain> {

    @Value("classpath:data/districts.csv") private Resource districtsCsvResource;

    @Autowired private DistrictAdapterPortImpl districtAdapterPort;

    @Autowired private CityAdapterPortImpl cityAdapterPort;

    private Map<Integer, CityDomain> cityCache;

    @Override protected String getScriptName() {
        return "DISTRICTS";
    }

    @Override protected Resource getCsvResource() {
        return districtsCsvResource;
    }

    @Override protected DistrictDomain convertToEntity(CSVRecord record) {
        Integer districtId = Integer.parseInt(record.get("id"));
        String districtName = record.get("bairro");
        BigDecimal latitude = new BigDecimal(record.get("latitude"));
        BigDecimal longitude = new BigDecimal(record.get("longitude"));
        Integer cityId = Integer.parseInt(record.get("id_cidade"));

        CityDomain city = cityCache.get(cityId);
        if (city == null) {
            throw new IllegalStateException("Cidade com ID " + cityId + " não encontrada no cache.");
        }

        return new DistrictDomain(districtId, districtName, latitude, longitude, city, LocalDateTime.now(),
                                  LocalDateTime.now());
    }

    @Override protected void persistBatch(List<DistrictDomain> batch) {
        districtAdapterPort.persistBatch(batch);
        incrementPersisted(batch.size());
    }

    @Override protected void loadCaches() {
        List<CityDomain> cities = cityAdapterPort.findAll();
        if (!cities.isEmpty()) {
            cityCache = cities.stream().collect(Collectors.toMap(CityDomain::getId, city -> city));
            log.info("Cache de cidades carregado: {} cidades encontradas.", cityCache.size());
        }
    }

    @Override protected boolean validateCaches() {
        return cityCache != null && !cityCache.isEmpty();
    }
}