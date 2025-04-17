# 📱 Mobile Provider Billing System

This project is a RESTful backend API developed using **Spring Boot** that simulates a mobile service provider’s billing system. It supports tracking subscriber usage (PHONE and INTERNET), calculating monthly bills, and paying them. Authentication is handled via **JWT tokens**, and API documentation is available via **Swagger UI**.

---

## 🚀 Tech Stack
- **Java 17**
- **Spring Boot 3.4.4**
- **Spring Security**
- **PostgreSQL**
- **Swagger (springdoc-openapi)**
- **JWT (jjwt 0.11.5)**

---

## 🔐 Authentication
- Public Endpoint: `/api/v1/auth/login`
- Protected Endpoints: Require JWT via `Authorization: Bearer <token>`

Example login request:
```
POST /api/v1/auth/login?username=admin&password=1234
```

Returns:
```json
{
  "token": "Bearer eyJhbGci..."
}
```

---

## 📡 API Endpoints Overview

### Subscriber
- `POST /api/v1/subscribers` → Create a new subscriber
- `GET /api/v1/subscribers` → List all subscribers
- `GET /api/v1/subscribers/{subscriberNo}` → Get specific subscriber info

### Usage
- `POST /api/v1/usage` → Add usage (requires JWT)

### Billing
- `GET /api/v1/bill/calculate` → Calculate bill for a subscriber (requires JWT)
- `GET /api/v1/bill/history` → Get all bills of a subscriber (requires JWT)
- `GET /api/v1/bill/detailed` → Paginated + sortable bill list (requires JWT)
- `POST /api/v1/bill/pay` → Pay for a bill (requires JWT)

---

## 📘 Swagger
Swagger UI is available at:
```
http://localhost:8080/swagger-ui.html
```
Use the **Authorize** button to enter your JWT.

---

## 🧪 How to Run the Project
1. Clone the repo
2. Make sure PostgreSQL is running and accessible
3. Update `application.properties` with your DB credentials
4. Run:
```
./mvnw spring-boot:run
```
5. Access Swagger at `http://localhost:8080/swagger-ui.html`

---

## 📂 Example Swagger Tests
- Login and receive token ✅
- Create subscriber ✅
- Add usage ✅
- Calculate and get bills ✅
- Paginated detailed bills ✅
- Pay bill ✅

---


## 📄 License
This project is for educational purposes (Midterm - SE4458 Yaşar University).