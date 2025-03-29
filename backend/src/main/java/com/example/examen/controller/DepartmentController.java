package com.example.examen.controller;


import com.example.examen.entity.Department;
import com.example.examen.service.DepartmentService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "*")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/getAllDepatment")
    public List<Department> getAll(){
        return this.departmentService.getAllDepatment();
    }
}
