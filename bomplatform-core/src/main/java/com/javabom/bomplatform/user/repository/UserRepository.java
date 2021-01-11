package com.javabom.bomplatform.user.repository;

import com.javabom.bomplatform.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
