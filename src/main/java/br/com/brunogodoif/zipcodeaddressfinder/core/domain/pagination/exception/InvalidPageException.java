package br.com.brunogodoif.zipcodeaddressfinder.core.domain.pagination.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPageException extends RuntimeException {
    public InvalidPageException(String msg) {
        super(msg);
    }
}
