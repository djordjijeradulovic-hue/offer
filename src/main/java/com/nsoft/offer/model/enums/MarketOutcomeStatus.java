package com.nsoft.offer.model.enums;

import lombok.Getter;

@Getter
public enum MarketOutcomeStatus {
    INACTIVE(0),
    ACTIVE(1);

    private final int value;

    MarketOutcomeStatus(int value) {
        this.value = value;
    }

    public static MarketOutcomeStatus fromValue(int value) {
        for (MarketOutcomeStatus status : MarketOutcomeStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid MarketOutcomeStatus value: " + value);
    }
}

