package com.wanho.trip.shared.auth;


import com.wanho.trip.query.model.User;
import com.wanho.trip.command.repository.UserCommandRepository;
import com.wanho.trip.query.service.UserQueryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthUserDetailService implements UserDetailsService {
    private final UserCommandRepository userCommandRepository;

    private final UserQueryService userQueryService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userQueryService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        return createUserDetails(user);
    }

    private UserDetails createUserDetails(User user) {
        List<SimpleGrantedAuthority> authorities = user.getUserRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))
                .toList();

        return new AuthUser(user.getId(), user.getEmail(), user.getPassword(), authorities);
    }
}
