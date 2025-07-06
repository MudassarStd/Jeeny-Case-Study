# Mini Ride Booking System

A lite backend prototype for a ride-hailing app, designed for testing in smaller cities. This project simulates the core backend logic and API flows for booking rides between locations, without real-time maps or GPS integration.

## Features

- **User Registration & Login** (Passenger/Driver)
- **Request a Ride** (Passenger)
  - Enter pickup and drop-off locations
  - Choose ride type (Bike, Car, Rickshaw)
- **View Ride Status** (Passenger)
- **View Ride History** (Passenger)
- **Driver Actions**
  - Accept or reject ride requests
  - Start and complete rides
  - Set availability status

## Tech Stack
- Java 17
- Spring Boot 3.5+
- Spring Data JPA
- MySQL
- Maven

## Project Structure

```
mini-ride-booking-system/
├── src/
│   ├── main/
│   │   ├── java/std/jeeny/rbsys/
│   │   │   ├── controller/      # REST controllers (Passenger, Driver, Auth)
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── model/           # Domain models (User, Driver, RideRequest, etc.)
│   │   │   ├── repository/      # Spring Data JPA repositories
│   │   │   ├── service/         # Business logic
│   │   │   └── MiniRideBookingSystemApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/std/jeeny/rbsys/
│           └── MiniRideBookingSystemApplicationTests.java
├── pom.xml
└── README.md
```

## API Overview

### Auth
- `POST /api/auth/register` — Register as passenger or driver
- `POST /api/auth/login` — Login
- `GET /api/auth/users` — List all users (test)
- `GET /api/auth/drivers` — List all drivers (test)

### Passenger
- `POST /api/passenger/request-ride` — Request a ride
- `GET /api/passenger/ride-status/{id}` — Get ride status
- `GET /api/passenger/ride-history` — Get ride history
- `GET /api/passenger/available-drivers` — See available drivers

### Driver
- `GET /api/driver/pending-requests?driverName=...` — List pending ride requests for driver
- `POST /api/driver/respond/{id}?driverName=...&accept=true|false` — Accept/reject a ride
- `POST /api/driver/start-ride/{id}?driverName=...` — Start a ride
- `POST /api/driver/complete-ride/{id}?driverName=...` — Complete a ride
- `POST /api/driver/set-status?driverName=...&status=AVAILABLE|BUSY` — Set driver availability

## Example: Register & Book a Ride

1. **Register a Passenger**
   ```json
   POST /api/auth/register
   {
     "username": "alice",
     "password": "pass123",
     "role": "PASSENGER"
   }
   ```
2. **Register a Driver**
   ```json
   POST /api/auth/register
   {
     "username": "bob",
     "password": "pass456",
     "role": "DRIVER"
   }
   ```
3. **Passenger Requests a Ride**
   ```json
   POST /api/passenger/request-ride
   {
     "username": "alice",
     "pickup": "Mall Road",
     "drop": "Airport",
     "type": "CAR",
     "driverName": "bob"
   }
   ```
4. **Driver Accepts Ride**
   ```
   POST /api/driver/respond/{rideId}?driverName=bob&accept=true
   ```
5. **Driver Starts Ride**
   ```
   POST /api/driver/start-ride/{rideId}?driverName=bob
   ```
6. **Driver Completes Ride**
   ```
   POST /api/driver/complete-ride/{rideId}?driverName=bob
   ```
   
## License
This project is for demo purposes.
