package br.com.brunogodoif.zipcodeaddressfinder.core.ports.inbound;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.http.viaCep.ViaCepAddresResponse;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.AddressDomain;

public interface PersistAddressServicePort {

    AddressDomain persist(ViaCepAddresResponse viaCepAddresResponse);

}
