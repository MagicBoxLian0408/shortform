package kr.magicbox.shortform.application.dto.command;

import kr.magicbox.shortform.domain.vo.ShortFormId;
import kr.magicbox.shortform.domain.vo.UserId;

public record DeleteShortFormCommand(ShortFormId shortFormId, UserId userId) {

    public static DeleteShortFormCommand of(ShortFormId shortFormId, UserId userId) {
        return new DeleteShortFormCommand(shortFormId, userId);
    }
}
