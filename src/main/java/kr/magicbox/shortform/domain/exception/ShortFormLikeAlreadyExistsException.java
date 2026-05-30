package kr.magicbox.shortform.domain.exception;

import kr.magicbox.shortform.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ShortFormLikeAlreadyExistsException extends BusinessException {

    public ShortFormLikeAlreadyExistsException() {
        super("이미 좋아요를 눌렀습니다.", HttpStatus.CONFLICT);
    }
}
