package com.example.examen.controller;

import com.example.examen.Repository.DepartmentRepository;
import com.example.examen.dataTransferObject.EmpoyeeDto;
import com.example.examen.entity.Department;
import com.example.examen.entity.Employee;
import com.example.examen.service.EmployeeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "*")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final DepartmentRepository departementRepository;

    public EmployeeController(EmployeeService employeeService, DepartmentRepository departementRepository) {
        this.employeeService = employeeService;
        this.departementRepository = departementRepository;
    }

    @GetMapping("/departement/{id}/employes")
    public List<Employee> getEmployeesByDepartment(@PathVariable Long id) {
        return employeeService.getEmployeesByDepartment(id);
    }

//    @PostMapping("/employes/save")
//    public Employee addEmployee(@ModelAttribute EmpoyeeDto employeeDto) {
//
//        Department department = departementRepository.findById(employeeDto.getDepartmentId())
//                .orElseThrow(() -> new RuntimeException("Département non trouvé"));
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        Employee employee = new Employee();
//        employee.setLastName(employeeDto.getLastName());
//        employee.setFirstName(employeeDto.getFirstName());
//        employee.setEmail(employeeDto.getEmail());
//        employee.setBirthDate(LocalDate.parse(employeeDto.getBirthDate(), formatter));
//        employee.setDepartment(department);
//        return employeeService.saveEmployee(employee, employeeDto.getPhoto());
//    }

    @PostMapping("/employes")
    public Employee addEmpoEmployee(@RequestBody EmpoyeeDto employeeDto){
        Employee employee = new Employee();
        Department department = departementRepository.findById(employeeDto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Département non trouvé"));
        employee.setEmail(employeeDto.getEmail());
        employee.setDepartment(department);
        employee.setLastName(employeeDto.getLastName());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setBirthDate(employeeDto.getBirthDate());

        return this.employeeService.saveEmployee(employee);
    }

    @DeleteMapping("/employes/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/employes/age/{years}")
    public ResponseEntity<?> deleteEmployeesOlderThan(@PathVariable int years) {
        employeeService.deleteEmployeesOlderThan(years);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/employes/{id}")
    public Employee updateEmployee(@PathVariable Long id,
                                   @RequestBody Employee employeeDetails
                                   ) {
        return employeeService.updateEmployee(id, employeeDetails);
    }
}
