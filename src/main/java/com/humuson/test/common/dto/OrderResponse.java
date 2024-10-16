package com.humuson.test.common.dto;

import com.humuson.test.config.StringConfig;
import com.humuson.test.order.Order;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderResponse {

    private String orderId; // 주문 ID
    private String customerName; // 고객명
    @DateTimeFormat(pattern = StringConfig.DATE_TIME_FORMAT)
    private LocalDateTime orderDate; // 주문 날짜
    private String orderStatus; // 주문 상태
    private String orderItem; // 주문 항목
    private Long orderPrice; // 주문 가격

    public static OrderResponse fromEntity(Order order) {
        return new OrderResponse(
                order.getOrderId(),
                order.getCustomerName(),
                order.getOrderDate(),
                order.getOrderStatus().name(),
                order.getOrderItem(),
                order.getOrderPrice()
        );
    }
}
