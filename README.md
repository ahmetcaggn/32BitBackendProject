
# 32Bit - Toyota Backend Project

The "cash-register" project is a comprehensive Spring Boot application designed to manage various aspects of a retail system. It is structured as a microservices architecture, ensuring modularity, scalability, and maintainability. Each service within the application focuses on a specific business function, facilitating the smooth operation of a retail environment. The entire application is containerized using Docker and deployed using Docker Compose for easy scalability and management. Additionally, the project includes extensive tests to ensure reliability and logging capabilities for effective monitoring and troubleshooting.
## Installation

Clone the project

```bash
  git clone https://github.com/ahmetcaggn/32BitBackendProject.git
```

Get into the root direction

```bash
  cd 32BitBackendProject
```

Compiles the project and packages it into a JAR file.

```bash
  mvn package -DskipTests
```

You can start the entire project with docker compose

```bash
  docker-compose up
```

  
## Used Technologies

- Netflix Eureka server
- Spring Cloud Gateway
- Spring Security
- Apache PDFBox
- PostgreSQL
- JUnit5
- Feign Client 
- Docker
- Log4J2
## API Usage
### Authentication Not Required


#### Fetch all products 

```http
  GET /product
```

#### Fetch product by id 

```http
  GET /product/{id}
```

| Parameter | Type | Required | Description |
| :-------- | :------- | :------- |:-------------------------------- |
| `id`      | `Long` | **yes**|Id of a product|


### Authentication Required

#### Fetch sale by id 

```http
  GET /report/sale/{id}
```

| Parameter | Type | Required | Description |
| :-------- | :------- | :------- |:-------------------------------- |
| `id`      | `Long` | **yes**|Id of a product|

#### Fetch all sale with pagination

```http
  GET /report/sales
```

| Parameter | Type | Required |  Default | Description |
| :-------- | :------- | :------- | :------- |:-------------------------------- |
| `page` | `integer` | **false** | 0 | Page number |
| `rows` | `integer` | **false** | 10 | Row size of a page |
| `sortDirection` | `string` | **false** | DESC | Sorting direction  |
| `sort` | `String` | **false** | id | Sorting category |
| `minPrice` | `Integer` | **false** | 0 | Min price to filter |
| `maxPrice` | `Integer` | **false** |  | Max price to filter |
| `filterProductId` | `Integer` | **false** |  | Filter by product id |
| `filterCampaignId` | `Integer` | **false** |  | Filter by Campaign id |


#### Create a empty sale 

```http
  POST /sales
```

#### Attach a product to a sale by id 

```http
  POST /sales/{saleId}/products/{productId}?quantity={amount}
```

| Parameter | Type | Required | Description |
| :-------- | :------- | :------- |:-------------------------------- |
| `saleId` | `Long` | **yes**| Sale id |
| `productId` | `Long` | **yes**| Product id |
| `quantity` | `Float` | **yes**| Quantity of a product |

#### Complete a Sale by id 

```http
  POST /sales/{saleId}/complete
```

| Parameter | Type | Required | Description |
| :-------- | :------- | :------- |:-------------------------------- |
| `saleId` | `Long` | **yes**| Sale Id |
| `PaymentDto` | `JSON` | **yes**| Cash and credit card amount |


#### Create receipt of a sale 

```http
  GET /report/createPdf/{id}
```

| Parameter | Type | Required | Description |
| :-------- | :------- | :------- |:-------------------------------- |
| `id` | `Long` | **yes** | Id of a sale |



#### Fetch all Campaigns 

```http
  GET /campaigns
```

#### Fetch Campaign by id 

```http
  GET /campaigns/{id}
```

#### Fetch all relevant Campagins by saleId 

```http
  GET /sales/{id}/campaigns
```


| Parameter | Type | Required | Description |
| :-------- | :------- | :------- |:-------------------------------- |
| `id` | `Long` | **yes** | Id of a Sale |

#### Create Campaign

```http
  POST /campaigns
```

| Parameter | Type | Required | Description |
| :-------- | :------- | :------- |:-------------------------------- |
| `CampaignRequestDto` | `JSON` | **yes** | Campaign requirement  |


#### Attach a campaign to a sale 

```http
  POST /sales/{saleId}/campaigns/{campaignId}
```

| Parameter | Type | Required | Description |
| :-------- | :------- | :------- |:-------------------------------- |
| `saleId` | `Long` | **yes** | Sale Id |
| `campaignId` | `Long` | **yes** | Campaign Id |


#### Generate a jwt token

```http
  POST /security/generateToken
```

| Parameter | Type | Required | Description |
| :-------- | :------- | :------- |:-------------------------------- |
| `AuthRequest` | `JSON` | **yes** | Authentication requirement |


#### Generate a jwt token

```http
  POST /security/validateToken
```

| Parameter | Type | Required | Description |
| :-------- | :------- | :------- |:-------------------------------- |
| `TokenValidateDto` | `JSON` | **yes** | Token dto |


#### Get All employees

```http
  GET /employee
```



#### Get employee by username

```http
  GET /employee/{username}
```

| Parameter | Type | Required | Description |
| :-------- | :------- | :------- |:-------------------------------- |
| `username` | `String` | **yes** | Username of a user |



#### Create an new employee

```http
  POST /employee
```

| Parameter | Type | Required | Description |
| :-------- | :------- | :------- |:-------------------------------- |
| `EmployeeRequestDto` | `JSON` | **yes** | Informations about user |



#### Update an employee

```http
  PUT /employee/{employeeId}
```

| Parameter | Type | Required | Description |
| :-------- | :------- | :------- |:-------------------------------- |
| `employeeId` | `Long` | **yes** | Id of a user |
| `EmployeeRequestDto` | `JSON` | **yes** | Informations about user |



#### Delete an employee

```http
  DELETE /employee/{employeeId}
```

| Parameter | Type | Required | Description |
| :-------- | :------- | :------- |:-------------------------------- |
| `employeeId` | `Long` | **yes** | Id of a user |



#### Generate JWT Token

```http
  POST /security/generateToken
```

| Parameter | Type | Required | Description |
| :-------- | :------- | :------- |:-------------------------------- |
| `AuthRequest` | `JSON` | **yes** | User login information |



#### Validate JWT Token

```http
  POST /security/validateToken
```

| Parameter | Type | Required | Description |
| :-------- | :------- | :------- |:-------------------------------- |
| `TokenValidateDto` | `JSON` | **yes** | JWT Token Dto |

### Dto References

PaymentDto

```
{
    "cashAmount":19.99,
    "creditCardAmount": 0
}
```

AuthRequest

```
{
    "username" : "username field",
    "password" : "password field"
}
```

TokenValidateDto

```
{
    "token": "JWT-Token field"
}
```

EmployeeRequestDto

```
{
    "name" : "name field",
    "surname" : "surname field",
    "address" : "address field",
    "phoneNo" : "12354677",
    "username" : "username field",
    "password" : "1234",
    "roles" : ["CASHIER","MANAGER","ADMIN"]
}
```

CampaignRequestDto	

```
{
    "name" : "Secili urunlerde %65 indirim",
    "discountRate": 65,
    "productList": [1,12,13,14,15,16,17,18,19,72]
}
```
