package com.huynhvannhan.beverage_business_management.model;

import com.huynhvannhan.beverage_business_management.enums.TableStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "dining_tables",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_area_table_name",
                columnNames = {"area_id", "name"}
        ),
        indexes = @Index(name = "idx_table_name", columnList = "name"))
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "area")
public class DiningTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column
    @Builder.Default
    private Integer seatCount = 2;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    @Builder.Default
    private TableStatus status = TableStatus.EMPTY;

    // Owning Side - giữ foreign key
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_tables_areas"))
    private Area area;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiningTable)) return false;
        return id != null && id.equals(((DiningTable) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
