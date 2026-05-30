package kr.magicbox.shortform.application.port.in;

import kr.magicbox.shortform.application.dto.command.RegisterShortFormCommand;

public interface RegisterShortFormUseCase {
    void registerShortForm(RegisterShortFormCommand command);
}
