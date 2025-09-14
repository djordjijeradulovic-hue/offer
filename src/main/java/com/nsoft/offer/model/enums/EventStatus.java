package com.nsoft.offer.model.enums;

import lombok.Getter;

@Getter
public enum EventStatus {
    INACTIVE(0),
    ACTIVE(1);

    private final int value;

    EventStatus(int value) {
        this.value = value;
    }

    public static EventStatus fromValue(int value) {
        for (EventStatus status : EventStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid EventStatus value: " + value);
    }
}

