package kr.magicbox.shortform.global.exception;

import org.springframework.http.HttpStatus;

public class SystemError extends BaseException {

    public SystemError(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public SystemError(String message, Throwable cause) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR, cause);
    }
}
