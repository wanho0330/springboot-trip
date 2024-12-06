package com.wanho.trip.query.dto;


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
