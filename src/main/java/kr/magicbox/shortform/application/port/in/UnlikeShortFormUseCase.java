package kr.magicbox.shortform.application.port.in;

import kr.magicbox.shortform.application.dto.command.UnlikeShortFormCommand;

public interface UnlikeShortFormUseCase {
    void unlikeShortForm(UnlikeShortFormCommand command);
}
