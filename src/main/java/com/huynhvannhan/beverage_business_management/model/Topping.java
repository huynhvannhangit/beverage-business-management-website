package com.huynhvannhan.beverage_business_management.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "toppings")
@SQLDelete(sql = "UPDATE toppings SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Topping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // INT cho master data

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal price;

    @Column(length = 255)
    private String description;

    @Column(length = 500)
    private String imageUrl;

    @Column
    @Builder.Default
    private Boolean isActive = true;

    @Column
    @Builder.Default
    private Boolean isDeleted = false;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Topping)) return false;
        return id != null && id.equals(((Topping) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
