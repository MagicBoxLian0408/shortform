package kr.magicbox.shortform.application.service;

import kr.magicbox.shortform.application.dto.command.HandleCreatorRevokedCommand;
import kr.magicbox.shortform.application.port.in.HandleCreatorRevokedUseCase;
import kr.magicbox.shortform.application.port.out.ShortFormRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HandleCreatorRevokedService implements HandleCreatorRevokedUseCase {

    private final ShortFormRepositoryPort shortFormRepositoryPort;

    @Transactional
    @Override
    public void handleCreatorRevoked(HandleCreatorRevokedCommand command) {
        shortFormRepositoryPort.softDeleteByCreatorId(command.creatorId());
    }
}
