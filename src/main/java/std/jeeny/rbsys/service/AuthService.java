package std.jeeny.rbsys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import std.jeeny.rbsys.dto.AuthRequestDTO;
import std.jeeny.rbsys.model.AvailabilityStatus;
import std.jeeny.rbsys.model.Driver;
import std.jeeny.rbsys.model.User;
import std.jeeny.rbsys.model.UserRole;
import std.jeeny.rbsys.repository.DriverRepository;
import std.jeeny.rbsys.repository.UserRepository;

import java.util.List;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DriverRepository driverRepository;

    public String register(AuthRequestDTO authRequest) {
        if (userRepository.findByUsername(authRequest.getUsername()) != null) {
            return "User already exists";
        }

        User user = new User(authRequest.getUsername(), authRequest.getPassword(), authRequest.getRole());
        userRepository.save(user);

        if (UserRole.DRIVER.name().equalsIgnoreCase(authRequest.getRole())) {
            Driver driver = new Driver(user.getUsername());
            driver.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
            driverRepository.save(driver);
        }

        return "Registered successfully as " + authRequest.getRole();
    }

    public String login(AuthRequestDTO authRequest) {
        User user = userRepository.findByUsername(authRequest.getUsername());
        if (user != null) {
            if (user.getPassword().equals(authRequest.getPassword())) {
                return "Login successful as " + user.getRole();
            } else {
                return "Invalid password";
            }
        } else {
            return "User not found";
        }
    }


    // for testing

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public List<Driver> getDrivers() {
        return driverRepository.findAll();
    }
}
