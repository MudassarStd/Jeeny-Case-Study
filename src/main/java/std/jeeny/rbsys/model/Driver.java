package std.jeeny.rbsys.model;

import jakarta.persistence.*;

@Entity
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;

    public Driver(String name) {
        this.name = name;
        this.availabilityStatus = AvailabilityStatus.AVAILABLE;
    }

    public Driver() {}

    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public Long getId() {
        return id;
    }
}

