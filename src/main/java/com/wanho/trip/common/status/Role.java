package com.wanho.trip.common.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum Role {
    USER;

    public String getRole() {
        return name();
    }
}


