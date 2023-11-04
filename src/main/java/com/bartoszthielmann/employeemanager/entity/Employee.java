package com.bartoszthielmann.employeemanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name="employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @NotNull(message="This is a required field")
    @Size(min=1, message="This is a required field")
    @Column(name="first_name")
    private String firstName;

    @NotNull(message="This is a required field")
    @Size(min=1, message="This is a required field")
    @Column(name="last_name")
    private String lastName;

    @NotNull(message="This is a required field")
    @Size(min=1, message="This is a required field")
    @Email(message = "Not a correct email address")
    @Pattern(regexp=".*@bth\\.com$", message = "Email must be in domain @bth.com")
    @Column(name="email", unique = true)
    private String email;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    public Employee() {}

    public Employee(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

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

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}