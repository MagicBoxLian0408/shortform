package kr.magicbox.shortform.application.port.out;

import kr.magicbox.shortform.domain.vo.UserId;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface SubscribedCreatorIdsQueryPort {
    CompletableFuture<List<Long>> getSubscribedCreatorIds(UserId userId) throws Exception;
}
