package com.bartoszthielmann.employeemanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class OfficeDto {

    private int id;
    @NotBlank(message="This field can't be blank")
    @Size(min=1, max=100, message="Length must be between 1-100")
    private String streetAddress;
    @NotBlank(message="This field can't be blank")
    @Size(min=1, max = 100, message="Length must be between 1-100")
    private String city;
    @NotBlank(message="This field can't be blank")
    @Size(min=2, max=2, message="Length must be 2")
    private String country;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
