package br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.http.viaCep

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.http.viaCep.exception.AddresssNotFoundInSourceException
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.http.viaCep.exception.ErrorCallingViaCepException
import org.mockito.Mockito
import spock.lang.Specification

import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class ViaCepClientSpec extends Specification {

    def "test findByZipCode with successful response"() {
        setup:
        HttpResponse mockHttpResponse = Mock(HttpResponse.class);
        HttpClient mockHttpClient = Mock(HttpClient.class);

        mockHttpResponse.body() >> '{"cep":"12345-678", "logradouro": "Mock Street"}'
        mockHttpClient.send(_ as HttpRequest, HttpResponse.BodyHandlers.ofString()) >> mockHttpResponse

        when:
        ViaCepClient viaCepClient = new ViaCepClient(mockHttpClient)
        def result = viaCepClient.findByZipCode("12345-678")

        then:
        result.cep == "12345-678"
        result.logradouro == "Mock Street"
    }

    def "Should throw AddresssNotFoundInSourceException when address not found in api"() {

        setup:
        HttpResponse mockHttpResponse = Mock(HttpResponse.class);
        HttpClient mockHttpClient = Mock(HttpClient.class);

        mockHttpResponse.body() >> '{"erro":true}'
        mockHttpClient.send(_ as HttpRequest, HttpResponse.BodyHandlers.ofString()) >> mockHttpResponse

        when:
        ViaCepClient viaCepClient = new ViaCepClient(mockHttpClient)
        viaCepClient.findByZipCode("12345-678")

        then:
        AddresssNotFoundInSourceException exception = thrown(AddresssNotFoundInSourceException)
        exception.message == "Error in ViaCep API response or zipCode not found: " + mockHttpResponse.body()
    }

    def "Should throw ErrorCallingViaCepException when erro call api"() {

        setup:
        HttpClient mockHttpClient = Mockito.mock(HttpClient.class);

        Mockito.when(mockHttpClient
                .send(Mockito.any(HttpRequest.class) as HttpRequest,
                        Mockito.any(HttpResponse.BodyHandler.class) as HttpResponse.BodyHandler<Object>))
                .thenThrow(new IOException("Error"))

        when:
        ViaCepClient viaCepClient = new ViaCepClient(mockHttpClient)
        viaCepClient.findByZipCode("12345-678")

        then:
        ErrorCallingViaCepException exception = thrown(ErrorCallingViaCepException)
        exception.message == "Error while calling ViaCep API: Error"
    }
}

