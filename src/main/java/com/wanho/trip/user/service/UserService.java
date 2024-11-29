package com.wanho.trip.user.service;


import com.wanho.trip.common.auth.JwtTokenProvider;
import com.wanho.trip.common.auth.TokenInfo;
import com.wanho.trip.common.exception.InvalidInputException;
import com.wanho.trip.common.response.APIResponse;
import com.wanho.trip.common.status.Role;
import com.wanho.trip.user.dto.UserDTO;
import com.wanho.trip.user.entity.User;
import com.wanho.trip.user.entity.UserRole;
import com.wanho.trip.user.repository.UserRepository;
import com.wanho.trip.user.repository.UserRoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    public Long signUp(UserDTO.SignUpReq signUpReq) {
        userRepository.findByEmail(signUpReq.getEmail()).ifPresent(
                user -> {
                    throw new InvalidInputException("email", "duplicate email");
                });

        User user = signUpReq.toEntity(bCryptPasswordEncoder.encode(signUpReq.getPassword()));

        userRepository.save(user);

        UserRole userRole = UserRole.builder()
                .role(Role.USER)
                .user(user)
                .build();
        userRoleRepository.save(userRole);


        return user.getId();
    }

    public TokenInfo login(UserDTO.LoginReq loginReq) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.createToken(authentication);
    }

    public UserDTO.InfoRes userInfo(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new InvalidInputException("id", "invalid id"));
        return new UserDTO.InfoRes(user);
    }
}
