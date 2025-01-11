package br.com.brunogodoif.zipcodeaddressfinder.core.ports.outbound;

import br.com.brunogodoif.zipcodeaddressfinder.core.domain.CityDomain;

import java.util.List;
import java.util.Optional;

public interface CityAdapterPort {

    Optional<CityDomain> getByCityName(String cityName);

    CityDomain persist(CityDomain cityDomain);

    void persistBatch(List<CityDomain> cityDomains);

    List<CityDomain> findAll();
}
