package std.jeeny.rbsys.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import std.jeeny.rbsys.dto.RideRequestDTO;
import std.jeeny.rbsys.model.*;
import std.jeeny.rbsys.repository.DriverRepository;
import std.jeeny.rbsys.repository.RideRepository;
import std.jeeny.rbsys.repository.UserRepository;

import java.util.List;

@Service
public class RideService {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DriverRepository driverRepository;

    private static final Logger logger = LoggerFactory.getLogger(RideService.class);

    public String requestRide(RideRequestDTO rideRequest) {

        User passenger = userRepository.findByUsername(rideRequest.getUsername());
        if (passenger == null) return "User not found";

        if (!UserRole.PASSENGER.name().equalsIgnoreCase(passenger.getRole())) {
            return "Only passengers can request rides";
        }

        boolean hasActiveRide = rideRepository.existsByPassengerAndStatusIn(passenger,
                List.of(RideStatus.ACCEPTED, RideStatus.IN_PROGRESS));
        if (hasActiveRide) return "You already have a ride in progress or pending.";

        Driver driver = driverRepository.findByName(rideRequest.getDriverName());
        if (driver == null || driver.getAvailabilityStatus() != AvailabilityStatus.AVAILABLE) {
            return "Requested driver is unavailable or not found";
        }

        RideRequest ride = new RideRequest();
        ride.setPickupLocation(rideRequest.getPickup());
        ride.setDropLocation(rideRequest.getDrop());
        ride.setType(rideRequest.getType());
        ride.setStatus(RideStatus.REQUESTED);
        ride.setPassenger(passenger);
        ride.setDriver(driver);

        rideRepository.save(ride);

        return "Ride requested with driver " + rideRequest.getDriverName() + ". Ride ID: " + ride.getId();
    }

    public List<Driver> getAvailableDrivers() {
        return driverRepository.findByAvailabilityStatus(AvailabilityStatus.AVAILABLE);
    }


    public String getRideStatus(Long rideId) {
        return rideRepository.findById(rideId)
                .map(ride -> "Ride status: " + ride.getStatus())
                .orElse("Ride not found");
    }

    public List<RideRequest> getRideHistory(String username) {
        User passenger = userRepository.findByUsername(username);
        return rideRepository.findByPassenger(passenger);
    }


    // Driver actions
    public List<RideRequest> getPendingRequests(String driverName) {
        logger.info("Fetching pending requests for driver: {}", driverName);
        Driver driver = driverRepository.findByName(driverName);
        System.out.println("driver: "+driver.getName());

        if (driver == null) {
            System.out.println("driver is null");
            return List.of();
        }
        return rideRepository.findByDriverAndStatus(driver, RideStatus.REQUESTED);
    }

    public String respondToRide(Long rideId, String driverName, boolean accept) {
        Driver driver = driverRepository.findByName(driverName);
        if (driver == null) {
            return "Invalid driver";
        }

        boolean hasActiveRide = rideRepository.existsByDriverAndStatusIn(driver,
                List.of(RideStatus.ACCEPTED, RideStatus.IN_PROGRESS));
        if (accept && hasActiveRide) return "Driver already has an ongoing ride.";

        RideRequest ride = rideRepository.findById(rideId).orElse(null);
        if (ride == null || !ride.getDriver().getId().equals(driver.getId())) {
            return "Ride not found or unauthorized";
        }

        if (ride.getStatus() != RideStatus.REQUESTED) {
            return "Ride already responded";
        }

        if (accept) {
            ride.setStatus(RideStatus.ACCEPTED);
            rideRepository.save(ride);
            return "Ride accepted";
        } else {
            ride.setStatus(RideStatus.REJECTED);
            rideRepository.save(ride);
            return "Ride rejected";
        }
    }

    public String startRide(Long rideId, String driverName) {
        Driver driver = driverRepository.findByName(driverName);
        RideRequest ride = rideRepository.findById(rideId).orElse(null);

        if (ride == null || !ride.getDriver().getId().equals(driver.getId())) {
            return "Ride not found or unauthorized";
        }

        if (ride.getStatus() != RideStatus.ACCEPTED) {
            return "Ride must be accepted before starting";
        }

        ride.setStatus(RideStatus.IN_PROGRESS);
        rideRepository.save(ride);

        return "Ride started and in progress";
    }

    public String completeRide(Long rideId, String driverName) {
        Driver driver = driverRepository.findByName(driverName);
        RideRequest ride = rideRepository.findById(rideId).orElse(null);

        if (ride == null || !ride.getDriver().getId().equals(driver.getId())) {
            return "Ride not found or unauthorized";
        }

        if (ride.getStatus() != RideStatus.IN_PROGRESS) {
            return "Ride must be started to complete";
        }

        ride.setStatus(RideStatus.COMPLETED);
        rideRepository.save(ride);

        driver.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
        driverRepository.save(driver);

        return "Ride completed";
    }

    public String setStatus(String driverName,  AvailabilityStatus status) {
        Driver driver = driverRepository.findByName(driverName);
        driver.setAvailabilityStatus(status);
        driverRepository.save(driver);
        return "Availability Status Updated";
    }
}
