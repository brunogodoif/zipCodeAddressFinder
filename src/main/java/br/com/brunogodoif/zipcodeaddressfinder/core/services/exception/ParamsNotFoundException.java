package br.com.brunogodoif.zipcodeaddressfinder.core.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ParamsNotFoundException extends RuntimeException {
    public ParamsNotFoundException(String msg) {
        super(msg);
    }
}
