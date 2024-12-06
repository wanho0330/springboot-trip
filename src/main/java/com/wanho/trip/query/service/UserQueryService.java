package com.wanho.trip.query.service;


import com.wanho.trip.query.dto.UserDTO;
import com.wanho.trip.query.model.User;
import com.wanho.trip.query.repository.UserQueryRepository;
import com.wanho.trip.query.repository.UserRoleQueryRepository;
import com.wanho.trip.shared.auth.JwtTokenProvider;
import com.wanho.trip.shared.auth.TokenInfo;
import com.wanho.trip.shared.exception.ErrorCode;
import com.wanho.trip.shared.exception.InvalidInputException;
import com.wanho.trip.query.mapper.UserQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserQueryService {
    private final UserQueryRepository userQueryRepository;
    private final UserRoleQueryRepository userRoleQueryRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;


    public TokenInfo login(UserDTO.SignInReq signInReq) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signInReq.getEmail(), signInReq.getPassword());
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.createToken(authenticate);
    }

    public UserDTO.InfoRes userInfo(Long id) {
        User user = userQueryRepository.findById(id).orElseThrow(() -> new InvalidInputException("userInfo", ErrorCode.USER_NOT_FOUND));
        return UserQueryMapper.toInfoRes(user);
    }

    public Page<UserDTO.ListRes> userList(int num, String email) {
        Pageable pageable = PageRequest.of(num, 20);
        Page<User> allByEmailContaining = userQueryRepository.findAllByEmailContainingOrderByNameAsc(email, pageable);
        return UserQueryMapper.toListRes(allByEmailContaining);
    }

    public boolean existsByEmail(String email) {
        return userQueryRepository.existsByEmail(email);
    }

    public Optional<User> findByEmail(String email) {
        return userQueryRepository.findByEmail(email);
    }

}
