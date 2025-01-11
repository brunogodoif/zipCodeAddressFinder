package br.com.brunogodoif.zipcodeaddressfinder.core.ports.outbound;

import br.com.brunogodoif.zipcodeaddressfinder.core.domain.RegionDomain;

import java.util.List;
import java.util.Optional;

public interface RegionAdapterPort {

    Optional<RegionDomain> getByUf(String stateUf);

    RegionDomain persist(RegionDomain stateDomain);

    List<RegionDomain> findAll();

    void persistBatch(List<RegionDomain> regionDomains);

}
