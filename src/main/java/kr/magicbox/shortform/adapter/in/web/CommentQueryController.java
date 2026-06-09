package kr.magicbox.shortform.adapter.in.web;

import kr.magicbox.shortform.adapter.in.web.constants.CursorConstants;
import kr.magicbox.shortform.adapter.in.web.dto.response.CommentResponse;
import kr.magicbox.shortform.adapter.in.web.dto.response.CursorResponse;
import kr.magicbox.shortform.adapter.in.web.validation.CursorSize;
import kr.magicbox.shortform.application.dto.query.GetCommentsByShortFormQuery;
import kr.magicbox.shortform.application.port.in.GetCommentsByShortFormUseCase;
import kr.magicbox.shortform.domain.vo.ShortFormId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Validated
public class CommentQueryController {
    private final GetCommentsByShortFormUseCase getCommentsByShortFormUseCase;

    @GetMapping("/{id}/comment")
    public ResponseEntity<CursorResponse<CommentResponse>> getComments(
            @PathVariable Long id,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = CursorConstants.DEFAULT_SIZE) @CursorSize Integer size
    ) {
        List<CommentResponse> contents = getCommentsByShortFormUseCase.getCommentsByShortForm(
                        GetCommentsByShortFormQuery.of(ShortFormId.of(id), cursor, size))
                .stream()
                .map(CommentResponse::from)
                .toList();
        return ResponseEntity.ok(CursorResponse.of(contents, size, r -> r.id()));
    }
}
