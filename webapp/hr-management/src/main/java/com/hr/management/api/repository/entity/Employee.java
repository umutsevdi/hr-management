package com.hr.management.api.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table
@Entity(name = "employee")
public class Employee extends BaseEntity {
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
    private Integer experience;
    @Column(name = "XP", nullable = false)
    private String cv;
    @Column
    private String profile;

}
