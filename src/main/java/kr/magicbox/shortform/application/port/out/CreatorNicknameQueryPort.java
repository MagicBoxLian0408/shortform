package kr.magicbox.shortform.application.port.out;

import kr.magicbox.shortform.domain.vo.CreatorId;

public interface CreatorNicknameQueryPort {
    String getCreatorNickname(CreatorId creatorId);
}
