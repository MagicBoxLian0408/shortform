package kr.magicbox.shortform.adapter.out.communication.grpc;

import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import kr.magicbox.shortform.adapter.out.communication.grpc.exception.CreatorServiceUnavailableException;
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
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubscribeGrpcAdapter implements SubscribedCreatorIdsQueryPort {

    @Qualifier("subscribeManagedChannel")
    private final ManagedChannel subscribeManagedChannel;

    @Override
    @Cacheable(value = "subscribed-creators", key = "#userId.value()")
    public List<Long> getSubscribedCreatorIds(UserId userId) {
        GetSubscribedCreatorIdsRequest request = GetSubscribedCreatorIdsRequest.newBuilder()
                .setUserId(userId.value())
                .build();

        SubscribeServiceGrpc.SubscribeServiceBlockingStub stub = SubscribeServiceGrpc.newBlockingStub(subscribeManagedChannel)
                .withDeadlineAfter(2, TimeUnit.SECONDS);

        try {
            GetSubscribedCreatorIdsResponse response = stub.getSubscribedCreatorIds(request);
            return response.getCreatorIdsList();
        } catch (StatusRuntimeException e) {
            log.warn("구독 서비스 연결 실패: {}", e.getStatus());
            throw new CreatorServiceUnavailableException(e);
        }
    }
}
