package com.wanho.trip.command.model;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "`user`")
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 128, unique = true, updatable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 16)
    private String name;

    @Column
    private String icon;

    @Column(nullable = false)
    private int status = 0;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<UserRole> userRoles;

    @Builder
    private User(String email, String password, String name, String icon, int status) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.icon = icon;
        this.status = status;
        this.userRoles = null;
    }

}
