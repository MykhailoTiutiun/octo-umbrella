package com.mykhailotiutiun.userservice.repository;

import com.mykhailotiutiun.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
