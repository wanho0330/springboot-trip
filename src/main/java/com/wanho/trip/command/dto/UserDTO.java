package com.wanho.trip.command.dto;


import com.wanho.trip.shared.status.UserStatus;
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


}
