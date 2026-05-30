package kr.magicbox.shortform.application.dto.result;

import kr.magicbox.shortform.domain.enums.MagicGenre;
import kr.magicbox.shortform.domain.enums.Visibility;
import kr.magicbox.shortform.domain.vo.CreatorId;
import kr.magicbox.shortform.domain.vo.ShortFormId;
import lombok.Builder;

import java.time.Instant;

@Builder
public record ShortFormResult(
        ShortFormId id,
        CreatorId creatorId,
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
) {}
