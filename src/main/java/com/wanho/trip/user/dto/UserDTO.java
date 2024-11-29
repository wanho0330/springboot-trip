package com.wanho.trip.user.dto;


import com.wanho.trip.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class UserDTO {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class SignUpReq {
        @NotBlank
        @Email
        private String email;
        @NotBlank
        private String password;
        @NotBlank
        private String name;
        private int status;

        public User toEntity(String encodePassword) {
            return User.builder()
                    .email(this.getEmail())
                    .password(encodePassword)
                    .name(this.getName())
                    .status(this.getStatus())
                    .build();
        }
    };

    @Getter
    @AllArgsConstructor
    @Builder
    public static class LoginReq {
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

        public InfoRes(User user) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.name = user.getName();
        }
    }
}
