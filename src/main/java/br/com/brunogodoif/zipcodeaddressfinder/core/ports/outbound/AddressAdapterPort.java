package br.com.brunogodoif.zipcodeaddressfinder.core.ports.outbound;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.AddressEntity;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.AddressDomain;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.CityDomain;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.pagination.PaginationRequest;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.request.AddressSearch;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface AddressAdapterPort {

    Page<AddressDomain> findAll(AddressSearch addressSearch, PaginationRequest paginationRequest);

    Optional<AddressDomain> getByZipCode(String zipCode);

    AddressDomain persist(AddressDomain addressDomain);

    void persistBatch(List<AddressDomain> addressDomains);

}
