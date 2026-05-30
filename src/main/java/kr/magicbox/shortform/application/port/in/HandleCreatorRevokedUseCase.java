package kr.magicbox.shortform.application.port.in;

import kr.magicbox.shortform.application.dto.command.HandleCreatorRevokedCommand;

public interface HandleCreatorRevokedUseCase {
    void handleCreatorRevoked(HandleCreatorRevokedCommand command);
}
