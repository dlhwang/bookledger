# 📌 API Server (Spring Boot)

## 🔹 프로젝트 개요
Spring Boot 기반의 REST API 서버로, Swagger 문서를 포함합니다.  
H2 데이터베이스를 사용하여 개발 환경을 구성하였습니다.

---

## 🚀 주요 기능
- RESTful API 개발
- H2 데이터베이스 사용 (개발 환경)
- Spring Data JPA 기반 ORM
- Swagger 문서 제공 (`/swagger-ui/index.html`)
- CORS 설정 (프론트엔드와 연동 가능)

---

## 🛠 기술 스택
| 기술                              | 설명 |
|---------------------------------|----------------|
| **Java 18**                     | 개발 언어 |
| **Spring Boot 3.x**             | 애플리케이션 프레임워크 |
| **Spring Security**             |  보안 설정 |
| **JPA (Hibernate)**             | ORM 기반 데이터베이스 관리 |
| **H2 Database**                 | 인메모리 데이터베이스 |
| **Swagger (springdoc-openapi)** | API 문서 제공 |
| **Lombok**                      | 코드 간결화 |


---

## Database H2
- http://localhost:8080/h2-console

---
## 🛠 API 문서 (Swagger)
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- API 문서 (JSON): http://localhost:8080/v3/api-docs




## 비즈니스 조건

- 도서는 하나 이상의 카테고리에 속할 수 있다.
- 도서는 지은이, 제목의 정보를 가지고 있다.
- 신규 도서는 항상 카테고리가 필요하다.
- 도서는 훼손 또는 분실 등의 이유로 대여가 중단 될 수 있다.
- 도서는 카테고리가 변경될 수 있다.
- 카테고리 별로 도서를 검색 할 수 있다.
- 지은이와 제목으로 도서를 검색 할 수 있다.
- 현재 서점에 있는 도서 목록은 다음과 같다.

#### 개발 조건

- Spring Boot V2.2이상 버전
- Java 8+ 이상
- JPA
- API 문서
- 유닛 테스트

![도서 관리 장부 도메인 모델 입니다](https://github.com/user-attachments/assets/c90bfe3c-50ea-4a0e-be3b-02f8fea0ccf3)

---

## 유닛테스트
/src/test/java/com/test/bookledger/application/service/BookLedgerServiceTest.java 
