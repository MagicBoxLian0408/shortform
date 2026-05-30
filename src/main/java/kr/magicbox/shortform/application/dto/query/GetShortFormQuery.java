package kr.magicbox.shortform.application.dto.query;

import kr.magicbox.shortform.domain.vo.ShortFormId;

public record GetShortFormQuery(ShortFormId shortFormId) {

    public static GetShortFormQuery of(ShortFormId shortFormId) {
        return new GetShortFormQuery(shortFormId);
    }
}
