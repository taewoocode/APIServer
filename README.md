# API Server

이 프로젝트는 사용자를 관리하는 RESTful API 서버를 구현한 예제입니다. Spring Boot를 사용하여 간단한 CRUD (생성, 조회, 수정, 삭제) 작업을 수행할 수 있는 API를 제공합니다.

## 프로젝트 구조

- **`com.example.apiserver`**: 주요 패키지
    - **`controller`**: REST API 엔드포인트를 처리하는 컨트롤러
    - **`entity`**: 데이터베이스 엔티티 클래스
    - **`service`**: 비즈니스 로직을 처리하는 서비스 클래스
    - **`exception`**: 사용자 정의 예외 및 예외 처리

## 기능

- 사용자 목록 조회
- 사용자 ID로 조회
- 사용자 생성
- 사용자 수정
- 사용자 삭제

## 기술 스택

- **Spring Boot**: 프레임워크
- **Spring Web**: 웹 애플리케이션 개발
- **Lombok**: 자바 코드 간소화
- **Jakarta Validation**: 입력 검증
- **MySQL**: 데이터베이스
- **JUnit 5**: 테스트 프레임워크
- **Mockito**: 모킹 프레임워크



## API 문서

### Method: GET, POST, PUT, DELETE

1. 모든 사용자 조회
   Request: GET /users
   Response: 사용자 목록 (JSON 배열)

2. 사용자 조회
   Request: GET /users/{id}
   Parameters: id (Long) - 조회할 사용자 ID
   Response: 사용자 정보 (JSON 객체) 또는 404 Not Found

3. 사용자 생성
   Request: POST /users
   Body: 사용자 정보 (JSON 객체)
   Response: 생성된 사용자 정보 (JSON 객체)

4. 사용자 수정
   Request: PUT /users/{id}
   Parameters: id (Long) - 수정할 사용자 ID
   Body: 수정할 사용자 정보 (JSON 객체)
   Response: 수정된 사용자 정보 (JSON 객체)

5. 사용자 삭제
   Request: DELETE /users/{id}
   Parameters: id (Long) - 삭제할 사용자 ID
   Response: 204 No Content 또는 404 Not Found