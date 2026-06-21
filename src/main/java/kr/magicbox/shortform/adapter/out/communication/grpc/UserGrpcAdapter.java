package kr.magicbox.shortform.adapter.out.communication.grpc;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.grpc.ManagedChannel;
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
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserGrpcAdapter implements UserNicknameQueryPort {

    @Qualifier("userManagedChannel")
    private final ManagedChannel userManagedChannel;

    @Override
    @CircuitBreaker(name = "userService", fallbackMethod = "getNicknamesBatchFallback")
    @TimeLimiter(name = "userService", fallbackMethod = "getNicknamesBatchFallback")
    public CompletableFuture<Map<Long, String>> getNicknamesBatch(List<Long> userIds) {
        GetUserNicknamesBatchRequest request = GetUserNicknamesBatchRequest.newBuilder()
                .addAllUserIds(userIds)
                .build();

        UserServiceGrpc.UserServiceFutureStub stub = UserServiceGrpc.newFutureStub(userManagedChannel);
        ListenableFuture<GetUserNicknamesBatchResponse> future = stub.getUserNicknamesBatch(request);

        return Futures.toCompletableFuture(
                Futures.transform(future, GetUserNicknamesBatchResponse::getNicknamesMap, MoreExecutors.directExecutor())
        );
    }

    @SuppressWarnings("unused")
    private CompletableFuture<Map<Long, String>> getNicknamesBatchFallback(List<Long> userIds, Throwable throwable) {
        log.warn("유저 서비스 연결 실패");
        return CompletableFuture.completedFuture(Collections.emptyMap());
    }
}
