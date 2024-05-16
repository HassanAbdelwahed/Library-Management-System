# Library-Management-System
Build a Library Management System API using Spring Boot. The system should allow librarians to manage books, patrons, and borrowing records.

## Installation
This Apllication require spring boot installation.

##### spring boot installation
Follow This link:
[https://spring.io/guides/gs/spring-boot/]

## Interact with Api End points

All Api Required are implemnted

I used Swagger for Api documentation. After Run The Application put this link [http://localhost:8080/swagger-ui/index.html] in the broswer to interact with Apis implemented in this application

# Data Storage:
MySQL used in this project to persist book, patron, and borrowing record details.

# Two Bonus Requirements implemnted

### Aspects
logging using Aspect-Oriented Programming (AOP) implemented to log method calls, exceptions, and performance metrics (execution time) of operations like book additions, updates, and patron transactions.

### Caching :
Caffeine cache implemnted in this project.
Caffeine is a high-performance and in-memory caching library. It provides a fast and efficient implementation of a key-value cache, designed to optimize read and write operations.

# Transaction Management:
Transaction management using Spring's @Transactional annotation added to ensure data integrity during critical operations.

# Testing:
Unit tests are implemnted using Mockito.
