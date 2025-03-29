package com.example.examen.service.implematation;

import com.example.examen.Repository.DepartmentRepository;
import com.example.examen.entity.Department;
import com.example.examen.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpt implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpt(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    @Override
    public List<Department> getAllDepatment() {
        return departmentRepository.findAll();
    }
}
