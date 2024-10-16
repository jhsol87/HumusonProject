package com.humuson.test.client;

import com.humuson.test.common.ApiResult;
import com.humuson.test.common.exception.APIResponseException;
import com.humuson.test.config.AppProperties;
import com.humuson.test.config.StringConfig;
import com.humuson.test.order.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class RestAPIClient {

    private final String url;
    private final String tokenKey;
    private final String tokenValue;

    private final RestClient client;

    RestAPIClient(AppProperties appProperties) {
        this.url = appProperties.externalHttpUrl;
        this.tokenKey = appProperties.externalHttpTokenKey;
        this.tokenValue = appProperties.externalHttpTokenValue;
        this.client = RestClient.create();
    }

    public ApiResult<List<Order>> get() throws APIResponseException {
        try {
            return client.method(HttpMethod.GET)
                    .uri(url)
                    .header(tokenKey, tokenValue)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (httpRequest, clientHttpResponse) -> {
                        throw new APIResponseException(clientHttpResponse.getStatusCode().value(), String.format(StringConfig.ERROR_MESSAGE_4XX_PATTERN, clientHttpResponse.getStatusCode().value()));
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, (httpRequest, clientHttpResponse) -> {
                        throw new APIResponseException(clientHttpResponse.getStatusCode().value(), String.format(StringConfig.ERROR_MESSAGE_5XX_PATTERN, clientHttpResponse.getStatusCode().value()));
                    })
                    .body(ApiResult.class);
        } catch (IllegalArgumentException ex) {
            throw new APIResponseException(HttpStatus.BAD_REQUEST.value(), StringConfig.ERROR_MESSAGE_BAD_URI_FORMAT);
        }
    }
}
