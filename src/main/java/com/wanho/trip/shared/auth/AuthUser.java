package com.wanho.trip.shared.auth;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AuthUser extends User {
    @Getter
    private Long id;
    private String email;
    private String password;
    private Collection<SimpleGrantedAuthority> authorities;

    public AuthUser(Long id, String email, String password, Collection<SimpleGrantedAuthority> authorities) {
        super(email, password, authorities);
        this.id = id;
    }
}
