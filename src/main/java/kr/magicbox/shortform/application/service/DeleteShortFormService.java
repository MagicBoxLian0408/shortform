package kr.magicbox.shortform.application.service;

import kr.magicbox.shortform.application.dto.command.DeleteShortFormCommand;
import kr.magicbox.shortform.application.port.in.DeleteShortFormUseCase;
import kr.magicbox.shortform.application.port.out.ShortFormOutboxPort;
import kr.magicbox.shortform.application.port.out.ShortFormRepositoryPort;
import kr.magicbox.shortform.domain.aggregate.ShortForm;
import kr.magicbox.shortform.domain.event.ShortFormDeletedEvent;
import kr.magicbox.shortform.domain.exception.ShortFormNotFoundException;
import kr.magicbox.shortform.domain.exception.ShortFormUnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeleteShortFormService implements DeleteShortFormUseCase {

    private final ShortFormRepositoryPort shortFormRepositoryPort;
    private final ShortFormOutboxPort shortFormOutboxPort;

    @Transactional
    @Override
    public void deleteShortForm(DeleteShortFormCommand command) {
        ShortForm shortForm = shortFormRepositoryPort.findById(command.shortFormId())
                .orElseThrow(ShortFormNotFoundException::new);

        if (!shortForm.getCreatorId().value().equals(command.userId().value())) {
            throw new ShortFormUnauthorizedException();
        }

        shortForm.delete();
        shortFormRepositoryPort.update(shortForm);

        shortFormOutboxPort.save(ShortFormDeletedEvent.builder()
                .shortFormId(shortForm.getId().value())
                .creatorId(shortForm.getCreatorId().value())
                .title(shortForm.getTitle())
                .mediaUrls(List.of(shortForm.getVideoUuid(), shortForm.getThumbnailUuid()))
                .occurredAt(Instant.now())
                .build());
    }
}
