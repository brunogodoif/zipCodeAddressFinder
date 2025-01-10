package br.com.brunogodoif.zipcodeaddressfinder.adapters.configs.dataloaders;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.*;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.*;
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
public class AddressDataLoader extends BaseDataLoader<AddressDomain> {

    @Value("classpath:data/address.csv") private Resource addressesCsvResource;

    @Autowired private RegionAdapterPortImpl regionAdapterPort;
    @Autowired private StateAdapterPortImpl stateAdapterPort;
    @Autowired private CityAdapterPortImpl cityAdapterPort;
    @Autowired private DistrictAdapterPortImpl districtAdapterPort;
    @Autowired private AddressAdapterPortImpl addressAdapterPort;

    private Map<Integer, RegionDomain> regionCache;
    private Map<Integer, StateDomain> stateCache;
    private Map<Integer, CityDomain> cityCache;
    private Map<Integer, DistrictDomain> districtCache;

    @Override protected String getScriptName() {
        return "ADDRESS";
    }

    @Override protected Resource getCsvResource() {
        return addressesCsvResource;
    }


    @Override protected AddressDomain convertToEntity(CSVRecord record) {
        String zipCode = record.get("cep");
        String addressComplete = record.get("endereco");
        String typeAddress = record.get("tipo");
        String address = record.get("logradouro");
        Integer districtId = Integer.parseInt(record.get("id_bairro"));
        Integer cityId = Integer.parseInt(record.get("id_cidade"));
        Integer stateId = Integer.parseInt(record.get("id_estado"));
        Integer regionId = Integer.parseInt(record.get("id_regiao"));
        double latitude = Double.parseDouble(record.get("latitude"));
        double longitude = Double.parseDouble(record.get("longitude"));
        Boolean active = Boolean.parseBoolean(record.get("ativo"));

        RegionDomain regionDomain = regionCache.get(regionId);
        StateDomain stateDomain = stateCache.get(stateId);
        CityDomain cityDomain = cityCache.get(cityId);
        DistrictDomain districtDomain = districtCache.get(districtId);


        // Criar e retornar o objeto AddressDomain
        return new AddressDomain(zipCode, addressComplete, typeAddress, address, districtDomain, cityDomain,
                                 stateDomain, regionDomain, BigDecimal.valueOf(latitude), BigDecimal.valueOf(longitude),
                                 active, LocalDateTime.now(), LocalDateTime.now());
    }

    @Override protected void persistBatch(List<AddressDomain> batch) {
        addressAdapterPort.persistBatch(batch);
        incrementPersisted(batch.size());
    }


    @Override protected void loadCaches() {
        List<RegionDomain> regions = regionAdapterPort.findAll();
        if (regions.isEmpty()) {
            log.error("Não foram encontrados regiões no banco. Encerrando execução.");
            regionCache = null;
        } else {
            regionCache = regions.stream().collect(Collectors.toMap(RegionDomain::getId, region -> region));
            log.info("Cache de regiões carregado: {} regiões encontrados.", regionCache.size());
        }

        List<StateDomain> states = stateAdapterPort.findAll();
        if (states.isEmpty()) {
            log.error("Não foram encontrados estados no banco. Encerrando execução.");
            stateCache = null;
        } else {
            stateCache = states.stream().collect(Collectors.toMap(StateDomain::getId, state -> state));
            log.info("Cache de estados carregado: {} estados encontrados.", stateCache.size());
        }

        List<CityDomain> cities = cityAdapterPort.findAll();
        if (!cities.isEmpty()) {
            cityCache = cities.stream().collect(Collectors.toMap(CityDomain::getId, city -> city));
            log.info("Cache de cidades carregado: {} cidades encontradas.", cityCache.size());
        }

        List<DistrictDomain> districts = districtAdapterPort.findAll();
        if (!districts.isEmpty()) {
            districtCache = districts.stream().collect(Collectors.toMap(DistrictDomain::getId, district -> district));
            log.info("Cache de cidades carregado: {} cidades encontradas.", districtCache.size());
        }

    }

    @Override protected boolean validateCaches() {
        return cityCache != null && !cityCache.isEmpty();
    }

}