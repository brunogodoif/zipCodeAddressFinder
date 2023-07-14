package br.com.brunogodoif.zipcodeaddressfinder.adapters.inbound.controllers.dtos.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressSearchTo {

    private String address;
    private String district;
    private String city;
    private String state;

}
