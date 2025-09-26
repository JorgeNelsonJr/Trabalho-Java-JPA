package com.streaming.streaming.repository;

import com.streaming.streaming.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
