package com.huynhvannhan.beverage_business_management.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@SQLDelete(sql = "UPDATE products SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"category", "productSizes"})  // Tránh StackOverflow
public class Product extends BaseEntity {

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 500)
    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    @Builder.Default
    private Boolean isActive = true;

    @Column
    @Builder.Default
    private Boolean isDeleted = false;  // Soft delete

    // Quan hệ Many-to-One với Category
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_products_categories"))
    private Category category;

    // Quan hệ One-to-Many với ProductSize
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductSize> productSizes = new ArrayList<>();

    // Helper methods để quản lý quan hệ bidirectional
    public void addProductSize(ProductSize productSize) {
        productSizes.add(productSize);
        productSize.setProduct(this);
    }

    public void removeProductSize(ProductSize productSize) {
        productSizes.remove(productSize);
        productSize.setProduct(null);
    }

    // Custom equals dựa trên ID từ BaseEntity
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        return getId() != null && getId().equals(((Product) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
