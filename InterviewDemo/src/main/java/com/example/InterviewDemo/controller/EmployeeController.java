package com.example.InterviewDemo.controller;

import com.example.InterviewDemo.dto.DepartmentDTO;
import com.example.InterviewDemo.dto.EmployeeDTO;
import com.example.InterviewDemo.service.DepartmentService;
import com.example.InterviewDemo.service.EmployeeService;
import com.example.InterviewDemo.service.PdfReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PdfReportService pdfReportService;


    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping("/department/{departmentId}")
    public EmployeeDTO addEmployee(@PathVariable Long departmentId, @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.addEmployee(departmentId, employeeDTO);
    }

    @GetMapping("/departments/{departmentId}")
    public List<EmployeeDTO> getEmployeesInDepartment(@PathVariable Long departmentId) {
        return employeeService.getEmployeesByDepartment(departmentId);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployeeInDepartment(
            @PathVariable Long employeeId) {
        employeeService.deleteEmployeeInDepartment(employeeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/report")
    public ResponseEntity<byte[]> getEmployeeReport() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        byte[] pdfReport = pdfReportService.generateEmployeeReport(employees);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employee_report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfReport);
    }

}
