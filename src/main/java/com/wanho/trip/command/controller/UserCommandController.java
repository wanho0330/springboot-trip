package com.wanho.trip.command.controller;


import com.wanho.trip.command.dto.UserDTO;
import com.wanho.trip.command.service.UserCommandService;
import com.wanho.trip.shared.response.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/command")
@RequiredArgsConstructor
public class UserCommandController {

    private final UserCommandService userCommandService;

    @PostMapping("/signup")
    public APIResponse<UserDTO.SignUpRes> signup(@Valid @RequestBody UserDTO.SignUpReq signUpReq) {
        return APIResponse.success(userCommandService.signUp(signUpReq));
    }


}


