package kr.magicbox.shortform.domain.exception;

import kr.magicbox.shortform.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ShortFormLikeNotFoundException extends BusinessException {

    public ShortFormLikeNotFoundException() {
        super("좋아요를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    }
}
