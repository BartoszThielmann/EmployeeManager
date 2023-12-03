package com.bartoszthielmann.employeemanager.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class WorkspaceDto {

    private int id;
    @NotBlank(message="This field can't be blank")
    @Size(min=1, max=50, message="Length must be between 1-50")
    private String name;
    private OfficeDto office;

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

    public OfficeDto getOffice() {
        return office;
    }

    public void setOffice(OfficeDto office) {
        this.office = office;
    }
}
