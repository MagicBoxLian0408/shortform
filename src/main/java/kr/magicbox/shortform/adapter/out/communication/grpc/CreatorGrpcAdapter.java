package kr.magicbox.shortform.adapter.out.communication.grpc;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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
import kr.magicbox.shortform.grpc.creator.GetCreatorIdByUserIdResponse;
import kr.magicbox.shortform.grpc.creator.GetCreatorNicknameByCreatorIdRequest;
import kr.magicbox.shortform.grpc.creator.GetCreatorNicknameByCreatorIdResponse;
import kr.magicbox.shortform.grpc.creator.GetCreatorProfileByCreatorIdRequest;
import kr.magicbox.shortform.grpc.creator.GetCreatorProfileByCreatorIdResponse;
import kr.magicbox.shortform.grpc.creator.GetCreatorProfilesBatchRequest;
import kr.magicbox.shortform.grpc.creator.GetCreatorProfilesBatchResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreatorGrpcAdapter implements CreatorIdQueryPort, CreatorNicknameQueryPort, CreatorProfileQueryPort {

    private final ManagedChannel creatorManagedChannel;

    @Override
    @CircuitBreaker(name = "creatorService", fallbackMethod = "getCreatorIdFallback")
    public CreatorId getCreatorId(UserId userId) {
        GetCreatorIdByUserIdRequest request = GetCreatorIdByUserIdRequest.newBuilder()
                .setUserId(userId.value())
                .build();

        CreatorServiceGrpc.CreatorServiceBlockingStub stub = CreatorServiceGrpc.newBlockingStub(creatorManagedChannel)
                .withDeadlineAfter(2, TimeUnit.SECONDS);
        GetCreatorIdByUserIdResponse response = stub.getCreatorIdByUserId(request);

        return new CreatorId(response.getCreatorId());
    }

    @SuppressWarnings("unused")
    private CreatorId getCreatorIdFallback(UserId userId, Throwable throwable) {
        if (throwable instanceof StatusRuntimeException statusException
                && statusException.getStatus().getCode() == Status.Code.NOT_FOUND) {
            throw new CreatorNotFoundException();
        }
        log.warn("크리에이터 서비스 연결 실패");
        throw new CreatorServiceUnavailableException(throwable);
    }

    @Override
    @CircuitBreaker(name = "creatorService", fallbackMethod = "getCreatorNicknameFallback")
    public String getCreatorNickname(CreatorId creatorId) {
        GetCreatorNicknameByCreatorIdRequest request = GetCreatorNicknameByCreatorIdRequest.newBuilder()
                .setCreatorId(creatorId.value())
                .build();

        CreatorServiceGrpc.CreatorServiceBlockingStub stub = CreatorServiceGrpc.newBlockingStub(creatorManagedChannel)
                .withDeadlineAfter(2, TimeUnit.SECONDS);
        GetCreatorNicknameByCreatorIdResponse response = stub.getCreatorNicknameByCreatorId(request);

        return response.getNickname();
    }

    @SuppressWarnings("unused")
    private String getCreatorNicknameFallback(CreatorId creatorId, Throwable throwable) {
        if (throwable instanceof StatusRuntimeException statusException
                && statusException.getStatus().getCode() == Status.Code.NOT_FOUND) {
            throw new CreatorNotFoundException();
        }
        log.warn("크리에이터 서비스 연결 실패");
        throw new CreatorServiceUnavailableException(throwable);
    }

    @Override
    @CircuitBreaker(name = "creatorService", fallbackMethod = "getCreatorProfileFallback")
    public CreatorProfile getCreatorProfile(CreatorId creatorId) {
        GetCreatorProfileByCreatorIdRequest request = GetCreatorProfileByCreatorIdRequest.newBuilder()
                .setCreatorId(creatorId.value())
                .build();

        CreatorServiceGrpc.CreatorServiceBlockingStub stub = CreatorServiceGrpc.newBlockingStub(creatorManagedChannel)
                .withDeadlineAfter(2, TimeUnit.SECONDS);
        GetCreatorProfileByCreatorIdResponse response = stub.getCreatorProfileByCreatorId(request);

        return new CreatorProfile(response.getCreatorId(), response.getNickname(), response.getProfileImageUrl());
    }

    @SuppressWarnings("unused")
    private CreatorProfile getCreatorProfileFallback(CreatorId creatorId, Throwable throwable) {
        if (throwable instanceof StatusRuntimeException statusException
                && statusException.getStatus().getCode() == Status.Code.NOT_FOUND) {
            throw new CreatorNotFoundException();
        }
        log.warn("크리에이터 서비스 연결 실패");
        throw new CreatorServiceUnavailableException(throwable);
    }

    @Override
    @CircuitBreaker(name = "creatorService", fallbackMethod = "getCreatorProfilesBatchFallback")
    public Map<Long, CreatorProfile> getCreatorProfilesBatch(List<CreatorId> creatorIds) {
        GetCreatorProfilesBatchRequest request = GetCreatorProfilesBatchRequest.newBuilder()
                .addAllCreatorIds(creatorIds.stream().map(CreatorId::value).toList())
                .build();

        CreatorServiceGrpc.CreatorServiceBlockingStub stub = CreatorServiceGrpc.newBlockingStub(creatorManagedChannel)
                .withDeadlineAfter(2, TimeUnit.SECONDS);
        GetCreatorProfilesBatchResponse response = stub.getCreatorProfilesBatch(request);

        return response.getProfilesList().stream()
                .collect(Collectors.toMap(
                        p -> p.getCreatorId(),
                        p -> new CreatorProfile(p.getCreatorId(), p.getNickname(), p.getProfileImageUrl())
                ));
    }

    @SuppressWarnings("unused")
    private Map<Long, CreatorProfile> getCreatorProfilesBatchFallback(List<CreatorId> creatorIds, Throwable throwable) {
        log.warn("크리에이터 서비스 연결 실패 (batch)");
        throw new CreatorServiceUnavailableException(throwable);
    }
}
