package kr.magicbox.shortform.adapter.out.communication.grpc.exception;

import kr.magicbox.shortform.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class CreatorServiceUnavailableException extends BaseException {

    public CreatorServiceUnavailableException(Throwable cause) {
        super("크리에이터 서비스에 연결할 수 없습니다.", HttpStatus.SERVICE_UNAVAILABLE, cause);
    }
}
