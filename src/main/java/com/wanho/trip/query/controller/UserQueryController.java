package com.wanho.trip.query.controller;


import com.wanho.trip.query.dto.UserDTO;
import com.wanho.trip.query.service.UserQueryService;
import com.wanho.trip.shared.auth.AuthUser;
import com.wanho.trip.shared.auth.TokenInfo;
import com.wanho.trip.shared.response.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/query")
@RequiredArgsConstructor
public class UserQueryController {

    private final UserQueryService userQueryService;

    @PostMapping("/login")
    public APIResponse<TokenInfo> login(@Valid @RequestBody UserDTO.SignInReq signInReq) {
        return APIResponse.success(userQueryService.login(signInReq));
    }

    @GetMapping("/search")
    public APIResponse<Page<UserDTO.ListRes>> list(@RequestParam int num, @RequestParam(required = false) String email) {
        Page<UserDTO.ListRes> listRes = userQueryService.userList(num, email);
        return APIResponse.success(listRes);
    }

    @GetMapping("/my")
    public APIResponse<UserDTO.InfoRes> info() {
        Long id = ((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return APIResponse.success(userQueryService.userInfo(id));
    }


}


