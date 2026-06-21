package kr.magicbox.shortform.adapter.out.communication.grpc;

import com.google.common.util.concurrent.ListenableFuture;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.grpc.ManagedChannel;
import kr.magicbox.shortform.adapter.out.communication.grpc.exception.SubscribeServiceUnavailableException;
import kr.magicbox.shortform.application.port.out.SubscribedCreatorIdsQueryPort;
import kr.magicbox.shortform.domain.vo.UserId;
import kr.magicbox.shortform.grpc.subscribe.GetSubscribedCreatorIdsRequest;
import kr.magicbox.shortform.grpc.subscribe.GetSubscribedCreatorIdsResponse;
import kr.magicbox.shortform.grpc.subscribe.SubscribeServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubscribeGrpcAdapter implements SubscribedCreatorIdsQueryPort {

    @Qualifier("subscribeManagedChannel")
    private final ManagedChannel subscribeManagedChannel;

    @Override
    @Cacheable(value = "subscribed-creators", key = "#userId.value()")
    @CircuitBreaker(name = "subscribeService", fallbackMethod = "getSubscribedCreatorIdsFallback")
    @TimeLimiter(name = "subscribeService", fallbackMethod = "getSubscribedCreatorIdsFallback")
    public CompletableFuture<List<Long>> getSubscribedCreatorIds(UserId userId) throws Exception {
        GetSubscribedCreatorIdsRequest request = GetSubscribedCreatorIdsRequest.newBuilder()
                .setUserId(userId.value())
                .build();

        SubscribeServiceGrpc.SubscribeServiceFutureStub stub = SubscribeServiceGrpc.newFutureStub(subscribeManagedChannel);
        ListenableFuture<GetSubscribedCreatorIdsResponse> future = stub.getSubscribedCreatorIds(request);
        GetSubscribedCreatorIdsResponse response = future.get();

        return CompletableFuture.completedFuture(response.getCreatorIdsList());
    }

    @SuppressWarnings("unused")
    private CompletableFuture<List<Long>> getSubscribedCreatorIdsFallback(UserId userId, Throwable throwable) {
        log.warn("구독 서비스 연결 실패");
        throw new SubscribeServiceUnavailableException(throwable);
    }
}
