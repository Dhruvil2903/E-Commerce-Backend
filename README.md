# E-Commerce-Backend
# 🛒 E-Commerce Backend Microservices Project

This is a backend system for an E-Commerce application built using **Spring Boot Microservices architecture**. The project is modular and consists of the following services:

---

## 📦 Microservices

### 1. **User Service**
- Handles user registration and authentication.
- JWT-based token generation and validation.
- Secure endpoints with role-based access.
- Tech: Spring Boot, Spring Security, JWT

### 2. **Product Service**
- Manages product-related operations (CRUD).
- Exposes REST APIs for product management.
- Communicates via API Gateway.

### 3. **Order Service**
- Handles order placement and retrieval.
- Checks product availability and user authentication.
- Supports inter-service communication.

---

## 🌐 API Gateway
- Central entry point for all client requests.
- Routes traffic to appropriate microservices.
- Configured with:
  - Load Balancing (via Eureka Client)
  - Strip Prefixing
  - CORS support

---

## 🔍 Eureka Server (Service Discovery)
- All microservices register with Eureka for service discovery.
- Enables dynamic routing and load balancing.

---

## 🔐 Security
- JWT Authentication implemented in **User Service**.
- Secured API endpoints.
- Stateless token-based session handling.
- Used `OncePerRequestFilter` for validating tokens in downstream services.

---

## 🧩 Technologies Used
- Java 17
- Spring Boot
- Spring Cloud (Eureka, Gateway)
- Spring Security
- JWT (jjwt)
- Maven
- MySQL (as the database)

---

## 📁 Project Structure
