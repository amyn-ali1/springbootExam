package com.example.examen.Repository;

import com.example.examen.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.time.LocalDate;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByDepartmentId(Long departmentId);

    @Modifying
    @Query("DELETE FROM Employee e WHERE e.birthDate <= :cutoffDate")
    void deleteByBirthDateBefore(LocalDate cutoffDate);
}
