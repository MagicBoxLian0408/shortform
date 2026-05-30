package kr.magicbox.shortform.domain.aggregate;

import kr.magicbox.shortform.domain.exception.InvalidFieldException;
import kr.magicbox.shortform.domain.vo.CommentId;
import kr.magicbox.shortform.domain.vo.ShortFormId;
import kr.magicbox.shortform.domain.vo.UserId;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
public class Comment {

    private final CommentId id;
    private final ShortFormId shortFormId;
    private final UserId userId;
    private final String content;
    private final Instant createdAt;
    private boolean isDeleted;

    @Builder(builderMethodName = "createBuilder", builderClassName = "CreateBuilder")
    public Comment(ShortFormId shortFormId, UserId userId, String content) {
        validateCreate(shortFormId, userId, content);
        this.id = null;
        this.shortFormId = shortFormId;
        this.userId = userId;
        this.content = content;
        this.createdAt = null;
        this.isDeleted = false;
    }

    @Builder(builderMethodName = "reconstructBuilder", builderClassName = "ReconstructBuilder")
    public Comment(CommentId id, ShortFormId shortFormId, UserId userId, String content, Instant createdAt, boolean isDeleted) {
        if (id == null) throw new InvalidFieldException("댓글 ID는 필수입니다.");
        validateCreate(shortFormId, userId, content);
        this.id = id;
        this.shortFormId = shortFormId;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
        this.isDeleted = isDeleted;
    }

    public void delete() {
        this.isDeleted = true;
    }

    private void validateCreate(ShortFormId shortFormId, UserId userId, String content) {
        if (shortFormId == null) throw new InvalidFieldException("숏폼 ID는 필수입니다.");
        if (userId == null) throw new InvalidFieldException("사용자 ID는 필수입니다.");
        if (content == null || content.isBlank()) throw new InvalidFieldException("댓글 내용은 필수입니다.");
        if (content.length() > 300) throw new InvalidFieldException("댓글은 300자 이하여야 합니다.");
    }
}
