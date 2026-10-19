# Bus Booking System

A RESTful backend application built using Java, Spring Boot, Spring Data JPA, and MySQL. This project manages bus details, user registration, ticket bookings, seat availability, and booking cancellations.

## Technologies Used

* Java 25
* Spring Boot
* Spring Data JPA
* Hibernate
* MySQL
* Maven
* REST API
* Postman

## Features

### Bus Management

* Create a bus record.
* Retrieve all buses.
* Retrieve a bus by ID.
* Update bus details.
* Delete a bus.
* Search buses by source and destination.

### User Management

* Register new users.
* Retrieve all registered users.
* Validate user information.
* Prevent duplicate email registration.

### Booking Management

* Book tickets for registered users.
* Validate user and bus IDs.
* Validate the number of seats requested.
* Check seat availability before confirming a booking.
* Automatically decrease available seats after booking.
* Retrieve all bookings.
* Retrieve bookings by user ID.
* Cancel confirmed bookings.
* Restore available seats after cancellation.
* Prevent duplicate cancellation.

### Error Handling

* Return meaningful error messages.
* Handle invalid booking requests.
* Reject attempts to cancel an already cancelled booking.

## Prerequisites

Install the following before running the application:

* Java 25
* MySQL
* IntelliJ IDEA or another Java IDE
* Postman (for API testing)

## Database Setup

Create a MySQL database:

```sql
CREATE DATABASE bus_booking_system;
```

Configure your database connection in `application.properties` using environment variables.

Example configuration:

```properties
spring.datasource.url=${DB_URL:jdbc:mysql://localhost:3306/bus_booking_system}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

Set `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` in your local environment.

**Security:** Never commit database passwords or other credentials to GitHub.

## Run the Application

### Windows

Run the following commands from the project root:

```bash
mvnw.cmd test
mvnw.cmd spring-boot:run
```

### macOS/Linux

```bash
./mvnw test
./mvnw spring-boot:run
```

The application runs on:

`http://localhost:8080`

## REST API Endpoints

### User APIs

| Method | Endpoint              | Description        |
| ------ | --------------------- | ------------------ |
| POST   | `/api/users/register` | Register a user    |
| GET    | `/api/users`          | Retrieve all users |

Example registration request:

```json
{
  "name": "Asha Rao",
  "email": "asha@example.com",
  "phoneNumber": "9876543210"
}
```

### Bus APIs

| Method | Endpoint                                                 | Description          |
| ------ | -------------------------------------------------------- | -------------------- |
| POST   | `/api/buses`                                             | Create a bus         |
| GET    | `/api/buses`                                             | Retrieve all buses   |
| GET    | `/api/buses/{id}`                                        | Retrieve a bus by ID |
| PUT    | `/api/buses/{id}`                                        | Update a bus         |
| DELETE | `/api/buses/{id}`                                        | Delete a bus         |
| GET    | `/api/buses/search?source=Chennai&destination=Bangalore` | Search buses         |

### Booking APIs

| Method | Endpoint                                       | Description                  |
| ------ | ---------------------------------------------- | ---------------------------- |
| POST   | `/api/bookings?userId=1&busId=2&seatsBooked=2` | Create a booking             |
| GET    | `/api/bookings`                                | Retrieve all bookings        |
| GET    | `/api/bookings/user/{userId}`                  | Retrieve bookings for a user |
| PUT    | `/api/bookings/{bookingId}/cancel`             | Cancel a booking             |

#### Example: Create a Booking

Send a `POST` request to:

`http://localhost:8080/api/bookings?userId=1&busId=2&seatsBooked=2`

No request body is required.

Replace the IDs with existing user and bus IDs.

#### Example: Cancel a Booking

Send a `PUT` request to:

`http://localhost:8080/api/bookings/1/cancel`

Replace `1` with the booking ID you want to cancel.

A successful cancellation updates the booking status to `Cancelled` and restores the booked seats to the bus.

## Testing

Use Postman to test the REST API endpoints.

Recommended scenarios:

* Register a user.
* Create and search for buses.
* Book tickets and verify seat availability.
* Reject bookings when insufficient seats remain.
* Cancel a booking and verify that seats are restored.
* Reject attempts to cancel the same booking twice.
* Verify error responses for invalid IDs.

Run automated tests using Maven before publishing the project.

## Project Structure

```text
src/main/java/com/BusBooking/BusBookingSystem/
├── Controller/
├── Model/
├── Repository/
├── Service/
├── Exception/
└── BusBookingSystemApplication.java
```

* **Controller:** Handles HTTP requests and responses.
* **Model:** Defines the application's data entities.
* **Repository:** Provides database access through Spring Data JPA.
* **Service:** Implements business logic.
* **Exception:** Handles application errors.

## Future Improvements

* Add a frontend for users to search buses and book tickets.
* Add authentication and authorization.
* Add Docker support.
* Improve automated test coverage.

## Author

Mohamed S
