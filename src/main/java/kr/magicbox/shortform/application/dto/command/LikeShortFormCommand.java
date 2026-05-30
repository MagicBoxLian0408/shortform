package kr.magicbox.shortform.application.dto.command;

import kr.magicbox.shortform.domain.vo.ShortFormId;
import kr.magicbox.shortform.domain.vo.UserId;

public record LikeShortFormCommand(ShortFormId shortFormId, UserId userId) {

    public static LikeShortFormCommand of(ShortFormId shortFormId, UserId userId) {
        return new LikeShortFormCommand(shortFormId, userId);
    }
}
