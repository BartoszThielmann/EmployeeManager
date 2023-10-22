# Employee Manager
![Screenshot from 2023-10-22 16-12-05](https://github.com/BartoszThielmann/EmployeeManager/assets/111487260/35e95131-d3ea-464a-8238-b7c7d0404114)
Work in progress. Spring Boot demo application for managing employees. 
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