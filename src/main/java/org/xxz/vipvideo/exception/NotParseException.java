package org.xxz.vipvideo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NotParseException extends RuntimeException {

    public NotParseException(String message) {
        super(message);
    }
}
