package com.bartoszthielmann.employeemanager.dto;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;

public class ReservationDto {

    private int id;
    @Future
    private Date start;
    @Future
    private Date end;
    @NotNull
    private UserDto user;
    @NotNull
    private WorkspaceDto workspace;

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

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public WorkspaceDto getWorkspace() {
        return workspace;
    }

    public void setWorkspace(WorkspaceDto workspace) {
        this.workspace = workspace;
    }
}
