package kr.magicbox.shortform.adapter.in.kafka.event;

import java.time.Instant;

public interface InboxEvent {
    Long eventId();
    Instant occurredAt();
}
