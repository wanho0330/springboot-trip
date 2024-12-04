package com.wanho.trip.user.util;

import com.wanho.trip.common.status.Role;
import com.wanho.trip.user.entity.User;
import com.wanho.trip.user.entity.UserRole;

public class UserRoleMapper {

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
