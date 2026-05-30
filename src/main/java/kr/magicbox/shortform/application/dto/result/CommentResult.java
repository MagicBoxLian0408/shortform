package kr.magicbox.shortform.application.dto.result;

import kr.magicbox.shortform.domain.vo.CommentId;
import kr.magicbox.shortform.domain.vo.UserId;
import lombok.Builder;

import java.time.Instant;

@Builder
public record CommentResult(
        CommentId id,
        UserId userId,
        String content,
        Instant createdAt
) {}
