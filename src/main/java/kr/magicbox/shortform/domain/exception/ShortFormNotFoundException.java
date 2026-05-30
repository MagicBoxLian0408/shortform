package kr.magicbox.shortform.domain.exception;

import kr.magicbox.shortform.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ShortFormNotFoundException extends BusinessException {

    public ShortFormNotFoundException() {
        super("숏폼을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    }
}
