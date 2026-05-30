package kr.magicbox.shortform.application.port.out;

import kr.magicbox.shortform.domain.event.ShortFormDomainEvent;

public interface ShortFormOutboxPort {
    void save(ShortFormDomainEvent event);
}
