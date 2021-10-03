package com.appointment.management;

import com.appointment.management.data.User;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Secured("ROLE_ADMIN")
public class UserEndpoint {
    private final UserService userService;

    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }

    @PostMapping ("/create")
    User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("/edit/{id}")
    User editUser(@RequestBody User userEdits, @PathVariable String id) {
        return userService.editUser(userEdits, id).orElse(null);
    }

    @DeleteMapping("/delete/{id}")
    void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

    @GetMapping("/view/{id}")
    User viewUser(@PathVariable String id) {
        return userService.viewUser(id).orElse(null);
    }

    @GetMapping("/view")
    List<User> viewAllUsers(){
        return userService.viewAllUsers();
    }
}
