package com.wanho.trip.user.util;

import com.wanho.trip.user.dto.UserDTO;
import com.wanho.trip.user.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    // SignUpReq -> Entity
    public static User fromSingUpReq(UserDTO.SignUpReq request, String encodedPassword) {
        return User.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .name(request.getName())
                .status(UserStatus.ACTIVE.getCode())
                .build();
    }

    // Entity -> InfoRes
    public static UserDTO.InfoRes toInfoRes(User user) {
        return UserDTO.InfoRes.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public static List<UserDTO.ListRes> toListRes(List<User> users) {
        return users.stream()
                .map(user ->
                        UserDTO.ListRes.builder()
                                .id(user.getId())
                                .email(user.getEmail())
                                .name(user.getName())
                                .status(UserStatus.fromCode(user.getStatus()))
                                .build())
                .collect(Collectors.toList());
    }



}
