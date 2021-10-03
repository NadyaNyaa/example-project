package com.appointment.management.security;

import com.appointment.management.data.User;
import com.appointment.management.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ConfigurationProperties("application.admin")
public class AdminConfiguration {
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Bean
    ApplicationRunner adminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.existsByUsername(user.getUsername())) {
                return;
            }
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            userRepository.save(user);
        };
    }
}

