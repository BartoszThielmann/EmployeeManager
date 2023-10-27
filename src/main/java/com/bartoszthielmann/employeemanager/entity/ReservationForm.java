package com.bartoszthielmann.employeemanager.entity;

import java.sql.Date;

public class ReservationForm {
    /**
     * Object used to gather user input in reservation-form.
     * Since user input is sent from form to server in the form of strings it is necessary to then convert that
     * information into actual Reservation object based on the fields.
     * This is sort of a DTO object.
    */
    private Date start;
    private Date end;
    private int employeeId;
    private int workspaceId;

    public ReservationForm() {
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

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(int workspaceId) {
        this.workspaceId = workspaceId;
    }

    @Override
    public String toString() {
        return "ReservationForm{" +
                "start=" + start +
                ", end=" + end +
                ", employeeId=" + employeeId +
                ", workspaceId=" + workspaceId +
                '}';
    }
}
