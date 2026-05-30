package kr.magicbox.shortform.application.dto.query;

import kr.magicbox.shortform.domain.vo.CreatorId;

public record GetShortFormsByCreatorIdQuery(CreatorId creatorId) {
    public static GetShortFormsByCreatorIdQuery of(CreatorId creatorId) {
        return new GetShortFormsByCreatorIdQuery(creatorId);
    }
}
