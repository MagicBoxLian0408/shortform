package kr.magicbox.shortform.adapter.out.communication.grpc;

import com.google.common.util.concurrent.ListenableFuture;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.grpc.ManagedChannel;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import kr.magicbox.shortform.adapter.out.communication.grpc.exception.CreatorNotFoundException;
import kr.magicbox.shortform.adapter.out.communication.grpc.exception.CreatorServiceUnavailableException;
import kr.magicbox.shortform.application.port.out.CreatorIdQueryPort;
import kr.magicbox.shortform.domain.vo.CreatorId;
import kr.magicbox.shortform.domain.vo.UserId;
import kr.magicbox.shortform.grpc.creator.CreatorServiceGrpc;
import kr.magicbox.shortform.grpc.creator.GetCreatorIdByUserIdRequest;
import kr.magicbox.shortform.grpc.creator.GetCreatorIdByUserIdResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreatorGrpcAdapter implements CreatorIdQueryPort {

    private final ManagedChannel creatorManagedChannel;

    @Override
    @CircuitBreaker(name = "creatorService", fallbackMethod = "getCreatorIdFallback")
    @TimeLimiter(name = "creatorService", fallbackMethod = "getCreatorIdFallback")
    public CompletableFuture<CreatorId> getCreatorId(UserId userId) {
        GetCreatorIdByUserIdRequest request = GetCreatorIdByUserIdRequest.newBuilder()
                .setUserId(userId.value())
                .build();

        CreatorServiceGrpc.CreatorServiceFutureStub stub = CreatorServiceGrpc.newFutureStub(creatorManagedChannel);
        ListenableFuture<GetCreatorIdByUserIdResponse> future = stub.getCreatorIdByUserId(request);

        CompletableFuture<CreatorId> result = new CompletableFuture<>();
        future.addListener(() -> {
            try {
                result.complete(new CreatorId(future.get().getCreatorId()));
            } catch (Exception e) {
                result.completeExceptionally(e);
            }
        }, Runnable::run);
        return result;
    }

    @SuppressWarnings("unused")
    private CompletableFuture<CreatorId> getCreatorIdFallback(UserId userId, Throwable throwable) {
        if (throwable instanceof StatusRuntimeException statusException
                && statusException.getStatus().getCode() == Status.Code.NOT_FOUND) {
            throw new CreatorNotFoundException();
        }
        log.warn("크리에이터 서비스 연결 실패");
        throw new CreatorServiceUnavailableException(throwable);
    }
}
