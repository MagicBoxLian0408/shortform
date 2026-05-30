package kr.magicbox.shortform.application.dto.command;

import kr.magicbox.shortform.domain.vo.ShortFormId;
import kr.magicbox.shortform.domain.vo.UserId;
import lombok.Builder;

@Builder
public record AddCommentCommand(
        ShortFormId shortFormId,
        UserId userId,
        String content
) {}
