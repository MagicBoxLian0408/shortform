package kr.magicbox.shortform.application.port.out;

import kr.magicbox.shortform.domain.vo.CreatorId;

import java.util.List;
import java.util.Map;

public interface CreatorProfileQueryPort {
    CreatorProfile getCreatorProfile(CreatorId creatorId);
    Map<Long, CreatorProfile> getCreatorProfilesBatch(List<CreatorId> creatorIds);

    record CreatorProfile(Long creatorId, String nickname, String profileImageUrl) {}
}
