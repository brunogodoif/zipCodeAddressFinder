package br.com.brunogodoif.zipcodeaddressfinder.core.ports.outbound;

import br.com.brunogodoif.zipcodeaddressfinder.core.domain.DistrictDomain;

import java.util.List;
import java.util.Optional;

public interface DistrictAdapterPort {

    Optional<DistrictDomain> getByDistrictName(String districtName);

    DistrictDomain persist(DistrictDomain districtDomain);

    void persistBatch(List<DistrictDomain> districtDomains);

    List<DistrictDomain> findAll();
}
