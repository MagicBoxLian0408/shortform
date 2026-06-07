package kr.magicbox.shortform.adapter.in.web;

import kr.magicbox.shortform.application.dto.command.LikeShortFormCommand;
import kr.magicbox.shortform.application.dto.command.UnlikeShortFormCommand;
import kr.magicbox.shortform.application.port.in.LikeShortFormUseCase;
import kr.magicbox.shortform.application.port.in.UnlikeShortFormUseCase;
import kr.magicbox.shortform.domain.vo.ShortFormId;
import kr.magicbox.shortform.domain.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequiredArgsConstructor
public class LikeCommandController {
    private final LikeShortFormUseCase likeShortFormUseCase;
    private final UnlikeShortFormUseCase unlikeShortFormUseCase;

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> like(
            @AuthenticationPrincipal UserId userId,
            @PathVariable Long id
    ) {
        likeShortFormUseCase.likeShortForm(LikeShortFormCommand.of(ShortFormId.of(id), userId));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/like")
    public ResponseEntity<Void> unlike(
            @AuthenticationPrincipal UserId userId,
            @PathVariable Long id
    ) {
        unlikeShortFormUseCase.unlikeShortForm(UnlikeShortFormCommand.of(ShortFormId.of(id), userId));
        return ResponseEntity.noContent().build();
    }
}
