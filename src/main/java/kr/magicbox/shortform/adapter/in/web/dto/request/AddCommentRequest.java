package kr.magicbox.shortform.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import kr.magicbox.shortform.application.dto.command.AddCommentCommand;
import kr.magicbox.shortform.domain.vo.ShortFormId;
import kr.magicbox.shortform.domain.vo.UserId;
import lombok.Builder;

@Builder
public record AddCommentRequest(
        @NotBlank(message = "댓글 내용은 필수입니다.") @Size(max = 300, message = "댓글은 300자 이하여야 합니다.") String content
) {
    public AddCommentCommand toCommand(ShortFormId shortFormId, UserId userId) {
        return AddCommentCommand.builder()
                .shortFormId(shortFormId)
                .userId(userId)
                .content(content)
                .build();
    }
}
