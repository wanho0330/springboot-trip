package com.wanho.trip.user.service;


import com.wanho.trip.common.auth.JwtTokenProvider;
import com.wanho.trip.common.auth.TokenInfo;
import com.wanho.trip.common.exception.ErrorCode;
import com.wanho.trip.common.exception.InvalidInputException;
import com.wanho.trip.common.status.Role;
import com.wanho.trip.user.dto.UserDTO;
import com.wanho.trip.user.entity.User;
import com.wanho.trip.user.entity.UserRole;
import com.wanho.trip.user.repository.UserRepository;
import com.wanho.trip.user.repository.UserRoleRepository;
import com.wanho.trip.user.util.UserMapper;
import com.wanho.trip.user.util.UserRoleMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserDTO.SignUpRes signUp(UserDTO.SignUpReq signUpReq) {
        if (userRepository.existsByEmail(signUpReq.getEmail())) {
            throw new InvalidInputException("signUp", ErrorCode.DUPLICATE_EMAIL);
        }

        String encodedPassword = bCryptPasswordEncoder.encode(signUpReq.getPassword());
        User user = UserMapper.fromSignUpReq(signUpReq, encodedPassword);
        userRepository.save(user);

        UserRole userRole = UserRoleMapper.fromUser(user, Role.USER);
        userRoleRepository.save(userRole);

        return UserDTO.SignUpRes.builder()
                .id(user.getId())
                .build();
    }

    public TokenInfo login(UserDTO.SignInReq signInReq) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signInReq.getEmail(), signInReq.getPassword());
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.createToken(authenticate);
    }

    public UserDTO.InfoRes userInfo(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new InvalidInputException("userInfo", ErrorCode.USER_NOT_FOUND));
        return UserMapper.toInfoRes(user);
    }

    public Page<UserDTO.ListRes> userList(int num, String email) {
        Pageable pageable = PageRequest.of(num, 20);
        Page<User> allByEmailContaining = userRepository.findAllByEmailContainingOrderByNameAsc(email, pageable);
        return UserMapper.toListRes(allByEmailContaining);
    }


}
