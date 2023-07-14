package br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.http.viaCep.exception;

public class AddresssNotFoundInSourceException extends RuntimeException {
    public AddresssNotFoundInSourceException(String msg) {
        super(msg);
    }
}
