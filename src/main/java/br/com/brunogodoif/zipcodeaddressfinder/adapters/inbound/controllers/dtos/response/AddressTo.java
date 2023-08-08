package br.com.brunogodoif.zipcodeaddressfinder.adapters.inbound.controllers.dtos.response;

import br.com.brunogodoif.zipcodeaddressfinder.commons.zipcode.ZipCodeUtil;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.AddressDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class AddressTo {

    private String zipCode;
    private String addressComplete;
    private String district;
    private String city;
    private String state;
    private String stateCapital;
    private String stateUf;
    private String region;
    private BigDecimal latitude;
    private BigDecimal longitude;

    public static AddressTo fromDomain(AddressDomain addressDomain) {
        return new AddressTo(
                ZipCodeUtil.applyMask(addressDomain.getZipCode()),
                addressDomain.getAddressComplete(),
                addressDomain.getDistrictDomain().getDistrict(),
                addressDomain.getCityDomain().getCity(),
                addressDomain.getStateDomain().getState(),
                addressDomain.getStateDomain().getCapital(),
                addressDomain.getStateDomain().getUf(),
                addressDomain.getRegionDomain().getRegion(),
                addressDomain.getLatitude(),
                addressDomain.getLongitude()
        );
    }
}
