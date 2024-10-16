package com.humuson.test.controller;

import com.humuson.test.common.ApiResult;
import com.humuson.test.common.dto.OrderResponse;
import com.humuson.test.common.exception.APIResponseException;
import com.humuson.test.config.AppProperties;
import com.humuson.test.common.ExternalType;
import com.humuson.test.order.FileConnector;
import com.humuson.test.order.HTTPConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final String type;
    @Autowired
    private HTTPConnector httpConnector;
    @Autowired
    private FileConnector fileConnector;

    OrderController(AppProperties appProperties) {
        type = appProperties.externalType.toUpperCase();
    }

    @PostMapping
    public ResponseEntity<ApiResult<OrderResponse>> addOrderFromExternal() {
        try {
            if(type.equals(ExternalType.HTTP.name())) {
                httpConnector.addOrderFromExternal();
            } else if(type.equals(ExternalType.FILE.name())) {
                fileConnector.addOrderFromExternal();
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResult.success(null, HttpStatus.CREATED));
        } catch (APIResponseException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ApiResult.fail(HttpStatus.valueOf(ex.getStatusCode()), ex.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<OrderResponse>> getOrder(@PathVariable("id") String id) {
        try {
            OrderResponse response = null;
            if(type.equals(ExternalType.HTTP.name())) {
                response = httpConnector.getOrder(id);
            } else if(type.equals(ExternalType.FILE.name())) {
                response = fileConnector.getOrder(id);
            }
            return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(response, HttpStatus.OK));
        } catch (APIResponseException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ApiResult.fail(HttpStatus.valueOf(ex.getStatusCode()), ex.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResult<List<OrderResponse>>> getOrderList() {
        List<OrderResponse> response = null;
        if(type.equals(ExternalType.HTTP.name())) {
            response = httpConnector.getOrderList();
        } else if(type.equals(ExternalType.FILE.name())) {
            response = fileConnector.getOrderList();
        }
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(response, HttpStatus.OK));
    }
}
