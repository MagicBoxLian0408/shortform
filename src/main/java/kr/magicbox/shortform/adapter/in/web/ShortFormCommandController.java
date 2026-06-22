package kr.magicbox.shortform.adapter.in.web;

import jakarta.validation.Valid;
import kr.magicbox.shortform.adapter.in.web.dto.request.RegisterShortFormRequest;
import kr.magicbox.shortform.adapter.in.web.dto.request.UpdateShortFormRequest;
import kr.magicbox.shortform.application.dto.command.DeleteShortFormCommand;
import kr.magicbox.shortform.application.port.in.DeleteShortFormUseCase;
import kr.magicbox.shortform.application.port.in.RegisterShortFormUseCase;
import kr.magicbox.shortform.application.port.in.UpdateShortFormUseCase;
import kr.magicbox.shortform.domain.vo.ShortFormId;
import kr.magicbox.shortform.domain.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class ShortFormCommandController {
    private final RegisterShortFormUseCase registerShortFormUseCase;
    private final UpdateShortFormUseCase updateShortFormUseCase;
    private final DeleteShortFormUseCase deleteShortFormUseCase;

    @PostMapping
    public ResponseEntity<Void> register(
            @AuthenticationPrincipal UserId userId,
            @Valid @RequestBody RegisterShortFormRequest request
    ) {
        registerShortFormUseCase.registerShortForm(request.toCommand(userId));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(
            @AuthenticationPrincipal UserId userId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateShortFormRequest request
    ) {
        updateShortFormUseCase.updateShortForm(request.toCommand(id, userId));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal UserId userId,
            @PathVariable Long id
    ) {
        deleteShortFormUseCase.deleteShortForm(DeleteShortFormCommand.of(ShortFormId.of(id), userId));
        return ResponseEntity.noContent().build();
    }
}
