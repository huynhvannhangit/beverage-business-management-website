package com.huynhvannhan.beverage_business_management.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_item_toppings")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"orderItem", "topping"})
public class OrderItemTopping extends BaseEntity {

    // Owning Side - Quan hệ với OrderItem
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_item_toppings_item"))
    private OrderItem orderItem;

    // Owning Side - Quan hệ với Topping
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topping_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_item_toppings_topping"))
    private Topping topping;

    // QUAN TRỌNG: Snapshot fields (Giá tại thời điểm đặt)
    @Column(nullable = false, length = 100)
    private String toppingNameSnapshot;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal toppingPriceSnapshot;

    @Column(nullable = false)
    @Builder.Default
    private Integer quantity = 1;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    // Helper method - Tính tổng giá
    public void calculateTotalPrice() {
        this.totalPrice = toppingPriceSnapshot.multiply(new BigDecimal(quantity));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItemTopping)) return false;
        return getId() != null && getId().equals(((OrderItemTopping) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
