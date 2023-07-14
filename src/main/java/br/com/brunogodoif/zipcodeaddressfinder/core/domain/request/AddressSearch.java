package br.com.brunogodoif.zipcodeaddressfinder.core.domain.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressSearch {
    private String address;
    private String district;
    private String city;
    private String state;
}
