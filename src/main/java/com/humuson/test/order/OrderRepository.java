package com.humuson.test.order;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderRepository {

    private final Map<String, Order> orderMap;

    OrderRepository() { this.orderMap = new HashMap<>(); }

    public Order getOrder(String id) {
        return orderMap.get(id);
    }

    public List<Order> getOrderList() {
        List<Order> result = new ArrayList<>();
        for(String id : orderMap.keySet()) {
            result.add(orderMap.get(id));
        }
        return result;
    }

    public void setOrder(Order order) {
        String id = order.getOrderId();
        orderMap.put(id, order);
    }
}
