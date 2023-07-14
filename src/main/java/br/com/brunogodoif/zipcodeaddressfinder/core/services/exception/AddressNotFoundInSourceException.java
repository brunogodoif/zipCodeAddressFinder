package br.com.brunogodoif.zipcodeaddressfinder.core.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AddressNotFoundInSourceException extends RuntimeException {
    public AddressNotFoundInSourceException(String msg) {
        super(msg);
    }
}