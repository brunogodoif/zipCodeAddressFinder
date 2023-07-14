package br.com.brunogodoif.zipcodeaddressfinder.core.ports.inbound;

import br.com.brunogodoif.zipcodeaddressfinder.core.domain.AddressDomain;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.pagination.PaginationRequest;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.request.AddressSearch;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.response.ListingAddressResponse;

public interface AddressServicePort {

    ListingAddressResponse findAllAddress(AddressSearch addressSearch, PaginationRequest paginationRequest);

    AddressDomain findByZipCode(String zipCode);
}
