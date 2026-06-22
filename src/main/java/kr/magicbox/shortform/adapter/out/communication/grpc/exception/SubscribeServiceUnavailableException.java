package kr.magicbox.shortform.adapter.out.communication.grpc.exception;

import kr.magicbox.shortform.global.exception.SystemError;

public class SubscribeServiceUnavailableException extends SystemError {

    public SubscribeServiceUnavailableException(Throwable cause) {
        super("구독 서비스에 연결할 수 없습니다.", cause);
    }
}
