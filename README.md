# [휴머스온] Tech 과제 전형

## 1. 구현 설명

- 다른 형태의 외부 시스템과 연동할 가능성을 고려하여 Connector 인터페이스와 구현체(HttpConnector, FileConnector) 로 구성하였습니다.
  - HTTPConnector 클래스에서 HTTP Request 를 통해 JSON 형식의 외부 데이터를 받아오도록 하였습니다.
    - RestAPIClient 클래스에서 RestClient 를 설정하고 활용하도록 하였습니다. 
    - 임의의 API 경로를 설정하였기에, 테스트를 위해 FileConnector 를 추가로 구현하였습니다.
  - FileConnector 클래스에서 CSV 포맷 파일을 읽어와 저장할 수 있도록 하였습니다.
    - FileClient 클래스에서 설정된 경로의 파일을 읽어와 BufferedReader 를 설정하고 활용하도록 하였습니다.
    - 임의로 생성한 테스트용 파일의 예시는 아래와 같습니다. (orderList.csv)
    - ```
      orderId,customerName,orderDate,orderStatus,orderItem,orderPrice
      order-326796476807000,이민재,2024-10-13T17:28:19,배송 준비 중,냉동식품,33434
      order-326796504145800,최현우,2024-10-10T17:28:19,배송 완료,의류,41445
      order-326796504477800,강서연,2024-10-13T17:28:19,결제 완료,선물세트,75753
      order-326796504694500,강서연,2024-10-11T17:28:19,배송 중,장난감,111734
      order-326796504912500,장서현,2024-10-12T17:28:19,결제 완료,주류,181387
      order-326796505107200,김민준,2024-10-10T17:28:19,배송 중,건강식품,323411
      order-326796505304600,임민지,2024-10-09T17:28:19,배송 준비 중,생활용품,464440
      order-326796505513200,조민서,2024-10-12T17:28:19,배송 준비 중,의류,679579
      order-326796505718200,장서현,2024-10-07T17:28:19,배송 중,장난감,119455
      order-326796505897500,임민지,2024-10-14T17:28:19,배송 준비 중,의류,834656
      ```
- 외부 시스템으로부터 가져온 주문 데이터는 Order 클래스를 통해 포맷을 관리하고, 이를 OrderRepository 클래스에서 메모리에 저장하고 관리되도록 하였습니다.
  - 주문 ID 를 통해 조회를 하기 용이하도록 Map 형태로 관리가 되도록 하였습니다.
- 외부 시스템의 타입과 주문 상태는 Enum 을 활용하여 관리가 되도록 하였습니다.
  - ``` java
    public enum ExternalType {
        HTTP, FILE;
    }
    ```
  - ``` java
    public enum OrderStatus {
        결제_완료, 배송_준비_중, 배송_중, 배송_완료;
    }
    ```
- 다양한 예외 상황에 대한 예외 처리를 공통적인 포맷으로 관리하기 위해 APIResponseException 클래스를 구현하여 활용하였습니다.
- StringConfig 클래스를 통해 반복적인 문자열을 효율적으로 관리하도록 하였습니다.

## 2. 개발 스택

    - Java 17
    - SpringBoot 3.3.1
    - Gradle

## 3. 의존성 관리

    - Spring-Web
    - Project Lombok

## 4. 실행 방법

1. git clone 명령어를 통해 프로젝트를 가져옵니다.
    ```
    git clone https://github.com/jhsol87/HumusonProject.git
    ```
2. application.properties 설정 후 프로젝트 파일을 실행합니다.
    ```
    humuson.external.type: 연동할 외부 시스템 타입 선택 (http or file)
    humuson.external.http.url: HTTP Request 를 통해 연동할 URL
    humuson.external.http.token.key: HTTP Request 를 통해 연동할 API token Header key 값
    humuson.external.http.token.value: HTTP 를 통해 연동할 API token Header value 값
    humuson.external.file.path: CSV 포맷 파일을 통해 연동할 경로
    ```
3. Postman 등을 통해 아래의 예시와 같이 요청하여 각 요청에 맞는 결과를 확인합니다.

## 5. 요청 처리 예시

### 1. 외부 시스템 데이터 연동 후 데이터 저장

- `POST` 요청을 사용해서 외부 시스템에서 가져온 데이터를 저장할 수 있습니다.

Request Example

    POST localhost:8080/api/v1/order

Response Fields

| Path      | Type     | Description |
|-----------|----------|-------------|
| `status`  | `Long`   | HTTP Status |
| `data`    | `String` | 주문 데이터      |
| `message` | `String` | 에러 메세지      |

Response Example
``` http request
HTTP/1.1 201
Content-Type: application/json
Transfer-Encoding: chunked
Date: Wed, 16 Oct 2024 09:28:14 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
    "status": 201,
    "data": null,
    "message": null
}
```

### 2. 주문 ID 에 맞는 주문 데이터 조회

