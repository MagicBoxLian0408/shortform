package kr.magicbox.shortform.application.port.in;

import kr.magicbox.shortform.application.dto.command.DeleteShortFormCommand;

public interface DeleteShortFormUseCase {
    void deleteShortForm(DeleteShortFormCommand command);
}
