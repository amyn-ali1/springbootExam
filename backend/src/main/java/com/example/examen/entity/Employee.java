package com.example.examen.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private String photoPath;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;


    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public Department getDepartment() {
        return department;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
