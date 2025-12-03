package com.huynhvannhan.beverage_business_management.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "product_sizes")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "product")
public class ProductSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Owning Side
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_product_sizes_product"))
    private Product product;

    @Column(nullable = false, length = 50)
    private String sizeName;  // S, M, L, Default

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    // equals() và hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductSize)) return false;
        return id != null && id.equals(((ProductSize) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
