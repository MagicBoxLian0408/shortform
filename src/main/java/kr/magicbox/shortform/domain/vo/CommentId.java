package kr.magicbox.shortform.domain.vo;

import kr.magicbox.shortform.domain.exception.InvalidFieldException;

public record CommentId(Long value) {

    public CommentId {
        if (value == null || value <= 0) throw new InvalidFieldException("댓글 ID는 양수여야 합니다.");
    }

    public static CommentId of(Long value) {
        return new CommentId(value);
    }
}
