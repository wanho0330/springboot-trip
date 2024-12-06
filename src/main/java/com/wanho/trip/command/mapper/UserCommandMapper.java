package com.wanho.trip.command.mapper;

import com.wanho.trip.command.dto.UserDTO;
import com.wanho.trip.command.model.User;
import com.wanho.trip.shared.status.UserStatus;

public class UserCommandMapper {
    // SignUpReq -> Entity
    public static User fromSignUpReq(UserDTO.SignUpReq request, String encodedPassword) {
        return User.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .name(request.getName())
                .status(UserStatus.ACTIVE.getCode())
                .build();
    }

    public static User fromQueryUser(com.wanho.trip.query.model.User user) {
        return User.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .status(user.getStatus())
                .build();
    }

}
