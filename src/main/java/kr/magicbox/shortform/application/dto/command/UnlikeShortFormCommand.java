package kr.magicbox.shortform.application.dto.command;

import kr.magicbox.shortform.domain.vo.ShortFormId;
import kr.magicbox.shortform.domain.vo.UserId;

public record UnlikeShortFormCommand(ShortFormId shortFormId, UserId userId) {

    public static UnlikeShortFormCommand of(ShortFormId shortFormId, UserId userId) {
        return new UnlikeShortFormCommand(shortFormId, userId);
    }
}
