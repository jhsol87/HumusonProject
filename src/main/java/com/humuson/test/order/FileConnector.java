package com.humuson.test.order;

import com.humuson.test.client.FileClient;
import com.humuson.test.common.dto.OrderResponse;
import com.humuson.test.common.exception.APIResponseException;
import com.humuson.test.config.StringConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileConnector implements Connector {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FileClient client;

    /*
    orderId,customerName,orderDate,orderStatus,orderItem,orderPrice
    order-275439679110499,홍길동,2024-10-14T10:15:30,배송 완료,상품명,15000
    ...
     */
    public void addOrderFromExternal() throws APIResponseException {
        try {
            List<Order> externalData = client.read();
            for(Order order : externalData) {
                if(order.validate()) orderRepository.setOrder(order);
            }
        } catch (DateTimeParseException ex) {
            throw new APIResponseException(HttpStatus.BAD_REQUEST.value(), StringConfig.ERROR_MESSAGE_BAD_ORDER_DATE);
        } catch (IllegalArgumentException ex) {
            throw new APIResponseException(HttpStatus.BAD_REQUEST.value(), StringConfig.ERROR_MESSAGE_BAD_ORDER_STATUS);
        }
    }

    public OrderResponse getOrder(String id) throws APIResponseException {
        Order order = orderRepository.getOrder(id);
        if(order != null) {
            return OrderResponse.fromEntity(order);
        } else {
            throw new APIResponseException(HttpStatus.NOT_FOUND.value(), StringConfig.ERROR_MESSAGE_NOT_FOUND);
        }
    }

    public List<OrderResponse> getOrderList() {
        List<OrderResponse> responseList = new ArrayList<>();
        List<Order> orderList = orderRepository.getOrderList();
        if(!orderList.isEmpty()) {
            for(Order order : orderList) {
                OrderResponse response = OrderResponse.fromEntity(order);
                responseList.add(response);
            }
        }
        return responseList;
    }
}
