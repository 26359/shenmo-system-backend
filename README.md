# Shenmo System

**Author:** MUGANWA Christian  
**Student ID:** 26359  
**Project:** Shenmo System (Spring Boot backend)

## Description

Spring Boot backend for a university exam. CRUD and business logic for skills, counters, agents, customers, service requests and service notes, using PostgreSQL and Spring Data JPA. Entities are used directly as request/response bodies (no DTOs).

## Tech stack

- Java 17, Maven, Spring Boot
- Spring Web, Spring Data JPA, PostgreSQL driver
- Database: PostgreSQL, database `shenmo_system`

## application.properties

Edit `src/main/resources/application.properties` before running:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/shenmo_system
spring.datasource.username=postgres
spring.datasource.password=PLACEHOLDER   <-- replace with your PostgreSQL password
```

Hibernate DDL is set to `update` and `data.sql` seeds the database on startup.

## Steps to run the API

1. Install PostgreSQL and create the database:

   ```sql
   CREATE DATABASE shenmo_system;
   ```

2. Edit `src/main/resources/application.properties` and set `spring.datasource.password` to your PostgreSQL password.

3. Build the project:

   ```bash
   ./mvnw clean package -DskipTests
   ```

   (On Windows use `mvnw.cmd` instead of `./mvnw`.)

4. Run the API:

   ```bash
   ./mvnw spring-boot:run
   ```

   The API starts on `http://localhost:8080`.

5. Seed data is loaded automatically from `src/main/resources/data.sql` on startup
   (`spring.sql.init.mode=always`).

6. Import `shenmo-system-midterm.postman_collection.json` into Postman to test all endpoints.

## Endpoints

- A2 CRUD: `/api/{skills|counters|agents|customers|service-requests}/save | /all | /{id} | /update/{id} | /delete/{id}`
- B1: `GET /api/customers/by-last-name?lastName=uwimana`
- B2: `GET /api/service-requests/by-status?status=OPENED`
- B3: `GET /api/service-requests/between?start=2026-10-01&end=2026-10-31`
- C1: `GET /api/agents/by-skill?name=billing`
- C2: `GET /api/counters/free`
- C3: `GET /api/skills/unused`
- C4: `GET /api/customers/of-agent/{agentId}`
- D1: `POST /api/service-requests/save` (past date / agent conflict rules)
- D2: `PATCH /api/service-requests/{id}/status?status=IN_PROGRESS`
- D3: `PUT /api/agents/{agentId}/skills/{skillId}`
- D4: `PATCH /api/agents/{agentId}/counter?counterNumber=4`
- E1: `POST /api/service-notes/save?serviceRequestId=...`
- E2: `GET /api/service-notes/customer/{customerCode}`
- E3: `DELETE /api/service-notes/delete/{id}`
- F1: `GET /api/service-requests/stats/by-status`
- F2: `PATCH /api/service-requests/cancel-day?agentId=...&date=2026-10-20`
- Bonus: `GET /api/dashboard/summary`, `GET /api/service-requests/page?page=0&size=5&sort=requestDate,desc`
