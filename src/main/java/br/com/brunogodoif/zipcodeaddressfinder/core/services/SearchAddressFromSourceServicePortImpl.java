package br.com.brunogodoif.zipcodeaddressfinder.core.services;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.http.viaCep.ViaCepAddresResponse;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.http.viaCep.ViaCepClient;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.http.viaCep.exception.AddresssNotFoundInSourceException;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.http.viaCep.exception.ErrorCallingViaCepException;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.inbound.SearchAddressFromSourceServicePort;
import br.com.brunogodoif.zipcodeaddressfinder.core.services.exception.AddressNotFoundInSourceException;
import org.springframework.stereotype.Component;

import java.net.http.HttpClient;

@Component
public class SearchAddressFromSourceServicePortImpl implements SearchAddressFromSourceServicePort {

    @Override
    public ViaCepAddresResponse search(String zipCode) {
        try {
            ViaCepClient viaCepClient = new ViaCepClient(HttpClient.newHttpClient());
            return viaCepClient.findByZipCode(zipCode);
        } catch (ErrorCallingViaCepException | AddresssNotFoundInSourceException e) {
            throw new AddressNotFoundInSourceException(e.getMessage());
        }
    }
}
