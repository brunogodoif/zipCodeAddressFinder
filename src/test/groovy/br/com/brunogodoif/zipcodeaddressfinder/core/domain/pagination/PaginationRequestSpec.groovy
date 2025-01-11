package br.com.brunogodoif.zipcodeaddressfinder.core.domain.pagination

import br.com.brunogodoif.zipcodeaddressfinder.core.domain.pagination.exception.InvalidPageException
import spock.lang.Specification
import spock.lang.Unroll

class PaginationRequestSpec extends Specification {

    def "Should create a valid PaginationRequest"() {

        setup:
        def pageNumber = 1
        def pageSize = 10

        when:
        def paginationRequest = new PaginationRequest(pageNumber, pageSize)

        then:
        paginationRequest.getPageNumber() == pageNumber
        paginationRequest.getPageSize() == pageSize

    }

    @Unroll("#description")
    def "Should throw InvalidPageException when"() {
        when:
        create.call()

        then:
        def exception = thrown(InvalidPageException)
        exception.getMessage() == message

        where:
        description                      | create                             | message
        "for invalid page number with 0" | ({ new PaginationRequest(0, 10) }) | "The current page value [0] is invalid! Value must be greater than zero."
        "for invalid page size with 0"   | ({ new PaginationRequest(1, 0) })  | "The size value [0] is invalid! Value must be greater than zero and equal or less than one hundred."
    }

}
