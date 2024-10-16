package com.humuson.test.order;

import com.humuson.test.common.dto.OrderResponse;
import com.humuson.test.common.exception.APIResponseException;

import java.util.List;

public interface Connector {

    void addOrderFromExternal() throws APIResponseException;

    OrderResponse getOrder(String id) throws APIResponseException;

    List<OrderResponse> getOrderList();
}
