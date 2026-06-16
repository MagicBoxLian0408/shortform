package kr.magicbox.shortform.adapter.in.web.dto.request;

import jakarta.validation.constraints.Size;
import kr.magicbox.shortform.application.dto.command.UpdateShortFormCommand;
import kr.magicbox.shortform.domain.enums.MagicGenre;
import kr.magicbox.shortform.domain.vo.ShortFormId;
import kr.magicbox.shortform.domain.vo.UserId;
import lombok.Builder;

@Builder
public record UpdateShortFormRequest(
        @Size(max = 100, message = "제목은 100자 이하여야 합니다.") String title,
        String description,
        String videoUuid,
        String thumbnailUuid,
        MagicGenre genre
) {
    public UpdateShortFormCommand toCommand(Long id, UserId userId) {
        return UpdateShortFormCommand.builder()
                .shortFormId(ShortFormId.of(id))
                .userId(userId)
                .title(title)
                .description(description)
                .videoUuid(videoUuid)
                .thumbnailUuid(thumbnailUuid)
                .genre(genre)
                .build();
    }
}
