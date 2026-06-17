package kr.magicbox.shortform.application.dto.query;

import kr.magicbox.shortform.domain.vo.ShortFormId;
import kr.magicbox.shortform.domain.vo.UserId;

public record GetShortFormQuery(ShortFormId shortFormId, UserId userId) {

    public static GetShortFormQuery of(ShortFormId shortFormId, UserId userId) {
        return new GetShortFormQuery(shortFormId, userId);
    }
}
