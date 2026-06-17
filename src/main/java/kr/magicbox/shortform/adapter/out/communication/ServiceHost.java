package kr.magicbox.shortform.adapter.out.communication;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceHost {
    CREATOR("creator"),
    SUBSCRIBE("subscribe"),
    USER("user");

    private final String hostName;
}
