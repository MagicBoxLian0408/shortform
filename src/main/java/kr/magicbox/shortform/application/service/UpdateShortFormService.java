package kr.magicbox.shortform.application.service;

import kr.magicbox.shortform.application.dto.command.UpdateShortFormCommand;
import kr.magicbox.shortform.application.port.in.UpdateShortFormUseCase;
import kr.magicbox.shortform.application.port.out.ShortFormOutboxPort;
import kr.magicbox.shortform.application.port.out.ShortFormRepositoryPort;
import kr.magicbox.shortform.domain.aggregate.ShortForm;
import kr.magicbox.shortform.domain.event.ShortFormUpdatedEvent;
import kr.magicbox.shortform.domain.exception.ShortFormNotFoundException;
import kr.magicbox.shortform.domain.exception.ShortFormUnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateShortFormService implements UpdateShortFormUseCase {

    private final ShortFormRepositoryPort shortFormRepositoryPort;
    private final ShortFormOutboxPort shortFormOutboxPort;

    @Transactional
    @Override
    public void updateShortForm(UpdateShortFormCommand command) {
        ShortForm shortForm = shortFormRepositoryPort.findById(command.shortFormId())
                .orElseThrow(ShortFormNotFoundException::new);

        if (!shortForm.getCreatorId().value().equals(command.userId().value())) {
            throw new ShortFormUnauthorizedException();
        }

        List<String> beforeMediaUrls = List.of(shortForm.getVideoUuid(), shortForm.getThumbnailUuid());
        String beforeTitle = shortForm.getTitle();

        shortForm.update(command.title(), command.description(), command.videoUuid(),
                command.thumbnailUuid(), command.genre(), command.visibility());

        shortFormRepositoryPort.update(shortForm);

        List<String> afterMediaUrls = List.of(shortForm.getVideoUuid(), shortForm.getThumbnailUuid());

        shortFormOutboxPort.save(ShortFormUpdatedEvent.builder()
                .shortFormId(shortForm.getId().value())
                .creatorId(shortForm.getCreatorId().value())
                .before(new ShortFormUpdatedEvent.ShortFormSnapshot(beforeTitle, beforeMediaUrls))
                .after(new ShortFormUpdatedEvent.ShortFormSnapshot(shortForm.getTitle(), afterMediaUrls))
                .occurredAt(Instant.now())
                .build());
    }
}
