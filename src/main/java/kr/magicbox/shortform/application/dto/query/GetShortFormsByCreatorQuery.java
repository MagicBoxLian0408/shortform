package kr.magicbox.shortform.application.dto.query;

import kr.magicbox.shortform.domain.vo.CreatorId;

public record GetShortFormsByCreatorQuery(CreatorId creatorId, Long cursorId, int size) {

    public static GetShortFormsByCreatorQuery of(CreatorId creatorId, Long cursorId, int size) {
        return new GetShortFormsByCreatorQuery(creatorId, cursorId, size);
    }
}
