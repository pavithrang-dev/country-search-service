# County Search Service

A production-ready Spring Boot REST API to suggest US counties based on a free-text query.
The service follows a contract-first approach using OpenAPI and demonstrates clean architecture,
validation, testing, and production-level design practices.

---

## 📌 Problem Overview

The goal of this application is to build a REST API that:
- Accepts a query parameter `q`
- Returns up to **5 matching US counties**
- Supports multiple query patterns:
  - State-only search (e.g. `wa`)
  - Partial county name search (e.g. `cowl`)
  - County name + state search (e.g. `cowlitz, wa`)
- Uses an **in-memory database**
- Follows **production-ready coding practices**
- Includes **validation, global error handling, and tests**

---

## 🧰 Tech Stack

| Technology | Version |
|----------|--------|
| Java | 21 |
| Spring Boot | 3.5.10 |
| Build Tool | Maven |
| Database | H2 (In-memory) |
| ORM | Spring Data JPA |
| API Documentation | OpenAPI / Swagger |
| Testing | JUnit 5, Mockito, MockMvc |
| Utilities | Lombok |

---

## 🚀 Project Initialization

### Spring Initializr Configuration

- Project: **Maven**
- Language: **Java**
- Spring Boot: **3.5.10**
- Java: **21**
- Dependencies:
  - Spring Web
  - Spring Data JPA
  - H2 Database
  - Validation
  - Lombok
  - Springdoc OpenAPI
  - Spring Boot Test

After generation, the application was started once to verify successful bootstrapping.



## 🔄 Development Approach (Step-by-Step)

### 1️⃣ Initial Git Setup

## A Git repository was initialized immediately after project creation.


 - git init
 - git add .
 - git commit -m "chore: initial Spring Boot project setup"


### 2 API Contract (OpenAPI)

The API contract is defined using OpenAPI and provided via spec.yaml.
Location:
 - src/main/resources/openapi/spec.yaml

Purpose:

 - Defines endpoint behavior before implementation
 - Acts as a contract between client and backend
 - Ensures consistency and correctness

Swagger UI:

http://localhost:8080/swagger-ui/index.html

### 3️ Data Loading Strategy

The application uses a static data.json file containing US county data
Data is loaded at application startup using ApplicationRunner
Data is persisted into an H2 in-memory database

 - Benefits:
 - Fast query performance
 - No repeated file reads
 - Production-like behavior
 - Clean separation of responsibilities

### 4️ Domain Modeling
 - Entity (CountyEntity)
 Represents database structure
 -  DTO (CountyDto)
Represents API response
 -  Mapper
 Converts entity to DTO
 - This avoids exposing database entities directly through the API.

### 5️ Repository Layer
 Responsibilities:
 - Database-level filtering
 - Case-insensitive searches
 - Deterministic ordering
 - Pagination and result limiting

 Key methods:

 - findByStateIgnoreCaseOrderByNameAsc
 - searchByNameAndState

All filtering is performed in the database for performance and clarity.

### 6️ Service Layer (Business Logic)

The service layer determines how the query parameter q should be interpreted.

Search Rules
Query	Behavior
wa	State-only search
cowl	Partial county name search
cowlitz, wa	County name + state search

Special logic:

If q.length == 2 and no comma → treated as state code
Otherwise:
First part → county name
Second part (optional) → state

This logic is intentionally kept out of the controller.

### 7️ Controller Layer

Exposes /suggest endpoint
Validates request parameters
Delegates logic to service layer
Matches the OpenAPI contract exactly

## 🔍 Search API
Endpoint
GET /suggest?q={query}

Examples
State-only:
/suggest?q=wa

Name-only:
/suggest?q=cowl

Name + State:
/suggest?q=cowlitz, wa

## ✅ Validation & Error Handling
Validation
Query parameter q is mandatory
q must not be blank
Implemented using:
Bean Validation (@NotBlank)
Controller-level validation
Global Exception Handling
A centralized @ControllerAdvice is used to:

## Handle validation errors

Handle missing request parameters
Return consistent, user-friendly error responses
Prevent stack traces from leaking to clients
Example error response:

{
  "status": 400,
  "error": "Bad Request",
  "message": "Query parameter 'q' must not be blank"
}

### 🧪 Testing Strategy
Service Layer Tests
Implemented using JUnit 5 and Mockito
Focus on business rules and query parsing logic
No Spring context loading
Controller Layer Tests
Implemented using @WebMvcTest
Uses MockMvc
Verifies:
HTTP status codes
JSON response structure
Validation behavior

Run all tests:

mvn test

### ⚙️ Configuration

application.yml
H2 in-memory database configuration
JPA configuration
Swagger enabled
H2 Console:

http://localhost:8080/h2-console


### Running the Application
mvn clean install
mvn spring-boot:run


Then access:
Swagger UI: /swagger-ui/index.html
H2 Console: /h2-console