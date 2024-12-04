package com.wanho.trip.user.repository;


import com.wanho.trip.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Page<User> findAllByEmailContainingOrderByNameAsc(String email, Pageable pageable);
}
