package kr.magicbox.shortform.application.port.in;

import kr.magicbox.shortform.application.dto.command.UpdateShortFormCommand;

public interface UpdateShortFormUseCase {
    void updateShortForm(UpdateShortFormCommand command);
}
