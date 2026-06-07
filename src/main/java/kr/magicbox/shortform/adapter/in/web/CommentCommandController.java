package kr.magicbox.shortform.adapter.in.web;

import jakarta.validation.Valid;
import kr.magicbox.shortform.adapter.in.web.dto.request.AddCommentRequest;
import kr.magicbox.shortform.application.dto.command.DeleteCommentCommand;
import kr.magicbox.shortform.application.port.in.AddCommentUseCase;
import kr.magicbox.shortform.application.port.in.DeleteCommentUseCase;
import kr.magicbox.shortform.domain.vo.CommentId;
import kr.magicbox.shortform.domain.vo.ShortFormId;
import kr.magicbox.shortform.domain.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequiredArgsConstructor
@Validated
public class CommentCommandController {
    private final AddCommentUseCase addCommentUseCase;
    private final DeleteCommentUseCase deleteCommentUseCase;

    @PostMapping("/{id}/comment")
    public ResponseEntity<Void> addComment(
            @AuthenticationPrincipal UserId userId,
            @PathVariable Long id,
            @Valid @RequestBody AddCommentRequest request
    ) {
        addCommentUseCase.addComment(request.toCommand(ShortFormId.of(id), userId));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{shortformId}/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @AuthenticationPrincipal UserId userId,
            @PathVariable Long shortformId,
            @PathVariable Long commentId
    ) {
        deleteCommentUseCase.deleteComment(DeleteCommentCommand.of(ShortFormId.of(shortformId), CommentId.of(commentId), userId));
        return ResponseEntity.noContent().build();
    }
}
