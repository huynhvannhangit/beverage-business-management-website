package com.huynhvannhan.beverage_business_management.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING("Chờ xử lý", "pending"),
    PREPARING("Đang pha chế", "preparing"),
    COMPLETED("Hoàn thành", "completed"),
    PAID("Đã thanh toán", "paid"),
    CANCELLED("Đã hủy", "cancelled");

    private final String displayName;
    private final String value;

    OrderStatus(String displayName, String value) {
        this.displayName = displayName;
        this.value = value;
    }

    // Tìm theo value
    public static OrderStatus fromValue(String value) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid OrderStatus value: " + value);
    }
}
