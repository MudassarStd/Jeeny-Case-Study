package std.jeeny.rbsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import std.jeeny.rbsys.model.Driver;
import std.jeeny.rbsys.model.RideRequest;
import std.jeeny.rbsys.model.RideStatus;
import std.jeeny.rbsys.model.User;

import java.util.List;

public interface RideRepository extends JpaRepository<RideRequest, Long> {
    List<RideRequest> findByPassenger(User passenger);
    List<RideRequest> findByDriverAndStatus(Driver driver, RideStatus status);
    boolean existsByPassengerAndStatusIn(User passenger, List<RideStatus> statuses);
    boolean existsByDriverAndStatusIn(Driver driver, List<RideStatus> statuses);
}