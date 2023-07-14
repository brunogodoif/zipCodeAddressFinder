package br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.http.viaCep.exception;

public class ErrorCallingViaCepException extends RuntimeException {
    public ErrorCallingViaCepException(String msg) {
        super(msg);
    }
}
