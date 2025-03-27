# Egglectricity

Egglectricity is a Spring Boot application for managing an online store for electrical products. It uses Thymeleaf for templating, Spring Security for authentication, and a MySQL database.

## Setup Instructions

1. **Clone the Repository**
   ```sh
   git clone https://github.com/Jsalvar124/Egglectricity.git
   cd egglectricity
   ```

2. **Database Setup**
   > This application uses MySQL. By default, MySQL runs on port `3306`, but on the development machine, port `3307` was used because `3306` was occupied.
   - You can use `3306` if it's available on your system.
   - Create a new database:
     ```sql
     CREATE DATABASE egglectricity;
     ```
   - Update the `application.properties` file with the correct port if necessary.

3. **Run the Application**
   ```sh
   mvn spring-boot:run
   ```

## Technologies & Dependencies

The project uses the following technologies:

- **Spring Boot 3.4.3**
- **Thymeleaf** for server-side rendering
- **Spring Security** for authentication and authorization
- **Spring Data JPA** for database interaction
- **MySQL** as the database
- **Lombok** to reduce boilerplate code
- **Spring Boot DevTools** for development convenience
- **JUnit & Spring Security Test** for testing

Refer to the `pom.xml` file for the full list of dependencies.

## Features
- User authentication and authorization with Spring Security
- Dynamic web pages using Thymeleaf
- Secure database interaction with JPA
- MVC architecture for clean separation of concerns

## Notes
- Ensure MySQL is running before starting the application.
- If you experience database connection issues, check that the correct port is used in `application.properties`.


