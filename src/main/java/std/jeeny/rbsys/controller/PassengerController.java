package std.jeeny.rbsys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import std.jeeny.rbsys.dto.RideRequestDTO;
import std.jeeny.rbsys.model.Driver;
import std.jeeny.rbsys.model.RideRequest;
import std.jeeny.rbsys.service.RideService;

import java.util.List;

@RestController
@RequestMapping("/api/passenger")
public class PassengerController {

    @Autowired
    private RideService rideService;

    @PostMapping("/request-ride")
    public String requestRide(@RequestBody RideRequestDTO rideRequest) {
        return rideService.requestRide(rideRequest);
    }

    @GetMapping("/available-drivers")
    public List<Driver> getAvailableDrivers() {
        return rideService.getAvailableDrivers();
    }

    @GetMapping("/ride-status/{id}")
    public String getRideStatus(@PathVariable Long id) {
        return rideService.getRideStatus(id);
    }

    @GetMapping("/ride-history")
    public List<RideRequest> getRideHistory(@RequestParam String username) {
        return rideService.getRideHistory(username);
    }
}