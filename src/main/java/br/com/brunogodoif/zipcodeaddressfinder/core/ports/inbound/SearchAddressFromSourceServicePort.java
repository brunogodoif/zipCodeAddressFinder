package br.com.brunogodoif.zipcodeaddressfinder.core.ports.inbound;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.http.viaCep.ViaCepAddresResponse;

public interface SearchAddressFromSourceServicePort {

    ViaCepAddresResponse search(String zipCode);

}
