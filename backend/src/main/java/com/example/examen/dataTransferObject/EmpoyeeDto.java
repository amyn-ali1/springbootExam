package com.example.examen.dataTransferObject;

import lombok.*;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter

public class EmpoyeeDto {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private Long departmentId;

    public Long getDepartmentId() {
        return departmentId;
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
}
