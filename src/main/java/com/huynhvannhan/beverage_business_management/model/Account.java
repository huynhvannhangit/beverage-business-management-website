package com.huynhvannhan.beverage_business_management.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "accounts",
        indexes = {
                @Index(name = "idx_account_username", columnList = "username"),
                @Index(name = "idx_account_email", columnList = "email")
        })
@SQLDelete(sql = "UPDATE accounts SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"password", "employee", "roles"})  // Loại trừ mật khẩu và quan hệ
public class Account {

    @Id
    @org.hibernate.annotations.UuidGenerator
    @Column(length = 36, updatable = false, nullable = false)
    private String id;  // UUID dạng String

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;  // BCrypt encoded

    @Column(length = 100)
    private String email;

    @Column(length = 100)
    private String fullName;

    @Column(length = 20)
    private String phone;

    @Column(nullable = false)
    @Builder.Default
    private Boolean enabled = true;

    @Column
    @Builder.Default
    private Boolean isDeleted = false;  // Soft delete

    // Quan hệ One-to-One với Employee
    @OneToOne
    @JoinColumn(name = "employee_id", unique = true)
    private Employee employee;

    // EAGER để Security load quyền ngay khi login (Theo file PDF)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_roles",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    // Audit fields
    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(length = 50)
    private String createdBy;

    @Column(length = 50)
    private String updatedBy;

    // JPA Lifecycle callback
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Helper methods để quản lý quan hệ Many-to-Many
    public void addRole(Role role) {
        roles.add(role);
        role.getAccounts().add(this);
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.getAccounts().remove(this);
    }

    // Custom equals dựa trên ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        return id != null && id.equals(((Account) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
