package com.huynhvannhan.beverage_business_management.model;

import com.huynhvannhan.beverage_business_management.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders",
        indexes = {
                @Index(name = "idx_order_status_date", columnList = "status,created_at"),
                @Index(name = "idx_orders_code", columnList = "code")
        })
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"table", "cashier", "barista", "orderItems"})
public class Order extends BaseEntity {

    @Column(nullable = false, unique = true, length = 20)
    private String code;  // Mã đơn hàng: ORD20241202001

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @Builder.Default
    private OrderType orderType = OrderType.DINE_IN;

    // Quan hệ Many-to-One với DiningTable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id", foreignKey = @ForeignKey(name = "FK_orders_tables"))
    private DiningTable table;  // Có thể null nếu TAKEAWAY

    @Column(precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal totalDrinkPrice = BigDecimal.ZERO;

    @Column(precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal totalToppingPrice = BigDecimal.ZERO;

    @Column(precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(nullable = false, precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal finalAmount = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;

    // Quan hệ Many-to-One với Employee (Thu ngân)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cashier_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_orders_cashier"))
    private Employee cashier;

    // Quan hệ Many-to-One với Employee (Pha chế)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barista_id",
            foreignKey = @ForeignKey(name = "FK_orders_barista"))
    private Employee barista;  // Có thể null ban đầu

    @Column(length = 100)
    private String customerName;

    @Column(length = 15)
    private String customerPhone;

    @Column(length = 500)
    private String note;

    // Quan hệ One-to-Many với OrderItem
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    // Helper methods
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
        orderItem.setOrder(null);
    }

    // Tính tổng tiền tự động,  )( Chuyển sang Service)
//    public void calculateTotalAmount() {
//        this.totalDrinkPrice = orderItems.stream()
//                .map(OrderItem::getSubtotal)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        this.totalToppingPrice = orderItems.stream()
//                .flatMap(item -> item.getToppings().stream())
//                .map(OrderItemTopping::getTotalPrice)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        BigDecimal total = totalDrinkPrice.add(totalToppingPrice);
//        this.finalAmount = total.subtract(discountAmount);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        return getId() != null && getId().equals(((Order) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
