package com.bartoszthielmann.employeemanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "workspace")
public class Workspace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank(message="This field can't be blank")
    @Size(min=1, max=50, message="Length must be between 1-50")
    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "office_id")
    private Office office;

    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    public Workspace() {
    }

    public Workspace(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "Workspace{" +
                "name='" + name + '\'' +
                '}';
    }
}
