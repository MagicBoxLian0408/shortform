package kr.magicbox.shortform.domain.event;

public interface ShortFormDomainEvent {
    String key();
    ShortFormDomainEventType eventType();
}
