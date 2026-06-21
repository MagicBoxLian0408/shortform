package kr.magicbox.shortform.application.service;

import kr.magicbox.shortform.application.dto.command.RegisterShortFormCommand;
import kr.magicbox.shortform.application.port.in.RegisterShortFormUseCase;
import kr.magicbox.shortform.application.port.out.CreatorIdQueryPort;
import kr.magicbox.shortform.application.port.out.ShortFormOutboxPort;
import kr.magicbox.shortform.application.port.out.ShortFormRepositoryPort;
import kr.magicbox.shortform.domain.aggregate.ShortForm;
import kr.magicbox.shortform.domain.event.ShortFormCreatedEvent;
import kr.magicbox.shortform.domain.vo.CreatorId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisterShortFormService implements RegisterShortFormUseCase {

    private final ShortFormRepositoryPort shortFormRepositoryPort;
    private final CreatorIdQueryPort creatorIdQueryPort;
    private final ShortFormOutboxPort shortFormOutboxPort;

    @Transactional
    @Override
    public void registerShortForm(RegisterShortFormCommand command) {
        CreatorId creatorId = creatorIdQueryPort.getCreatorId(command.userId()).join();

        ShortForm shortForm = ShortForm.createBuilder()
                .creatorId(creatorId)
                .title(command.title())
                .description(command.description())
                .videoUuid(command.videoUuid())
                .thumbnailUuid(command.thumbnailUuid())
                .genre(command.genre())
                .build();

        Long savedId = shortFormRepositoryPort.save(shortForm);

        shortFormOutboxPort.save(ShortFormCreatedEvent.builder()
                .shortFormId(savedId)
                .creatorId(creatorId.value())
                .title(command.title())
                .mediaUrls(List.of(command.videoUuid(), command.thumbnailUuid()))
                .occurredAt(Instant.now())
                .build());
    }
}
