package com.example.InterviewDemo.service;

import com.example.InterviewDemo.dto.DepartmentDTO;
import com.example.InterviewDemo.dto.EmployeeDTO;
import com.example.InterviewDemo.model.Department;
import com.example.InterviewDemo.model.Employee;
import com.example.InterviewDemo.repository.DepartmentRepository;
import com.example.InterviewDemo.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public DepartmentDTO createDepartmentWithEmployees(DepartmentDTO departmentDTO, List<EmployeeDTO> employeeDTOs) {
        // Map DepartmentDTO to Department entity
        Department department = new Department();
        department.setName(departmentDTO.getName());
        department.setLocation(departmentDTO.getLocation());

        // Save Department entity
        Department savedDepartment = departmentRepository.save(department);

        // Map EmployeeDTOs to Employee entities and associate them with the Department
        List<Employee> employees = employeeDTOs.stream().map(dto -> {
            Employee employee = new Employee();
            employee.setName(dto.getName());
            employee.setEmail(dto.getEmail());
            employee.setPosition(dto.getPosition());
            employee.setSalary(dto.getSalary());
            employee.setDepartment(savedDepartment);
            return employee;
        }).collect(Collectors.toList());

        // Save all Employee entities
        employeeRepository.saveAll(employees);

        // Return the saved Department as a DTO
        DepartmentDTO savedDepartmentDTO = new DepartmentDTO();
        savedDepartmentDTO.setName(savedDepartment.getName());
        savedDepartmentDTO.setLocation(savedDepartment.getLocation());
        return savedDepartmentDTO;
    }

    private DepartmentDTO mapToDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setLocation(department.getLocation());
        return dto;
    }

    public DepartmentDTO createDepartment(DepartmentDTO dto) {
        Department department = mapToEntity(dto);
        department = departmentRepository.save(department);
        return mapToDTO(department);
    }

    private Department mapToEntity(DepartmentDTO dto) {
        Department department = new Department();
        department.setName(dto.getName());
        department.setLocation(dto.getLocation());
        return department;
    }
}
