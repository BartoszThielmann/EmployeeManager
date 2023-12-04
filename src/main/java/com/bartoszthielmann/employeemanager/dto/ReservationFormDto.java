package com.bartoszthielmann.employeemanager.dto;

import com.bartoszthielmann.employeemanager.validation.DateRange;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;

@DateRange(startField = "start", endField = "end")
public class ReservationFormDto {

    @Future private Date start;

    @Future private Date end;

    @NotNull private int userId;

    @NotNull private int workspaceId;

    private int officeId;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int employeeId) {
        this.userId = employeeId;
    }

    public int getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(int workspaceId) {
        this.workspaceId = workspaceId;
    }

    public int getOfficeId() {
        return officeId;
    }

    public void setOfficeId(int officeId) {
        this.officeId = officeId;
    }

    @Override
    public String toString() {
        return "ReservationFormDto{" +
                "start=" + start +
                ", end=" + end +
                ", userId=" + userId +
                ", workspaceId=" + workspaceId +
                ", officeId=" + officeId +
                '}';
    }
}
