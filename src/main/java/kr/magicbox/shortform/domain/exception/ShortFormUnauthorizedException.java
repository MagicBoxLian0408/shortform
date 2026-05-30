package kr.magicbox.shortform.domain.exception;

import kr.magicbox.shortform.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ShortFormUnauthorizedException extends BusinessException {

    public ShortFormUnauthorizedException() {
        super("숏폼에 대한 권한이 없습니다.", HttpStatus.FORBIDDEN);
    }
}
