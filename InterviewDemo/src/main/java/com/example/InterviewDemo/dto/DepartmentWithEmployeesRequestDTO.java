package com.example.InterviewDemo.dto;

import java.util.List;

public class DepartmentWithEmployeesRequestDTO {
    private DepartmentDTO department;
    private List<EmployeeDTO> employees;

    // Getters and Setters
    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    public List<EmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDTO> employees) {
        this.employees = employees;
    }
}

