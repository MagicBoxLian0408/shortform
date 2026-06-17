package kr.magicbox.shortform.adapter.in.web.dto.response;

import kr.magicbox.shortform.application.dto.result.CommentResult;
import lombok.Builder;

import java.time.Instant;

@Builder
public record CommentResponse(
        Long id,
        Long userId,
        String userNickname,
        String content,
        Instant createdAt
) {
    public static CommentResponse from(CommentResult result) {
        return CommentResponse.builder()
                .id(result.id().value())
                .userId(result.userId().value())
                .userNickname(result.userNickname())
                .content(result.content())
                .createdAt(result.createdAt())
                .build();
    }
}
