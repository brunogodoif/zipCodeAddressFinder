package br.com.brunogodoif.zipcodeaddressfinder.adapters.inbound.controllers.dtos.response;

import br.com.brunogodoif.zipcodeaddressfinder.core.domain.response.ListingAddressResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor

public class ListingAddressResponseTo {

    private List<AddressTo> content;
    private PaginationResponseTo pagination;

    public static ListingAddressResponseTo fromDomain(ListingAddressResponse listingAddressResponse) {

        return new ListingAddressResponseTo(listingAddressResponse.getContent().stream()
                .map(AddressTo::fromDomain)
                .collect(Collectors.toList()),
                PaginationResponseTo.fromDomain(listingAddressResponse.getPagination())
        );

    }
}
