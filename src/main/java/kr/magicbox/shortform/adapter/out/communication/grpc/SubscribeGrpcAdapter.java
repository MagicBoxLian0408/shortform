package kr.magicbox.shortform.adapter.out.communication.grpc;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.grpc.ManagedChannel;
import kr.magicbox.shortform.adapter.out.communication.grpc.exception.SubscribeServiceUnavailableException;
import kr.magicbox.shortform.application.port.out.SubscribedCreatorIdsQueryPort;
import kr.magicbox.shortform.domain.vo.UserId;
import kr.magicbox.shortform.grpc.subscribe.GetSubscribedCreatorIdsRequest;
import kr.magicbox.shortform.grpc.subscribe.SubscribeServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubscribeGrpcAdapter implements SubscribedCreatorIdsQueryPort {

    @Qualifier("subscribeManagedChannel")
    private final ManagedChannel subscribeManagedChannel;

    @Override
    @Cacheable(value = "subscribed-creators", key = "#userId.value()")
    @CircuitBreaker(name = "subscribeService", fallbackMethod = "getSubscribedCreatorIdsFallback")
    @TimeLimiter(name = "subscribeService", fallbackMethod = "getSubscribedCreatorIdsFallback")
    public CompletableFuture<List<Long>> getSubscribedCreatorIds(UserId userId) {
        return GrpcFutures.toCompletable(
                SubscribeServiceGrpc.newFutureStub(subscribeManagedChannel).getSubscribedCreatorIds(
                        GetSubscribedCreatorIdsRequest.newBuilder().setUserId(userId.value()).build()
                )
        ).thenApply(response -> response.getCreatorIdsList());
    }

    @SuppressWarnings("unused")
    private CompletableFuture<List<Long>> getSubscribedCreatorIdsFallback(UserId userId, Throwable throwable) {
        log.warn("구독 서비스 연결 실패");
        throw new SubscribeServiceUnavailableException(throwable);
    }
}
