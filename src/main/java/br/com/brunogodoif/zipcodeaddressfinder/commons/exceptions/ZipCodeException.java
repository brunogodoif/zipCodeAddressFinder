package br.com.brunogodoif.zipcodeaddressfinder.commons.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ZipCodeException extends RuntimeException {
    public ZipCodeException(String msg) {
        super(msg);
    }
}
