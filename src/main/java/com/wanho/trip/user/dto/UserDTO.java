package com.wanho.trip.user.dto;


import com.wanho.trip.user.entity.User;
import com.wanho.trip.user.util.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class UserDTO {

    @Getter
    @AllArgsConstructor
    public static class SignUpReq {
        @NotBlank
        @Email
        private String email;
        @NotBlank
        private String password;
        @NotBlank
        private String name;
        private UserStatus status;
    };

    @AllArgsConstructor
    @Builder
    public static class SignUpRes {
        private Long id;
    }

    @Getter
    @AllArgsConstructor
    public static class SignInReq {
        @NotBlank
        @Size(min = 5, max = 20)
        private String email;

        @NotBlank
        @Size(min = 5, max = 20)
        private String password;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class InfoRes {
        private Long id;
        private String email;
        private String name;
    }


    @Getter
    @AllArgsConstructor
    @Builder
    public static class ListRes {
        private Long id;
        private String email;
        private String name;
        private UserStatus status;
    }



}
