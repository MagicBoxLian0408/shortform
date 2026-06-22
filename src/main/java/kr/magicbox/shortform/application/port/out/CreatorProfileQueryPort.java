package kr.magicbox.shortform.application.port.out;

import kr.magicbox.shortform.domain.vo.CreatorId;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface CreatorProfileQueryPort {
    CompletableFuture<CreatorProfile> getCreatorProfile(CreatorId creatorId);
    CompletableFuture<Map<Long, CreatorProfile>> getCreatorProfilesBatch(List<CreatorId> creatorIds);

    record CreatorProfile(Long creatorId, String nickname, String profileImageUrl) {}
}
