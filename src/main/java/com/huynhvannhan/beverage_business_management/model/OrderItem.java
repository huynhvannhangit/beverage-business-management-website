package com.huynhvannhan.beverage_business_management.model;

import com.huynhvannhan.beverage_business_management.enums.OrderItemStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_items",
        indexes = @Index(name = "idx_order_items", columnList = "order_id,product_id"))
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"order", "product", "toppings"})
public class OrderItem extends BaseEntity {

    // Owning Side
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_order_items_order"))
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_order_items_product"))
    private Product product;

    // QUAN TRỌNG: Snapshot fields (Giá tại thời điểm đặt)
    @Column(nullable = false, length = 100)
    private String productNameSnapshot;

    @Column(nullable = false, length = 50)
    private String sizeNameSnapshot;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPriceSnapshot;

    @Column(nullable = false)
    @Builder.Default
    private Integer quantity = 1;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    @Column
    @Builder.Default
    private Integer iceLevel = 3;  // 0-5: Ít đá -> Nhiều đá

    @Column
    @Builder.Default
    private Integer sugarLevel = 3;  // 0-5: Ít đường -> Nhiều đường

    @Column(length = 255)
    private String note;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @Builder.Default
    private OrderItemStatus status = OrderItemStatus.QUEUE;

    @OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItemTopping> toppings = new ArrayList<>();

    // Helper methods
    public void addTopping(OrderItemTopping topping) {
        toppings.add(topping);
        topping.setOrderItem(this);
    }

    public void removeTopping(OrderItemTopping topping) {
        toppings.remove(topping);
        topping.setOrderItem(null);
    }

    // Tính subtotal tự động
    public void calculateSubtotal() {
        this.subtotal = unitPriceSnapshot.multiply(new BigDecimal(quantity));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;
        return getId() != null && getId().equals(((OrderItem) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
