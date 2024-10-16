package com.humuson.test.order;

import com.humuson.test.client.RestAPIClient;
import com.humuson.test.common.ApiResult;
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
public class HTTPConnector implements Connector {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestAPIClient client;

    /*
    {
        "status": 200,
        "data": [
            {
                "orderId": order-275439679110499,
                "customerName": "홍길동",
                "orderDate": "2024-10-14T10:15:30",
                "orderStatus": "배송 완료",
                "orderItem": "상품명",
                "orderPrice": 15000
            },
            ...
        ],
        "message": null
    }
     */
    public void addOrderFromExternal() throws APIResponseException {
        try {
            ApiResult<List<Order>> externalData = client.get();
            if(!externalData.getData().isEmpty()) {
                for(Order order : externalData.getData()) {
                    if(order.validate()) orderRepository.setOrder(order);
                }
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
