package com.wanho.trip.shared.status;

import lombok.Getter;

@Getter
public enum Role {
    USER;

    public String getRole() {
        return name();
    }
}


