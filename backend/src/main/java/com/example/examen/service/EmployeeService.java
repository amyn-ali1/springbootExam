package com.example.examen.service;

import com.example.examen.entity.Employee;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmployeeService {
    List<Employee> getEmployeesByDepartment(Long departmentId);
    Employee saveEmployee(Employee employee);
    Employee saveEmployee(Employee employee, MultipartFile photo);
    void deleteEmployee(Long id);
    void deleteEmployeesOlderThan(int years);
    Employee updateEmployee(Long id, Employee employeeDetails, MultipartFile photo);
    Employee updateEmployee(Long id, Employee employeeDetails);
}
