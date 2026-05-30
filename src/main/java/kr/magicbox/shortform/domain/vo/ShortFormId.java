package kr.magicbox.shortform.domain.vo;

import kr.magicbox.shortform.domain.exception.InvalidFieldException;

public record ShortFormId(Long value) {

    public ShortFormId {
        if (value == null || value <= 0) throw new InvalidFieldException("숏폼 ID는 양수여야 합니다.");
    }

    public static ShortFormId of(Long value) {
        return new ShortFormId(value);
    }
}
