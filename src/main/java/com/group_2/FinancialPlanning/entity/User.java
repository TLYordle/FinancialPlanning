package com.group_2.FinancialPlanning.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Users") // Tên bảng trong database

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    @Size(min = 3, max = 50, message = "Username muse be between 3 and 50 characters")
    @Column(nullable = false, unique = true, length = 50)
    private String userName;

    @Size(min = 0, max = 100, message = "Fullname muse be between 0 and 100 characters")
    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Pattern(regexp = "^(0[3|5|7|8|9])+([0-9]{8})$", message = "Invalid phone number format")
    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dob;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String address;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Department department;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Position position;

    @Column(nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Status status = Status.Active; // Giá trị mặc định

    @Column(columnDefinition = "TEXT")
    private String note;

    // Getters & Setters
//    public enum Department {
//        IT, HR, Finance, Communication, Marketing, Accounting
//    }

//    public enum Position {
//        Financial_Executive, Project_manager, Office_Assistant, Senior_Executive,
//        Accounting_Executive, Junior_Executive, Intern
//    }

    public enum Role {
        Admin, Financial_Staff, Accountant
    }

//    public enum Status {
//        Active, Inactive
//    }
}


