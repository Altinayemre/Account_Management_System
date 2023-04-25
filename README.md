# Spring Boot Application


---
This project provides to create account for customers.

### Summary
Evaluation consists of an API to be used to open a new "current account" for a new customer created.
customers.

#### Requirements

• The API will expose an endpoint which accepts the user information (customerID,
initialCredit).

• Once the endpoint is called, a new account will be opened connected to the user whose ID is
customerID.

• Also, if initialCredit is not 0, a transaction will be sent to the new account.

• Another Endpoint will output the user information showing Name, Surname, balance, and
transactions of the accounts.
___
The application has 2 apis
* AccountAPI
* CustomerAPI

```html
POST /v1/account - creates a new account for customer
GET /v1/customer/{customerId} - retrieves a customer
GET /v1/customer - retrieves all customers
POST /v1/customer - creates a new customer
```

JUnit test coverage is 100% are available.


### Tech Stack

---
- Java 17
- Spring Boot
- Spring Data JPA
- Request-Response Pattern / Record-Dto
- Restful API
- H2 In memory database  
- Docker
- JUnit 5

### Prerequisites

---
- Maven
- Docker
