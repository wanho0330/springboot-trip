package com.wanho.trip.command.service;


import com.wanho.trip.command.dto.UserDTO;
import com.wanho.trip.command.mapper.UserCommandMapper;
import com.wanho.trip.command.mapper.UserRoleCommandMapper;
import com.wanho.trip.command.model.User;
import com.wanho.trip.command.model.UserRole;
import com.wanho.trip.command.repository.UserCommandRepository;
import com.wanho.trip.command.repository.UserRoleCommandRepository;
import com.wanho.trip.query.service.UserQueryService;
import com.wanho.trip.shared.auth.JwtTokenProvider;
import com.wanho.trip.shared.exception.ErrorCode;
import com.wanho.trip.shared.exception.InvalidInputException;
import com.wanho.trip.shared.status.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCommandService {
    private final UserCommandRepository userCommandRepository;
    private final UserRoleCommandRepository userRoleCommandRepository;

    private final UserQueryService userQueryService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserDTO.SignUpRes signUp(UserDTO.SignUpReq signUpReq) {
        if (userQueryService.existsByEmail(signUpReq.getEmail())) {
            throw new InvalidInputException("signUp", ErrorCode.DUPLICATE_EMAIL);
        }

        String encodedPassword = bCryptPasswordEncoder.encode(signUpReq.getPassword());
        User user = UserCommandMapper.fromSignUpReq(signUpReq, encodedPassword);
        userCommandRepository.save(user);

        UserRole userRole = UserRoleCommandMapper.fromUser(user, Role.USER);
        userRoleCommandRepository.save(userRole);

        return UserDTO.SignUpRes.builder()
                .id(user.getId())
                .build();
    }


}
