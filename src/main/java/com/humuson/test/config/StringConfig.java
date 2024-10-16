package com.humuson.test.config;

public class StringConfig {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DEFAULT_FILE_ENCODING = "UTF-8";
    public static final String KEY_ORDER_ID = "orderId";
    public static final String KEY_CUSTOMER_NAME = "customerName";
    public static final String KEY_ORDER_DATE = "orderDate";
    public static final String KEY_ORDER_STATUS = "orderStatus";
    public static final String KEY_ORDER_ITEM = "orderItem";
    public static final String KEY_ORDER_PRICE = "orderPrice";
    public static final String DEFAULT_FILE_HEADER = KEY_ORDER_ID.concat(",")
            .concat(KEY_CUSTOMER_NAME).concat(",")
            .concat(KEY_ORDER_DATE).concat(",")
            .concat(KEY_ORDER_STATUS).concat(",")
            .concat(KEY_ORDER_ITEM).concat(",")
            .concat(KEY_ORDER_PRICE);
    public static final String ERROR_MESSAGE_4XX_PATTERN = "%s 외부 요청 오류";
    public static final String ERROR_MESSAGE_5XX_PATTERN = "%s 외부 시스템 오류";
    public static final String ERROR_MESSAGE_BAD_URI_FORMAT = "URI 형식에 문제가 있습니다.";
    public static final String ERROR_MESSAGE_BAD_ORDER_ID = "주문 ID 에 문제가 있습니다.";
    public static final String ERROR_MESSAGE_BAD_CUSTOMER_NAME = "고객명에 문제가 있습니다.";
    public static final String ERROR_MESSAGE_BAD_ORDER_DATE = "주문 날짜에 문제가 있습니다.";
    public static final String ERROR_MESSAGE_BAD_ORDER_STATUS = "주문 상태에 문제가 있습니다.";
    public static final String ERROR_MESSAGE_BAD_ORDER_ITEM = "주문 항목에 문제가 있습니다.";
    public static final String ERROR_MESSAGE_BAD_ORDER_PRICE = "주문 가격에 문제가 있습니다.";
    public static final String ERROR_MESSAGE_NOT_FOUND = "해당 주문 ID 에 대한 데이터가 없습니다.";
    public static final String ERROR_MESSAGE_NOT_FOUND_FILE = "해당 파일이 존재하지 않습니다.";
    public static final String ERROR_MESSAGE_UNSUPPORTED_ENCODING = "지원하지 않는 인코딩 형식입니다.";
    public static final String ERROR_MESSAGE_FILE_READ_IO = "파일을 읽는데 문제가 있습니다.";
    public static final String ERROR_MESSAGE_FILE_CLOSE_IO = "파일 읽기를 끝내는 데 문제가 있습니다.";
}
