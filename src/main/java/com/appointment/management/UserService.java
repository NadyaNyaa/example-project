package com.appointment.management;

import com.appointment.management.data.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String userRole;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, @Value("${application.appuser.user.authority}") String userRole) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRole = userRole;
    }

    public User saveUser(User user) {
        var oUser = userRepository.findOneByUsername(user.getUsername());
        if (oUser.isPresent()) return oUser.get();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthority(userRole);
        userRepository.save(user);
        return user;
    }

    public Optional<User> editUser(User userEdits, String id) {
        var oUser = userRepository.findById(Long.parseLong(id));
        if (oUser.isEmpty()) return Optional.empty();
        var user = oUser.get();
        user.setUsername(userEdits.getUsername());
        user.setPassword(passwordEncoder.encode(userEdits.getPassword()));
        userRepository.save(user);
        return Optional.of(user);
    }

    public void deleteUser(String id) {
        var oUser = userRepository.findById(Long.parseLong(id));
        if (oUser.isEmpty()) return;
        userRepository.delete(oUser.get());
    }

    public Optional<User> viewUser(String id) {
        return userRepository.findById(Long.parseLong(id));
    }

    public List<User> viewAllUsers() {
        return userRepository.findByAuthority("ROLE_APPUSER");
    }
}
