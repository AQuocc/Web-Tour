# Travel Tour Website

A Java Spring Boot web application for managing travel tour bookings. The application provides functionality for users to browse and book tours, and for administrators to manage tours and bookings.

## Features

### User Features
- User registration and authentication
- Browse available tours
- Search tours by destination, price range, and dates
- View tour details
- Book tours
- Manage personal bookings
- View booking history

### Admin Features
- Tour management (Create, Read, Update, Delete)
- Booking management
- View all bookings
- Approve/reject booking requests
- User management

## Technology Stack

- Java 17
- Spring Boot 3.2.3
- Spring Security
- Spring Data JPA
- Thymeleaf
- MySQL
- Bootstrap 5
- HTML/CSS/JavaScript

## Project Setup

### Prerequisites
- Java Development Kit (JDK) 17 or higher
- MySQL Server
- Maven

### Database Configuration
Configure your MySQL database connection in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/travel_tour?createDatabaseIfNotExist=true
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Building and Running
1. Clone the repository
2. Navigate to the project directory
3. Build the project:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```
5. Access the application at `http://localhost:8080`

### Default Admin Account
The application creates a default admin account on first run:
- Username: admin
- Password: admin

## Project Structure

```
travel-tour/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/travel/
│   │   │       ├── controller/    # Web controllers
│   │   │       ├── model/        # Entity classes
│   │   │       ├── repository/   # Data access layer
│   │   │       ├── security/     # Security configuration
│   │   │       └── service/      # Business logic
│   │   └── resources/
│   │       ├── static/          # Static resources (CSS, JS)
│   │       ├── templates/       # Thymeleaf templates
│   │       └── application.properties
└── pom.xml
```

## Security

The application implements role-based access control:
- ROLE_USER: Regular users who can browse and book tours
- ROLE_ADMIN: Administrators who can manage tours and bookings

## Features Implementation

### Tour Management
- Complete CRUD operations for tours
- Image upload support
- Search and filtering capabilities
- Availability management

### Booking System
- Real-time availability checking
- Booking confirmation process
- Cancellation handling
- Email notifications (can be implemented)

### User Management
- Secure registration and login
- Password encryption
- Profile management
- Booking history

## Future Enhancements

1. Payment Integration
   - Add payment gateway integration
   - Support multiple payment methods

2. Review System
   - Allow users to rate and review tours
   - Display average ratings

3. Advanced Search
   - Add more search filters
   - Implement sorting options

4. Email Notifications
   - Booking confirmations
   - Tour updates
   - Reminder emails

5. Report Generation
   - Booking reports
   - Revenue reports
   - Popular tours analytics