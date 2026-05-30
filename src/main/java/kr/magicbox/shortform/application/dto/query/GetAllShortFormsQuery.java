package kr.magicbox.shortform.application.dto.query;

public record GetAllShortFormsQuery(Long cursorId, int size) {

    public static GetAllShortFormsQuery of(Long cursorId, int size) {
        return new GetAllShortFormsQuery(cursorId, size);
    }
}
