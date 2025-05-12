
# E-Commerce Backend Microservices Project(Shopping mart)

This is a backend system for an E-Commerce application built using **Spring Boot Microservices architecture**. The project is modular and consists of the following services:

# Microservices

# 1. *User Service*
- Handles user registration and authentication.
- JWT-based token generation and validation.
- Secure endpoints with role-based access.
- Tech: Spring Boot, Spring Security, JWT

# 2. *Product Service*
- Manages product-related operations (CRUD).
- Exposes REST APIs for product management.
- Communicates via API Gateway.

# 3. *Order Service*
- Handles order placement and retrieval.
- Checks product availability and user authentication.
- Supports inter-service communication.

# API Gateway
- Central entry point for all client requests.
- Routes traffic to appropriate microservices.
- User global filteration by implementing `GlobalFiltering`.
- Configured with:
  - Load Balancing (via Eureka Client)
  - Strip Prefixing
  - CORS support

# Eureka Server (Service Discovery)
- All microservices register with Eureka for service discovery.
- Enables dynamic routing and load balancing.

# Security
- JWT Authentication implemented in **User Service**.
- Secured API endpoints.
- Stateless token-based session handling.
- Used `OncePerRequestFilter` for validating tokens in downstream services.

# Technologies Used
- Java 21
- Spring Boot
- Spring Cloud (Eureka, Gateway)
- Spring Data JPA
- Spring Security
- JWT (jjwt)
- Maven
- Postman (for checking API)
- MySQL (as the database)

# Project Structure
E-Commerce-Backend
1.API-GATEWAY
2.EUREKA SERVER
3.ORDER SERVICE
4.PRODUCT SERVICE
5.USER SERVICE

# Running the Project

1. **Start Eureka Server** (`eureka-server`)
2. **Run API Gateway** (`api-gateway`)
3. **Start microservices**:
   - `user-service`
   - `product-service`
   - `order-service`
4. Access via: `http://localhost:8080` (Gateway entry)
   
# JWT Flow
1. User logs in via `/api/auth/login`
2. Receives a JWT token
3. Send the token as a `Bearer` token in the `Authorization` header for protected endpoints

# Future Enhancements
- Add payment service
- Implement rate limiting
- Use Kafka for event-driven communication
- Dockerize all services

