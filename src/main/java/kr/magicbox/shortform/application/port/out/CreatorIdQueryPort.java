package kr.magicbox.shortform.application.port.out;

import kr.magicbox.shortform.domain.vo.CreatorId;
import kr.magicbox.shortform.domain.vo.UserId;

import java.util.concurrent.CompletableFuture;

public interface CreatorIdQueryPort {
    CompletableFuture<CreatorId> getCreatorId(UserId userId) throws Exception;
}
