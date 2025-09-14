package com.nsoft.offer.model.enums;

import lombok.Getter;

@Getter
public enum EventMarketStatus {
    INACTIVE(0),
    ACTIVE(1);

    private final int value;

    EventMarketStatus(int value) {
        this.value = value;
    }

    public static EventMarketStatus fromValue(int value) {
        for (EventMarketStatus status : EventMarketStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid EventMarketStatus value: " + value);
    }
}
