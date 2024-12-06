package com.wanho.trip.command.repository;


import com.wanho.trip.command.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCommandRepository extends JpaRepository<User, Long> {
}
