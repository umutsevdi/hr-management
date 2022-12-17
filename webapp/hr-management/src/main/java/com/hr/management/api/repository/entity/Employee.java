package com.hr.management.api.repository.entity;

import com.hr.management.api.service.model.EmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table
@Entity(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    // Generic information
    @Column(name = "CREATED")
    private LocalDateTime createdDate;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "DOB")
    private LocalDate dateOfBirth;
    @Column
    private Character gender;
    // Communication Related Information
    @Column(unique = true, nullable = false)
    private String email;
    @Column(name = "PHONE", unique = true)
    private String phoneNumber;

    // Work Related Information
    @Column(nullable = false)
    private String education;
    @Column(name = "XP", nullable = false)
    private Double experience;
    @Column(nullable = false)
    private String cv;
    @Column
    private String profile;

    public Employee(EmployeeDto employee) {
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
