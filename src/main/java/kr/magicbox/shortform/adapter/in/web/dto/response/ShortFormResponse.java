package kr.magicbox.shortform.adapter.in.web.dto.response;

import kr.magicbox.shortform.application.dto.result.ShortFormResult;
import kr.magicbox.shortform.domain.enums.MagicGenre;
import lombok.Builder;

import java.time.Instant;

@Builder
public record ShortFormResponse(
        Long id,
        CreatorResponse creator,
        String title,
        String description,
        String videoUuid,
        String thumbnailUuid,
        MagicGenre genre,
        Long likeCount,
        Long commentCount,
        Long viewCount,
        Instant createdAt
) {
    @Builder
    public record CreatorResponse(Long id, String nickname, String profileImage) {}

    public static ShortFormResponse from(ShortFormResult result) {
        return ShortFormResponse.builder()
                .id(result.id().value())
                .creator(new CreatorResponse(
                        result.creator().id(),
                        result.creator().nickname(),
                        result.creator().profileImage()
                ))
                .title(result.title())
                .description(result.description())
                .videoUuid(result.videoUuid())
                .thumbnailUuid(result.thumbnailUuid())
                .genre(result.genre())
                .likeCount(result.likeCount())
                .commentCount(result.commentCount())
                .viewCount(result.viewCount())
                .createdAt(result.createdAt())
                .build();
    }
}
