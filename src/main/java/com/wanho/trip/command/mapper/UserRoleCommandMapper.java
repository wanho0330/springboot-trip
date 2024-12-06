package com.wanho.trip.command.mapper;

import com.wanho.trip.command.model.User;
import com.wanho.trip.command.model.UserRole;
import com.wanho.trip.shared.status.Role;

public class UserRoleCommandMapper {

    public static UserRole fromUser(User user, Role role) {
        if (user == null) {
            return UserRole.builder().role(role).build();
        }

        return UserRole.builder()
                .role(role)
                .user(user)
                .build();
    }
}
