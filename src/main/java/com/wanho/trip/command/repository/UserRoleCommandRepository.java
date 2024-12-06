package com.wanho.trip.command.repository;

import com.wanho.trip.command.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleCommandRepository extends JpaRepository<UserRole, Long> {

}
