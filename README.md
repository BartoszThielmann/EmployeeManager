# Employee Manager
![demo](https://github.com/BartoszThielmann/EmployeeManager/assets/111487260/cdc3a0df-c007-47ba-b323-05dd1aed1b6d)
Work in progress. Spring Boot demo application for managing employees. Currently the main functionality is to allow
users to reserve workspaces in an office.


The goal is to include/use:
- Spring Boot
- Spring MVC
- Thymeleaf
- Hibernate
- Spring Security
- Authentication Persistence
- Session management
- Unit tests
- Exception handling

# Setup
**1. Clone the repo**

**2. Run MySQL scripts:**
   - `user_setup.sql` script to setup springstudent user
   - `database_setup.sql` script to create database, create tables and create users

**3. Run the app using maven**

Change directories to project root and execute:
```bash
./mvnw spring-boot:run
```

**4. Open the webpage**

Go to `http://localhost:8080/` in your browser