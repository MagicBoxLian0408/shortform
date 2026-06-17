package kr.magicbox.shortform.adapter.out.communication.grpc;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import kr.magicbox.shortform.adapter.out.communication.grpc.exception.CreatorServiceUnavailableException;
import kr.magicbox.shortform.application.port.out.UserNicknameQueryPort;
import kr.magicbox.shortform.grpc.user.GetUserNicknamesBatchRequest;
import kr.magicbox.shortform.grpc.user.GetUserNicknamesBatchResponse;
import kr.magicbox.shortform.grpc.user.UserServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserGrpcAdapter implements UserNicknameQueryPort {

    @Qualifier("userManagedChannel")
    private final ManagedChannel userManagedChannel;

    @Override
    @CircuitBreaker(name = "userService", fallbackMethod = "getNicknamesBatchFallback")
    public Map<Long, String> getNicknamesBatch(List<Long> userIds) {
        GetUserNicknamesBatchRequest request = GetUserNicknamesBatchRequest.newBuilder()
                .addAllUserIds(userIds)
                .build();

        UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(userManagedChannel)
                .withDeadlineAfter(2, TimeUnit.SECONDS);
        GetUserNicknamesBatchResponse response = stub.getUserNicknamesBatch(request);

        return response.getNicknamesMap();
    }

    @SuppressWarnings("unused")
    private Map<Long, String> getNicknamesBatchFallback(List<Long> userIds, Throwable throwable) {
        log.warn("유저 서비스 연결 실패");
        if (throwable instanceof StatusRuntimeException) {
            return Collections.emptyMap();
        }
        throw new CreatorServiceUnavailableException(throwable);
    }
}
