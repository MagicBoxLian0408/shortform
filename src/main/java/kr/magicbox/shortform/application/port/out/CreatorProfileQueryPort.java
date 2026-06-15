package kr.magicbox.shortform.application.port.out;

import kr.magicbox.shortform.domain.vo.CreatorId;

public interface CreatorProfileQueryPort {
    CreatorProfile getCreatorProfile(CreatorId creatorId);

    record CreatorProfile(Long creatorId, String nickname, String profileImageUrl) {}
}
