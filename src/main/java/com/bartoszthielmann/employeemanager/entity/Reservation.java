package com.bartoszthielmann.employeemanager.entity;

import com.bartoszthielmann.employeemanager.validation.DateRange;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;

@Entity
@Table(name = "reservation")
@DateRange(startField = "start", endField = "end")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Future
    @Column(name = "start")
    private Date start;

    @Future
    @Column(name = "end")
    private Date end;

    @NotNull
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    public Reservation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        user.addReservation(this);
        this.user = user;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        workspace.addReservation(this);
        this.workspace = workspace;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "start=" + start +
                ", end=" + end +
                ", user=" + user +
                ", workspace=" + workspace +
                '}';
    }
}
