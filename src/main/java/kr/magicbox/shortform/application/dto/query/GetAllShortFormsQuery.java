package kr.magicbox.shortform.application.dto.query;

import kr.magicbox.shortform.domain.vo.UserId;

public record GetAllShortFormsQuery(Long cursorId, int size, UserId userId) {

    public static GetAllShortFormsQuery of(Long cursorId, int size, UserId userId) {
        return new GetAllShortFormsQuery(cursorId, size, userId);
    }
}
