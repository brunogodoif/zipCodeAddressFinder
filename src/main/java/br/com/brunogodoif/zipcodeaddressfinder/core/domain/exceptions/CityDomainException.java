package br.com.brunogodoif.zipcodeaddressfinder.core.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CityDomainException extends RuntimeException {
    public CityDomainException(String msg) {
        super(msg);
    }
}
