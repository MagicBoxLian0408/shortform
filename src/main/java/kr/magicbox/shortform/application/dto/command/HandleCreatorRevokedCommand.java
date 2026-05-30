package kr.magicbox.shortform.application.dto.command;

import kr.magicbox.shortform.domain.vo.CreatorId;

public record HandleCreatorRevokedCommand(CreatorId creatorId) {

    public static HandleCreatorRevokedCommand of(CreatorId creatorId) {
        return new HandleCreatorRevokedCommand(creatorId);
    }
}
