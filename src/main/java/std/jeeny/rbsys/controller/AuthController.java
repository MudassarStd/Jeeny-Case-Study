package std.jeeny.rbsys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import std.jeeny.rbsys.dto.AuthRequestDTO;
import std.jeeny.rbsys.model.Driver;
import std.jeeny.rbsys.model.User;
import std.jeeny.rbsys.service.AuthService;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    String register(@RequestBody AuthRequestDTO authRequest) {
        return authService.register(authRequest);
    }

    @PostMapping("/login")
    String login(@RequestBody AuthRequestDTO authRequest) {
        return authService.login(authRequest);
    }

    // Test endpoints
    @GetMapping("/users")
    List<User> getUsers() {
        return authService.getUsers();
    }

    @GetMapping("/drivers")
    List<Driver> getDrivers() {
        return authService.getDrivers();
    }

}
