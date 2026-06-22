package kr.magicbox.shortform.adapter.out.communication.grpc;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.grpc.ManagedChannel;
import kr.magicbox.shortform.application.port.out.UserNicknameQueryPort;
import kr.magicbox.shortform.grpc.user.GetUserNicknamesBatchRequest;
import kr.magicbox.shortform.grpc.user.UserServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserGrpcAdapter implements UserNicknameQueryPort {

    @Qualifier("userManagedChannel")
    private final ManagedChannel userManagedChannel;

    @Override
    @CircuitBreaker(name = "userService", fallbackMethod = "getNicknamesBatchFallback")
    @TimeLimiter(name = "userService", fallbackMethod = "getNicknamesBatchFallback")
    public CompletableFuture<Map<Long, String>> getNicknamesBatch(List<Long> userIds) {
        return GrpcFutures.toCompletable(
                UserServiceGrpc.newFutureStub(userManagedChannel).getUserNicknamesBatch(
                        GetUserNicknamesBatchRequest.newBuilder().addAllUserIds(userIds).build()
                )
        ).thenApply(response -> response.getNicknamesMap());
    }

    @SuppressWarnings("unused")
    private CompletableFuture<Map<Long, String>> getNicknamesBatchFallback(List<Long> userIds, Throwable throwable) {
        log.warn("유저 서비스 연결 실패");
        return CompletableFuture.completedFuture(Collections.emptyMap());
    }
}
