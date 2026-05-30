package kr.magicbox.shortform.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "shortform_outbox")
public class ShortFormOutboxEntity extends BaseEntity {

    @Column(nullable = false)
    private String eventType;

    @Column(nullable = false)
    private String aggregateKey;

    @Column(nullable = false, columnDefinition = "JSON")
    private String payload;

    @Builder
    public ShortFormOutboxEntity(String eventType, String aggregateKey, String payload) {
        this.eventType = eventType;
        this.aggregateKey = aggregateKey;
        this.payload = payload;
    }
}
