package std.jeeny.rbsys.dto;

import std.jeeny.rbsys.model.RideType;

public class RideRequestDTO {
    private String username;
    private String pickup;
    private String drop;
    private RideType type;
    private String driverName;

    public String getUsername() {
        return username;
    }

    public String getPickup() {
        return pickup;
    }

    public String getDrop() {
        return drop;
    }

    public RideType getType() {
        return type;
    }

    public String getDriverName() {
        return driverName;
    }
}
