package com.nsoft.offer.model.enums;

public enum MarketStatus {
    INACTIVE(0),
    ACTIVE(1);

    private final int value;

    MarketStatus(int value) {
        this.value = value;
    }

    public static MarketStatus fromValue(int value) {
        for (MarketStatus status : MarketStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid MarketStatus value: " + value);
    }
}

