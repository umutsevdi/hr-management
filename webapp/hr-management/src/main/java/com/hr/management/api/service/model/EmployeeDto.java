package com.hr.management.api.service.model;

import com.hr.management.api.repository.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.hr.management.api.repository.entity.Employee} entity
 */
@Data
@AllArgsConstructor
public class EmployeeDto implements Serializable {
    private Long id;
    private LocalDateTime createdDate;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Character gender;
    private String email;
    private String phoneNumber;
    private String education;
    private Integer experience;
    private String cv;
    private String profile;

    public EmployeeDto(Employee employee) {
        this(
                employee.getId(),
                employee.getCreatedDate(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getDateOfBirth(),
                employee.getGender(),
                employee.getEmail(),
                employee.getPhoneNumber(),
                employee.getEducation(),
                employee.getExperience(),
                employee.getCv(),
                employee.getProfile()
        );
    }
}