package com.humuson.test.order;

import com.humuson.test.common.OrderStatus;
import com.humuson.test.common.exception.APIResponseException;
import com.humuson.test.config.StringConfig;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@Getter
public class Order {

    private String orderId; // 주문 ID
    private String customerName; // 고객명
    @DateTimeFormat(pattern = StringConfig.DATE_TIME_FORMAT)
    private LocalDateTime orderDate; // 주문 날짜
    private OrderStatus orderStatus; // 주문 상태
    private String orderItem; // 주문 항목
    private Long orderPrice; // 주문 가격

    @Builder
    public Order(String orderId, String customerName, String orderDate, String orderStatus, String orderItem, Long orderPrice) throws DateTimeParseException {
        this.orderId = orderId;
        this.customerName = customerName;
        this.orderDate = LocalDateTime.parse(orderDate, DateTimeFormatter.ofPattern(StringConfig.DATE_TIME_FORMAT));
        this.orderStatus = OrderStatus.valueOf(orderStatus.replaceAll(" ", "_"));
        this.orderItem = orderItem;
        this.orderPrice = orderPrice;
    }

    public boolean validate() throws APIResponseException {
        if(Objects.isNull(this.getOrderId()) || this.getOrderId().isEmpty()) throw new APIResponseException(HttpStatus.BAD_REQUEST.value(), StringConfig.ERROR_MESSAGE_BAD_ORDER_ID);
        if(Objects.isNull(this.getCustomerName()) || this.getCustomerName().isEmpty()) throw new APIResponseException(HttpStatus.BAD_REQUEST.value(), StringConfig.ERROR_MESSAGE_BAD_CUSTOMER_NAME);
        if(Objects.isNull(this.getOrderDate())) throw new APIResponseException(HttpStatus.BAD_REQUEST.value(), StringConfig.ERROR_MESSAGE_BAD_ORDER_DATE);
        if(Objects.isNull(this.getOrderStatus())) throw new APIResponseException(HttpStatus.BAD_REQUEST.value(), StringConfig.ERROR_MESSAGE_BAD_ORDER_STATUS);
        if(Objects.isNull(this.getOrderItem()) || this.getOrderItem().isEmpty()) throw new APIResponseException(HttpStatus.BAD_REQUEST.value(), StringConfig.ERROR_MESSAGE_BAD_ORDER_ITEM);
        if(Objects.isNull(this.getOrderPrice()) || this.getOrderPrice() < 0) throw new APIResponseException(HttpStatus.BAD_REQUEST.value(), StringConfig.ERROR_MESSAGE_BAD_ORDER_PRICE);
        return true;
    }

}
