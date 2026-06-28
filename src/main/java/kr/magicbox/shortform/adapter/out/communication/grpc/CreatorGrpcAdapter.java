package kr.magicbox.shortform.adapter.out.communication.grpc;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.grpc.ManagedChannel;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import kr.magicbox.shortform.adapter.out.communication.grpc.exception.CreatorNotFoundException;
import kr.magicbox.shortform.adapter.out.communication.grpc.exception.CreatorServiceUnavailableException;
import kr.magicbox.shortform.application.port.out.CreatorIdQueryPort;
import kr.magicbox.shortform.application.port.out.CreatorNicknameQueryPort;
import kr.magicbox.shortform.application.port.out.CreatorProfileQueryPort;
import kr.magicbox.shortform.domain.vo.CreatorId;
import kr.magicbox.shortform.domain.vo.UserId;
import kr.magicbox.shortform.grpc.creator.CreatorServiceGrpc;
import kr.magicbox.shortform.grpc.creator.GetCreatorIdByUserIdRequest;
import kr.magicbox.shortform.grpc.creator.GetCreatorNicknameByCreatorIdRequest;
import kr.magicbox.shortform.grpc.creator.GetCreatorProfileByCreatorIdRequest;
import kr.magicbox.shortform.grpc.creator.GetCreatorProfilesBatchRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreatorGrpcAdapter implements CreatorIdQueryPort, CreatorNicknameQueryPort, CreatorProfileQueryPort {

    private final ManagedChannel creatorManagedChannel;

    @Override
    @CircuitBreaker(name = "creatorService", fallbackMethod = "getCreatorIdFallback")
    @TimeLimiter(name = "creatorService", fallbackMethod = "getCreatorIdFallback")
    public CompletableFuture<CreatorId> getCreatorId(UserId userId) {
        return GrpcFutures.toCompletable(
                CreatorServiceGrpc.newFutureStub(creatorManagedChannel).getCreatorIdByUserId(
                        GetCreatorIdByUserIdRequest.newBuilder().setUserId(userId.value()).build()
                )
        ).thenApply(response -> new CreatorId(response.getCreatorId()));
    }

    @Override
    @CircuitBreaker(name = "creatorService", fallbackMethod = "getCreatorNicknameFallback")
    @TimeLimiter(name = "creatorService", fallbackMethod = "getCreatorNicknameFallback")
    public CompletableFuture<String> getCreatorNickname(CreatorId creatorId) {
        return GrpcFutures.toCompletable(
                CreatorServiceGrpc.newFutureStub(creatorManagedChannel).getCreatorNicknameByCreatorId(
                        GetCreatorNicknameByCreatorIdRequest.newBuilder().setCreatorId(creatorId.value()).build()
                )
        ).thenApply(response -> response.getNickname());
    }

    @Override
    @CircuitBreaker(name = "creatorService", fallbackMethod = "getCreatorProfileFallback")
    @TimeLimiter(name = "creatorService", fallbackMethod = "getCreatorProfileFallback")
    public CompletableFuture<CreatorProfile> getCreatorProfile(CreatorId creatorId) {
        return GrpcFutures.toCompletable(
                CreatorServiceGrpc.newFutureStub(creatorManagedChannel).getCreatorProfileByCreatorId(
                        GetCreatorProfileByCreatorIdRequest.newBuilder().setCreatorId(creatorId.value()).build()
                )
        ).thenApply(response -> new CreatorProfile(
                response.getCreatorId(),
                response.getNickname(),
                response.getProfileImageUrl()
        ));
    }

    @Override
    @CircuitBreaker(name = "creatorService", fallbackMethod = "getCreatorProfilesBatchFallback")
    @TimeLimiter(name = "creatorService", fallbackMethod = "getCreatorProfilesBatchFallback")
    public CompletableFuture<Map<Long, CreatorProfile>> getCreatorProfilesBatch(List<CreatorId> creatorIds) {
        List<Long> ids = creatorIds.stream().map(CreatorId::value).toList();
        return GrpcFutures.toCompletable(
                CreatorServiceGrpc.newFutureStub(creatorManagedChannel).getCreatorProfilesBatch(
                        GetCreatorProfilesBatchRequest.newBuilder().addAllCreatorIds(ids).build()
                )
        ).thenApply(response -> response.getProfilesList().stream()
                .collect(Collectors.toMap(
                        p -> p.getCreatorId(),
                        p -> new CreatorProfile(p.getCreatorId(), p.getNickname(), p.getProfileImageUrl())
                ))
        );
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

    @SuppressWarnings("unused")
    private CompletableFuture<String> getCreatorNicknameFallback(CreatorId creatorId, Throwable throwable) {
        if (throwable instanceof StatusRuntimeException statusException
                && statusException.getStatus().getCode() == Status.Code.NOT_FOUND) {
            throw new CreatorNotFoundException();
        }
        log.warn("크리에이터 서비스 연결 실패");
        throw new CreatorServiceUnavailableException(throwable);
    }

    @SuppressWarnings("unused")
    private CompletableFuture<CreatorProfile> getCreatorProfileFallback(CreatorId creatorId, Throwable throwable) {
        if (throwable instanceof StatusRuntimeException statusException
                && statusException.getStatus().getCode() == Status.Code.NOT_FOUND) {
            throw new CreatorNotFoundException();
        }
        log.warn("크리에이터 서비스 연결 실패");
        throw new CreatorServiceUnavailableException(throwable);
    }

    @SuppressWarnings("unused")
    private CompletableFuture<Map<Long, CreatorProfile>> getCreatorProfilesBatchFallback(List<CreatorId> creatorIds, Throwable throwable) {
        if (throwable instanceof StatusRuntimeException statusException
                && statusException.getStatus().getCode() == Status.Code.NOT_FOUND) {
            throw new CreatorNotFoundException();
        }
        log.warn("크리에이터 서비스 연결 실패");
        throw new CreatorServiceUnavailableException(throwable);
    }
}
