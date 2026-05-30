package kr.magicbox.shortform.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record ShortFormDeletedEvent(
        @JsonProperty("shortform_id") Long shortFormId,
        @JsonProperty("creator_id") Long creatorId,
        @JsonProperty("title") String title,
        @JsonProperty("media_urls") List<String> mediaUrls,
        @JsonProperty("occurred_at") Instant occurredAt
) implements ShortFormDomainEvent {

    @Override
    public String key() {
        return shortFormId.toString();
    }

    @Override
    public ShortFormDomainEventType eventType() {
        return ShortFormDomainEventType.SHORTFORM_DELETED;
    }
}