- `GET` 요청을 사용해서 주문 ID 에 맞는 주문 데이터를 조회할 수 있습니다.

Path parameters

    /api/v1/order/{id}

Request Fields

| Path | Type     | Description |
|------|----------|-------------|
| `id` | `String` | 주문 ID       |

Request Example

    GET localhost:8080/api/v1/order/order-326796505718200

Response Fields

| Path      | Type     | Description |
|-----------|----------|-------------|
| `status`  | `Long`   | HTTP Status |
| `data`    | `String` | 주문 데이터      |
| `message` | `String` | 에러 메세지      |

주문 데이터

| Path           | Type     | Description |
|----------------|----------|-------------|
| `orderId`      | `String` | 주문 ID       |
| `customerName` | `String` | 고객명         |
| `orderDate`    | `String` | 주문 날짜       |
| `orderStatus`  | `String` | 주문 상태       |
| `orderItem`    | `String` | 주문 항목       |
| `orderPrice`   | `Long`   | 주문 가격       |

Response Example
``` http request
HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Wed, 16 Oct 2024 09:33:01 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
    "status": 200,
    "data": {
        "orderId": "order-326796505718200",
        "customerName": "장서현",
        "orderDate": "2024-10-07T17:28:19",
        "orderStatus": "배송_중",
        "orderItem": "장난감",
        "orderPrice": 119455
    },
    "message": null
}
```

### 3. 전체 주문 데이터 조회

- `GET` 요청을 사용해서 전체 주문 데이터를 조회할 수 있습니다.

Request Example

    GET localhost:8080/api/v1/order

Response Fields

| Path      | Type     | Description |
|-----------|----------|-------------|
| `status`  | `Long`   | HTTP Status |
| `data`    | `String` | 주문 데이터      |
| `message` | `String` | 에러 메세지      |

주문 데이터

| Path           | Type     | Description |
|----------------|----------|-------------|
| `orderId`      | `String` | 주문 ID       |
| `customerName` | `String` | 고객명         |
| `orderDate`    | `String` | 주문 날짜       |
| `orderStatus`  | `String` | 주문 상태       |
| `orderItem`    | `String` | 주문 항목       |
| `orderPrice`   | `Long`   | 주문 가격       |

Response Example
``` http request
HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Wed, 16 Oct 2024 09:33:04 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
    "status": 200,
    "data": [
        {
            "orderId": "order-326796504694500",
            "customerName": "강서연",
            "orderDate": "2024-10-11T17:28:19",
            "orderStatus": "배송_중",
            "orderItem": "장난감",
            "orderPrice": 111734
        },
        {
            "orderId": "order-326796504145800",
            "customerName": "최현우",
            "orderDate": "2024-10-10T17:28:19",
            "orderStatus": "배송_완료",
            "orderItem": "의류",
            "orderPrice": 41445
        },
        {
            "orderId": "order-326796505304600",
            "customerName": "임민지",
            "orderDate": "2024-10-09T17:28:19",
            "orderStatus": "배송_준비_중",
            "orderItem": "생활용품",
            "orderPrice": 464440
        },
        {
            "orderId": "order-326796476807000",
            "customerName": "이민재",
            "orderDate": "2024-10-13T17:28:19",
            "orderStatus": "배송_준비_중",
            "orderItem": "냉동식품",
            "orderPrice": 33434
        },
        {
            "orderId": "order-326796504912500",
            "customerName": "장서현",
            "orderDate": "2024-10-12T17:28:19",
            "orderStatus": "결제_완료",
            "orderItem": "주류",
            "orderPrice": 181387
        },
        {
            "orderId": "order-326796505513200",
            "customerName": "조민서",
            "orderDate": "2024-10-12T17:28:19",
            "orderStatus": "배송_준비_중",
            "orderItem": "의류",
            "orderPrice": 679579
        },
        {
            "orderId": "order-326796505107200",
            "customerName": "김민준",
            "orderDate": "2024-10-10T17:28:19",
            "orderStatus": "배송_중",
            "orderItem": "건강식품",
            "orderPrice": 323411
        },
        {
            "orderId": "order-326796504477800",
            "customerName": "강서연",
            "orderDate": "2024-10-13T17:28:19",
            "orderStatus": "결제_완료",
            "orderItem": "선물세트",
            "orderPrice": 75753
        },
        {
            "orderId": "order-326796505718200",
            "customerName": "장서현",
            "orderDate": "2024-10-07T17:28:19",
            "orderStatus": "배송_중",
            "orderItem": "장난감",
            "orderPrice": 119455
        },
        {
            "orderId": "order-326796505897500",
            "customerName": "임민지",
            "orderDate": "2024-10-14T17:28:19",
            "orderStatus": "배송_준비_중",
            "orderItem": "의류",
            "orderPrice": 834656
        }
    ],
    "message": null
}
```