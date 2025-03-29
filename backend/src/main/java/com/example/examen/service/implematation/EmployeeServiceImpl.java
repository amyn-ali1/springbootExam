package com.example.examen.service.implematation;

import com.example.examen.Repository.EmployeeRepository;
import com.example.examen.entity.Employee;
import com.example.examen.service.EmployeeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${upload.path}")
    private String uploadPath;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getEmployeesByDepartment(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return this.employeeRepository.save(employee);
    }

    @Override
    public Employee saveEmployee(Employee employee, MultipartFile photo) {
        logger.info("Saving Employee...");
        Employee employeesaved = employeeRepository.save(employee);
        if (photo != null && !photo.isEmpty()) {
            try {

                String fileExtension = photo.getContentType().split("/")[1];
                String filename = UUID.randomUUID().toString() +  "." + fileExtension;
                String path = "src/main/resources/static/photos/" +  filename;
                photo.transferTo(Path.of(path));
                String photoUrl = "http://localhost:8080/photos/" + filename;
                employee.setPhotoPath(photoUrl);

            } catch (IOException e) {
                throw new RuntimeException("Failed to store photo", e);
            }
        }

        return employeeRepository.save(employeesaved);
    }


    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.findById(id).ifPresent(employee -> {
            if (employee.getPhotoPath() != null) {
                try {
                    Files.deleteIfExists(Paths.get(uploadPath, employee.getPhotoPath()));
                } catch (IOException e) {
                    throw new RuntimeException("Failed to delete photo", e);
                }
            }
            employeeRepository.deleteById(id);
        });
    }

    @Override
    public void deleteEmployeesOlderThan(int years) {
        LocalDate cutoffDate = LocalDate.now().minusYears(years);
        employeeRepository.deleteByBirthDateBefore(cutoffDate);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employeeDetails, MultipartFile photo) {
        return employeeRepository.findById(id).map(employee -> {
            employee.setFirstName(employeeDetails.getFirstName());
            employee.setLastName(employeeDetails.getLastName());
            employee.setEmail(employeeDetails.getEmail());
            employee.setBirthDate(employeeDetails.getBirthDate());
            employee.setDepartment(employeeDetails.getDepartment());

            if (photo != null && !photo.isEmpty()) {
                // Delete old photo if exists
                if (employee.getPhotoPath() != null) {
                    try {
                        Files.deleteIfExists(Paths.get(uploadPath, employee.getPhotoPath()));
                    } catch (IOException e) {
                        logger.error("Failed to delete old photo", e);
                    }
                }

                // Save new photo
                try {
                    String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
                    Path filePath = Paths.get(uploadPath, fileName);
                    Files.copy(photo.getInputStream(), filePath);
                    employee.setPhotoPath(fileName);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to store new photo", e);
                }
            }

            return employeeRepository.save(employee);
        }).orElseThrow(() -> new RuntimeException("Employee not found with id " + id));
    }

    @Override
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setFirstName(employeeDetails.getFirstName());
                    employee.setLastName(employeeDetails.getLastName());
                    employee.setEmail(employeeDetails.getEmail());
                    employee.setBirthDate(employeeDetails.getBirthDate());

                    // Only update department if it's provided in employeeDetails
                    if (employeeDetails.getDepartment() != null) {
                        employee.setDepartment(employeeDetails.getDepartment());
                    }

                    return employeeRepository.save(employee);
                })
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }
}
