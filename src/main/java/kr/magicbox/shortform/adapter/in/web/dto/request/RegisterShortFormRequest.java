package kr.magicbox.shortform.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.magicbox.shortform.application.dto.command.RegisterShortFormCommand;
import kr.magicbox.shortform.domain.enums.MagicGenre;
import kr.magicbox.shortform.domain.vo.UserId;
import lombok.Builder;

@Builder
public record RegisterShortFormRequest(
        @NotBlank(message = "제목은 필수입니다.") @Size(max = 100, message = "제목은 100자 이하여야 합니다.") String title,
        String description,
        @NotBlank(message = "영상 UUID는 필수입니다.") String videoUuid,
        @NotBlank(message = "썸네일 UUID는 필수입니다.") String thumbnailUuid,
        @NotNull(message = "장르는 필수입니다.") MagicGenre genre
) {
    public RegisterShortFormCommand toCommand(UserId userId) {
        return RegisterShortFormCommand.builder()
                .userId(userId)
                .title(title)
                .description(description)
                .videoUuid(videoUuid)
                .thumbnailUuid(thumbnailUuid)
                .genre(genre)
                .build();
    }
}
