package com.wanho.trip.user.controller;


import com.wanho.trip.common.auth.AuthUser;
import com.wanho.trip.common.response.APIResponse;
import com.wanho.trip.user.dto.UserDTO;
import com.wanho.trip.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public APIResponse<Long> signup(@Valid @RequestBody UserDTO.SignUpReq signUpReq) {
        return APIResponse.success(userService.signUp(signUpReq));
    }

    @PostMapping("/login")
    public APIResponse<?> login(@Valid @RequestBody UserDTO.LoginReq loginReq) {
        return APIResponse.success(userService.login(loginReq));
    }

    @GetMapping
    public APIResponse<?> Info() {
        Long id = ((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return APIResponse.success(userService.userInfo(id));
    }


}

