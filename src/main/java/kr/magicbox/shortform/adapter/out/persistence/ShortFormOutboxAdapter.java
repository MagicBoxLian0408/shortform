package kr.magicbox.shortform.adapter.out.persistence;

import kr.magicbox.shortform.adapter.out.persistence.entity.ShortFormOutboxEntity;
import kr.magicbox.shortform.adapter.out.persistence.repository.ShortFormOutboxRepository;
import kr.magicbox.shortform.application.port.out.ShortFormOutboxPort;
import kr.magicbox.shortform.domain.event.ShortFormDomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

@Repository
@RequiredArgsConstructor
public class ShortFormOutboxAdapter implements ShortFormOutboxPort {

    private final ShortFormOutboxRepository shortFormOutboxRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void save(ShortFormDomainEvent event) {
        String payload = objectMapper.writeValueAsString(event);
        shortFormOutboxRepository.save(ShortFormOutboxEntity.builder()
                .eventType(event.eventType().getValue())
                .aggregateKey(event.key())
                .payload(payload)
                .build());
    }
}
