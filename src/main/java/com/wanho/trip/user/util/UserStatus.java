package com.wanho.trip.user.util;

import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE(0),
    INACTIVE(1),
    SUSPENDED(2),
    ;

    private final int code;

    UserStatus(int code) {
        this.code = code;
    }

    public static UserStatus fromCode(int code) {
        for (UserStatus userStatus : UserStatus.values()) {
            if (userStatus.code == code) {
                return userStatus;
            }
        }
        throw new IllegalArgumentException("Invalid user status: " + code);
    }

}
