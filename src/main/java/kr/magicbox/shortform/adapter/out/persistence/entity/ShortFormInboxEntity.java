package kr.magicbox.shortform.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "shortform_inbox")
public class ShortFormInboxEntity extends BaseEntity {

    @Column(name = "event_key", nullable = false, unique = true)
    private String key;

    @Column(nullable = false)
    private String topic;

    @Column(name = "kafka_partition", nullable = false)
    private Integer partition;

    @Column(name = "kafka_offset", nullable = false)
    private Long offset;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShortFormInboxStatus status;

    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;

    @Builder
    public ShortFormInboxEntity(String key, String topic, Integer partition, Long offset,
                                ShortFormInboxStatus status, Instant occurredAt) {
        this.key = key;
        this.topic = topic;
        this.partition = partition;
        this.offset = offset;
        this.status = status;
        this.occurredAt = occurredAt;
    }

    public void markProcessed() {
        this.status = ShortFormInboxStatus.PROCESSED;
    }

    public void markDeadLettered() {
        this.status = ShortFormInboxStatus.DEAD_LETTERED;
    }
}
