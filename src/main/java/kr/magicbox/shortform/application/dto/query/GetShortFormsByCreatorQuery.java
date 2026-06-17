package kr.magicbox.shortform.application.dto.query;

import kr.magicbox.shortform.domain.vo.CreatorId;
import kr.magicbox.shortform.domain.vo.UserId;

public record GetShortFormsByCreatorQuery(CreatorId creatorId, Long cursorId, int size, UserId userId) {

    public static GetShortFormsByCreatorQuery of(CreatorId creatorId, Long cursorId, int size, UserId userId) {
        return new GetShortFormsByCreatorQuery(creatorId, cursorId, size, userId);
    }
}
