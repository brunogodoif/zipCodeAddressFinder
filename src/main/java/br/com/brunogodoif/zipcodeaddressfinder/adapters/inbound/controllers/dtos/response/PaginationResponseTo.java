package br.com.brunogodoif.zipcodeaddressfinder.adapters.inbound.controllers.dtos.response;


import br.com.brunogodoif.zipcodeaddressfinder.core.domain.pagination.PaginationResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaginationResponseTo {

    private final Integer size;
    private final Integer totalPages;
    private final Integer totalElements;
    private final Integer currentPage;
    private final Integer nextPage;
    private final Integer previousPage;
    private final Boolean hasNext;
    private final Boolean hasPrevious;

    public static PaginationResponseTo fromDomain(PaginationResponse paginationResponse) {
        return new PaginationResponseTo(
                paginationResponse.getPageSize(),
                paginationResponse.getTotalPages(),
                paginationResponse.getTotalElements(),
                paginationResponse.getCurrentPage(),
                paginationResponse.getNextPage(),
                paginationResponse.getPreviousPage(),
                paginationResponse.getHasNextPage(),
                paginationResponse.getHasPreviousPage()
        );
    }
}