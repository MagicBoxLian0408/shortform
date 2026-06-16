package kr.magicbox.shortform.application.dto.command;

import kr.magicbox.shortform.domain.enums.MagicGenre;
import kr.magicbox.shortform.domain.vo.ShortFormId;
import kr.magicbox.shortform.domain.vo.UserId;
import lombok.Builder;

@Builder
public record UpdateShortFormCommand(
        ShortFormId shortFormId,
        UserId userId,
        String title,
        String description,
        String videoUuid,
        String thumbnailUuid,
        MagicGenre genre
) {}
