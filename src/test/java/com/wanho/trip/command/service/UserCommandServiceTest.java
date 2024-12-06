package com.wanho.trip.command.service;

import com.wanho.trip.command.model.User;
import com.wanho.trip.command.repository.UserCommandRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class UserCommandServiceTest {

    @Autowired
    private UserCommandRepository userCommandRepository;

    @Test
    @DisplayName("회원 가입 테스트")
    void signUp() {
        // given
        User user = User.builder()
                .email("test@test.com")
                .password("12345")
                .name("test")
                .build();

        // when
        User savedUser = userCommandRepository.save(user);

        // then
        Assertions.assertNotNull(savedUser);
        Assertions.assertNotNull(savedUser.getId());
        Assertions.assertEquals(savedUser.getEmail(), user.getEmail());
        Assertions.assertEquals(savedUser.getPassword(), user.getPassword());
        Assertions.assertEquals(savedUser.getName(), user.getName());

    }

    @Test
    @DisplayName("로그인 테스트")
    void login() {
    }

    @Test
    @DisplayName("내 정보 테스트")
    void userInfo() {
    }
}