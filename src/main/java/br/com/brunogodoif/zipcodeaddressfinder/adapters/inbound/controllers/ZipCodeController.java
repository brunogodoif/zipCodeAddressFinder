package br.com.brunogodoif.zipcodeaddressfinder.adapters.inbound.controllers;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.inbound.controllers.dtos.request.AddressSearchTo;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.inbound.controllers.dtos.response.AddressTo;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.inbound.controllers.dtos.response.ListingAddressResponseTo;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.pagination.PaginationRequest;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.request.AddressSearch;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.inbound.AddressServicePort;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/address")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ZipCodeController {

    private final AddressServicePort addressServicePort;

    public ZipCodeController(AddressServicePort addressServicePort) {
        this.addressServicePort = addressServicePort;
    }

    @GetMapping()
    public ResponseEntity<ListingAddressResponseTo> findZipCodeByAddress(
            @ModelAttribute AddressSearchTo AddressSearchTo,
            @PageableDefault(page = 1, size = 15, sort = "address",
                    direction = Sort.Direction.ASC) Pageable pageable
    ) {
        log.info("Receiving request to find address by parameters [{}]", AddressSearchTo);

        var paginationRequest = new PaginationRequest(pageable.getPageNumber(), pageable.getPageSize());

        var addressSearch = new AddressSearch();
        BeanUtils.copyProperties(AddressSearchTo, addressSearch);

        var listingAddressResponse = this.addressServicePort.findAllAddress(addressSearch, paginationRequest);

        return ResponseEntity.ok(ListingAddressResponseTo.fromDomain(listingAddressResponse));
    }

    @GetMapping("zipcode/{zipCodeId}")
    public ResponseEntity<Object> findZipCode(@PathVariable(value = "zipCodeId") String zipCodeId) {
        log.info("Receiving request to find address by zipCode [zipCode: {}]", zipCodeId);

        var addressDomain = this.addressServicePort.findByZipCode(zipCodeId);
        return ResponseEntity.ok(AddressTo.fromDomain(addressDomain));
    }

}
