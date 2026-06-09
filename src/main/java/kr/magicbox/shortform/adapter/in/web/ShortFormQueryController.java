package kr.magicbox.shortform.adapter.in.web;

import kr.magicbox.shortform.adapter.in.web.constants.CursorConstants;
import kr.magicbox.shortform.adapter.in.web.dto.response.CursorResponse;
import kr.magicbox.shortform.adapter.in.web.dto.response.ShortFormResponse;
import kr.magicbox.shortform.adapter.in.web.validation.CursorSize;
import kr.magicbox.shortform.application.dto.query.GetAllShortFormsQuery;
import kr.magicbox.shortform.application.dto.query.GetShortFormQuery;
import kr.magicbox.shortform.application.dto.query.GetShortFormsByCreatorQuery;
import kr.magicbox.shortform.application.dto.result.ShortFormResult;
import kr.magicbox.shortform.application.port.in.GetAllShortFormsUseCase;
import kr.magicbox.shortform.application.port.in.GetShortFormUseCase;
import kr.magicbox.shortform.application.port.in.GetShortFormsByCreatorUseCase;
import kr.magicbox.shortform.domain.vo.CreatorId;
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
public class ShortFormQueryController {
    private final GetShortFormUseCase getShortFormUseCase;
    private final GetAllShortFormsUseCase getAllShortFormsUseCase;
    private final GetShortFormsByCreatorUseCase getShortFormsByCreatorUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<ShortFormResponse> getShortForm(@PathVariable Long id) {
        ShortFormResult result = getShortFormUseCase.getShortForm(GetShortFormQuery.of(ShortFormId.of(id)));
        return ResponseEntity.ok(ShortFormResponse.from(result));
    }

    @GetMapping
    public ResponseEntity<CursorResponse<ShortFormResponse>> getShortForms(
            @RequestParam(required = false) Long creatorId,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = CursorConstants.DEFAULT_SIZE) @CursorSize Integer size
    ) {
        List<ShortFormResponse> contents;
        if (creatorId != null) {
            contents = getShortFormsByCreatorUseCase.getShortFormsByCreator(
                            GetShortFormsByCreatorQuery.of(CreatorId.of(creatorId), cursor, size))
                    .stream()
                    .map(ShortFormResponse::from)
                    .toList();
        } else {
            contents = getAllShortFormsUseCase.getAllShortForms(GetAllShortFormsQuery.of(cursor, size))
                    .stream()
                    .map(ShortFormResponse::from)
                    .toList();
        }
        return ResponseEntity.ok(CursorResponse.of(contents, size, r -> r.id()));
    }
}
