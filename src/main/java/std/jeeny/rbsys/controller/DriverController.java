package std.jeeny.rbsys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import std.jeeny.rbsys.model.AvailabilityStatus;
import std.jeeny.rbsys.model.RideRequest;
import std.jeeny.rbsys.service.RideService;

import java.util.List;

@RestController
@RequestMapping("/api/driver")
public class DriverController {

    @Autowired
    private RideService rideService;

    @GetMapping("/pending-requests")
    List<RideRequest> getPendingRequests(@RequestParam String driverName) {
        return rideService.getPendingRequests(driverName);
    }

    @PostMapping("/respond/{id}")
    String respondToRideRequest(@PathVariable Long id, @RequestParam String driverName, @RequestParam boolean accept) {
        return rideService.respondToRide(id, driverName, accept);
    }

    @PostMapping("/start-ride/{id}")
    String startRide(@PathVariable Long id, @RequestParam String driverName) {
        return rideService.startRide(id, driverName);
    }

    @PostMapping("/complete-ride/{id}")
    String completeRide(@PathVariable Long id, @RequestParam String driverName) {
        return rideService.completeRide(id, driverName);
    }

    @PostMapping("/set-status")
    String setStatus(@RequestParam String driverName, @RequestParam AvailabilityStatus status) {
        return rideService.setStatus(driverName, status);
    }
}
