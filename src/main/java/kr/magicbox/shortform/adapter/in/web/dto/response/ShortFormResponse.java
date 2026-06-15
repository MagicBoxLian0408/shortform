package kr.magicbox.shortform.adapter.in.web.dto.response;

import kr.magicbox.shortform.application.dto.result.ShortFormResult;
import kr.magicbox.shortform.domain.enums.MagicGenre;
import kr.magicbox.shortform.domain.enums.Visibility;
import lombok.Builder;

import java.time.Instant;

@Builder
public record ShortFormResponse(
        Long id,
        Long creatorId,
        String creatorNickname,
        String title,
        String description,
        String videoUuid,
        String thumbnailUuid,
        MagicGenre genre,
        Visibility visibility,
        Long likeCount,
        Long commentCount,
        Long viewCount,
        Instant createdAt
) {
    public static ShortFormResponse from(ShortFormResult result) {
        return ShortFormResponse.builder()
                .id(result.id().value())
                .creatorId(result.creatorId().value())
                .creatorNickname(result.creatorNickname())
                .title(result.title())
                .description(result.description())
                .videoUuid(result.videoUuid())
                .thumbnailUuid(result.thumbnailUuid())
                .genre(result.genre())
                .visibility(result.visibility())
                .likeCount(result.likeCount())
                .commentCount(result.commentCount())
                .viewCount(result.viewCount())
                .createdAt(result.createdAt())
                .build();
    }
}
