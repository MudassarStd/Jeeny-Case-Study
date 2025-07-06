package std.jeeny.rbsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import std.jeeny.rbsys.model.AvailabilityStatus;
import std.jeeny.rbsys.model.Driver;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findByAvailabilityStatus(AvailabilityStatus status);
    Driver findByName(String name);
}
