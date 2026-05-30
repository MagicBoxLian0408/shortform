package kr.magicbox.shortform.application.port.out;

import kr.magicbox.shortform.domain.vo.CreatorId;
import kr.magicbox.shortform.domain.vo.UserId;

public interface CreatorIdQueryPort {
    CreatorId getCreatorId(UserId userId);
}
