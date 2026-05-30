package kr.magicbox.shortform.domain.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ShortFormDomainEventType {

    SHORTFORM_CREATED("shortform-created"),
    SHORTFORM_UPDATED("shortform-updated"),
    SHORTFORM_DELETED("shortform-deleted");

    private final String value;
}
