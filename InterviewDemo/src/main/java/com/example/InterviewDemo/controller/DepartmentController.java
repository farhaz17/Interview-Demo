package com.example.InterviewDemo.controller;

import com.example.InterviewDemo.dto.DepartmentDTO;
import com.example.InterviewDemo.dto.DepartmentWithEmployeesRequestDTO;
import com.example.InterviewDemo.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public List<DepartmentDTO> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @PostMapping
    public DepartmentDTO createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        return departmentService.createDepartment(departmentDTO);
    }

    @PostMapping("/department-employee")
    public DepartmentDTO createDepartmentWithEmployees(
            @RequestBody DepartmentWithEmployeesRequestDTO requestDTO) {
        return departmentService.createDepartmentWithEmployees(
                requestDTO.getDepartment(),
                requestDTO.getEmployees()
        );
    }
}
