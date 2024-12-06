package com.wanho.trip.query.repository;


import com.wanho.trip.query.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserQueryRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
    Page<User> findAllByEmailContainingOrderByNameAsc(String email, Pageable pageable);
}
