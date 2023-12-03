package com.bartoszthielmann.employeemanager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class UserInfoDto {

    private int id;
    private String firstName;
    private String lastName;
    @Email
    @Pattern(regexp=".*@bth\\.com$", message = "Email must be in domain @bth.com")
    private String email;
    private UserDto user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
