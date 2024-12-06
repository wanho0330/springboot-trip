package com.wanho.trip.query.mapper;

import com.wanho.trip.query.dto.UserDTO;
import com.wanho.trip.query.model.User;
import com.wanho.trip.shared.status.UserStatus;
import org.springframework.data.domain.Page;

public class UserQueryMapper {


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
