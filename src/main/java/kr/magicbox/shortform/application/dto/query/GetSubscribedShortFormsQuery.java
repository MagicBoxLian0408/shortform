package kr.magicbox.shortform.application.dto.query;

import kr.magicbox.shortform.domain.vo.UserId;

public record GetSubscribedShortFormsQuery(UserId userId, Long cursorId, int size) {

    public static GetSubscribedShortFormsQuery of(UserId userId, Long cursorId, int size) {
        return new GetSubscribedShortFormsQuery(userId, cursorId, size);
    }
}
