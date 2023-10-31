package com.bartoszthielmann.employeemanager.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "office")
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank(message="This field can't be blank")
    @Size(min=1, max=100, message="Length must be between 1-100")
    @Column(name = "street_address")
    private String streetAddress;

    @NotBlank(message="This field can't be blank")
    @Size(min=1, max = 100, message="Length must be between 1-100")
    @Column(name = "city")
    private String city;

    @NotBlank(message="This field can't be blank")
    @Size(min=2, max=2, message="Length must be 2")
    @Column(name = "country")
    private String country;

    @Valid
    @OneToMany(mappedBy = "office", cascade = CascadeType.ALL)
    private List<Workspace> workspaces;

    public Office() {
    }

    public Office(String streetAddress, String city, String country) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.country = country;
    }

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

    public List<Workspace> getWorkspaces() {
        return workspaces;
    }

    public void setWorkspaces(List<Workspace> workspaces) {
        this.workspaces = workspaces;
    }

    public void addWorkspace() {
        if (this.workspaces == null) {
            this.workspaces = new ArrayList<>();
        }
        this.workspaces.add(new Workspace());
    }

    @Override
    public String toString() {
        return "Office{" +
                "streetAddress='" + streetAddress + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", workspaces='" + workspaces + '\'' +
                '}';
    }
}
