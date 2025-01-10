package br.com.brunogodoif.zipcodeaddressfinder.core.services;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.http.viaCep.ViaCepAddresResponse;
import br.com.brunogodoif.zipcodeaddressfinder.commons.ZipCodeUtil;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.AddressDomain;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.pagination.PaginationRequest;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.pagination.PaginationResponse;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.request.AddressSearch;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.response.ListingAddressResponse;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.inbound.AddressServicePort;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.inbound.PersistAddressServicePort;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.inbound.SearchAddressFromSourceServicePort;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.outbound.AddressAdapterPort;
import br.com.brunogodoif.zipcodeaddressfinder.core.services.exception.AddressNotFoundException;
import br.com.brunogodoif.zipcodeaddressfinder.core.services.exception.ParamsNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;

import java.util.Optional;

public class AddressServicePortImpl implements AddressServicePort {

    private final AddressAdapterPort addressAdapterPort;
    private final SearchAddressFromSourceServicePort searchAddressFromSourceServicePort;
    private final PersistAddressServicePort persistAddressServicePort;

    public AddressServicePortImpl(AddressAdapterPort addressAdapterPort, SearchAddressFromSourceServicePort searchAddressFromSourceServicePort, PersistAddressServicePort persistAddressServicePort) {
        this.addressAdapterPort = addressAdapterPort;
        this.searchAddressFromSourceServicePort = searchAddressFromSourceServicePort;
        this.persistAddressServicePort = persistAddressServicePort;
    }

    @Override
    public ListingAddressResponse findAllAddress(AddressSearch addressSearch, PaginationRequest paginationRequest) {

        Page<AddressDomain> allAddress = this.addressAdapterPort.findAll(addressSearch, paginationRequest);
        PaginationResponse pagination = PaginationResponse.getInstance(paginationRequest, Math.toIntExact(allAddress.getTotalElements()));
        return new ListingAddressResponse(allAddress.getContent(), pagination);

    }

    @Override
    public AddressDomain findByZipCode(String zipCode) {

        if (StringUtils.isEmpty(zipCode)) {
            throw new ParamsNotFoundException("Param [zipCode] not found");
        }

        Optional<AddressDomain> byZipCode = this.addressAdapterPort.getByZipCode(ZipCodeUtil.removeMask(zipCode));

        if (byZipCode.isEmpty()) {

            ViaCepAddresResponse viaCepAddresFound = this.searchAddressFromSourceServicePort.search(zipCode);

            this.persistAddressServicePort.persist(viaCepAddresFound);

            return this.findByZipCode(zipCode);

        }

        return byZipCode.orElseThrow(() -> new AddressNotFoundException("Address for zip code [" + zipCode + "] not found"));

    }
}
