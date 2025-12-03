package com.huynhvannhan.beverage_business_management.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "areas",
        indexes = @Index(name = "idx_area_name", columnList = "name"))
@SQLDelete(sql = "UPDATE areas SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "diningTables")  // Tránh StackOverflow
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // INT cho master data

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 255)
    private String description;

    @Column
    @Builder.Default
    private Boolean isActive = true;

    @Column
    @Builder.Default
    private Boolean isDeleted = false;  // Soft delete

    // Quan hệ One-to-Many với DiningTable
    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL)
    @Builder.Default
    private List<DiningTable> diningTables = new ArrayList<>();

    // Timestamps
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Helper methods
    public void addDiningTable(DiningTable table) {
        diningTables.add(table);
        table.setArea(this);
    }

    public void removeDiningTable(DiningTable table) {
        diningTables.remove(table);
        table.setArea(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Area)) return false;
        return id != null && id.equals(((Area) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
