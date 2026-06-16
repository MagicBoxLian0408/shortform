package kr.magicbox.shortform.application.dto.result;

import kr.magicbox.shortform.domain.enums.MagicGenre;
import kr.magicbox.shortform.domain.vo.ShortFormId;
import lombok.Builder;

import java.time.Instant;

@Builder
public record ShortFormResult(
        ShortFormId id,
        CreatorInfo creator,
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
    public record CreatorInfo(Long id, String nickname, String profileImage) {}
}
