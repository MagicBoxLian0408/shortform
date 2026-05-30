package kr.magicbox.shortform.domain.exception;

import kr.magicbox.shortform.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class CommentUnauthorizedException extends BusinessException {

    public CommentUnauthorizedException() {
        super("댓글에 대한 권한이 없습니다.", HttpStatus.FORBIDDEN);
    }
}
