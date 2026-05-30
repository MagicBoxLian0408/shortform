package kr.magicbox.shortform.application.dto.query;

import kr.magicbox.shortform.domain.vo.ShortFormId;

public record GetCommentsByShortFormQuery(ShortFormId shortFormId, Long cursorId, int size) {

    public static GetCommentsByShortFormQuery of(ShortFormId shortFormId, Long cursorId, int size) {
        return new GetCommentsByShortFormQuery(shortFormId, cursorId, size);
    }
}
