package com.appointment.management;

import com.appointment.management.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    Optional<User> findOneByUsername(String username);
    List<User> findByAuthority(String authority);
}
