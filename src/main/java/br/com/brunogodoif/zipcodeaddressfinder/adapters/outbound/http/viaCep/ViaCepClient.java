package br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.http.viaCep;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.http.viaCep.exception.AddresssNotFoundInSourceException;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.http.viaCep.exception.ErrorCallingViaCepException;
import br.com.brunogodoif.zipcodeaddressfinder.commons.ZipCodeUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
public class ViaCepClient {

    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/";

    private final HttpClient httpClient;

    public ViaCepClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public ViaCepAddresResponse findByZipCode(String cepString) {
        ZipCodeUtil.validate(cepString);
        try {
            log.info("[VIA CEP] - [ZIP CODE: {}]", cepString);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(VIA_CEP_URL + cepString + "/json"))
                    .build();

            HttpResponse<String> httpResponse = httpClient
                    .send(httpRequest, HttpResponse.BodyHandlers.ofString());

            log.info("[VIA CEP] - [RESPONSE: {}]", httpResponse.body());
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode responseJson = objectMapper.readTree(httpResponse.body());

            if (responseJson.has("erro") && responseJson.get("erro").asBoolean()) {
                throw new AddresssNotFoundInSourceException("Error in ViaCep API response or zipCode not found: " + responseJson);
            }

            return objectMapper.readValue(httpResponse.body(), ViaCepAddresResponse.class);

        } catch (IOException e) {
            log.error("[VIA CEP] - [ERROR: {}]", e.getMessage());
            throw new ErrorCallingViaCepException("Error while calling ViaCep API: " + e.getMessage());
        } catch (InterruptedException e) {
            log.error("[VIA CEP] - [INTERRUPTED ERROR: {}]", e.getMessage());
            Thread.currentThread().interrupt();
            throw new ErrorCallingViaCepException("Error while calling ViaCep API: " + e.getMessage());
        }
    }
}