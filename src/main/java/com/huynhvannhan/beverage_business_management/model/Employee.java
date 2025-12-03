package com.huynhvannhan.beverage_business_management.model;

import com.huynhvannhan.beverage_business_management.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "account")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // INT cho Employee

    @Column(nullable = false, unique = true, length = 20)
    private String code;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;

    private LocalDate dateOfBirth;

    @Column(unique = true, length = 20)
    private String citizenId;

    @Column(length = 500)
    private String address;

    @Column(unique = true, length = 15)
    private String phone;

    @Column(unique = true, length = 100)
    private String email;

    private LocalDate startDate;

    @Column(precision = 12, scale = 2)
    private BigDecimal baseSalary;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private Shift shift;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    @Builder.Default
    private WorkingStatus workingStatus = WorkingStatus.WORKING;

    @Column(length = 500)
    private String imageUrl;

    // Inverse Side của One-to-One
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private Account account;

    // Timestamps
    @Column(updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private LocalDateTime createdAt;

    @org.hibernate.annotations.UpdateTimestamp
    private LocalDateTime updatedAt;

    // equals() và hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        return id != null && id.equals(((Employee) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
