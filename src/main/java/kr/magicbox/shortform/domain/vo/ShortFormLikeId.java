package kr.magicbox.shortform.domain.vo;

import kr.magicbox.shortform.domain.exception.InvalidFieldException;

public record ShortFormLikeId(Long value) {

    public ShortFormLikeId {
        if (value == null || value <= 0) throw new InvalidFieldException("좋아요 ID는 양수여야 합니다.");
    }

    public static ShortFormLikeId of(Long value) {
        return new ShortFormLikeId(value);
    }
}
