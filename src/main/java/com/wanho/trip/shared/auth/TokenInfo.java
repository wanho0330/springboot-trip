package com.wanho.trip.shared.auth;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenInfo {
    private String grantType;
    private String accessToken;

    public TokenInfo(String grantType, String accessToken) {
        this.grantType = grantType;
        this.accessToken = accessToken;
    }

}
