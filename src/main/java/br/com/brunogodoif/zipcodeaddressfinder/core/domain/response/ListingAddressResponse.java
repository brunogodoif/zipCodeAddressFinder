package br.com.brunogodoif.zipcodeaddressfinder.core.domain.response;

import br.com.brunogodoif.zipcodeaddressfinder.core.domain.AddressDomain;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.pagination.PaginationResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class ListingAddressResponse {

    private List<AddressDomain> content;
    private PaginationResponse pagination;

    public ListingAddressResponse(List<AddressDomain> content, PaginationResponse pagination) {
        this.content = content;
        this.pagination = pagination;
    }
}
