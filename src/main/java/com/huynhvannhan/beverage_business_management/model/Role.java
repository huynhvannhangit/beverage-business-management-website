package com.huynhvannhan.beverage_business_management.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles",
        indexes = @Index(name = "idx_role_name", columnList = "name"))
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "accounts")  // Tránh vòng lặp
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // BIGINT cho Role

    @Column(nullable = false, unique = true, length = 50)
    private String name;  // ROLE_ADMIN, ROLE_CASHIER, ROLE_BARISTA

    @Column(length = 255)
    private String description;

    // Inverse Side của Many-to-Many
    @ManyToMany(mappedBy = "roles")
    @Builder.Default
    private Set<Account> accounts = new HashSet<>();

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

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Helper methods cho quan hệ Many-to-Many
    public void addAccount(Account account) {
        accounts.add(account);
        account.getRoles().add(this);
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
        account.getRoles().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        return id != null && id.equals(((Role) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
