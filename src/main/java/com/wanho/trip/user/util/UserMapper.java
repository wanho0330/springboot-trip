package com.wanho.trip.user.util;

import com.wanho.trip.user.dto.UserDTO;
import com.wanho.trip.user.entity.User;
import org.springframework.data.domain.Page;

public class UserMapper {
    // SignUpReq -> Entity
    public static User fromSignUpReq(UserDTO.SignUpReq request, String encodedPassword) {
        return User.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .name(request.getName())
                .status(UserStatus.ACTIVE.getCode())
                .build();
    }

    // Entity -> InfoRes
    public static UserDTO.InfoRes toInfoRes(User user) {
        if (user == null ) {
            return UserDTO.InfoRes.builder().build();
        }

        return UserDTO.InfoRes.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    // Entity -> ListRes
    public static Page<UserDTO.ListRes> toListRes(Page<User> users) {
        if (users == null || users.isEmpty()) {
            return Page.empty();
        }

        return users.map(user -> UserDTO.ListRes.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .status(UserStatus.fromCode(user.getStatus()))
                .build());
    }


}
