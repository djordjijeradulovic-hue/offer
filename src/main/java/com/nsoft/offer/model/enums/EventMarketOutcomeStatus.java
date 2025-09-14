package com.nsoft.offer.model.enums;

import lombok.Getter;

@Getter
public enum EventMarketOutcomeStatus {
    INACTIVE(0),
    ACTIVE(1);

    private final int value;

    EventMarketOutcomeStatus(int value) {
        this.value = value;
    }

    public static EventMarketOutcomeStatus fromValue(int value) {
        for (EventMarketOutcomeStatus status : EventMarketOutcomeStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid EventMarketOutcomeStatus value: " + value);
    }
}
