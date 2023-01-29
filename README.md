# [TEAM H] hteam-backend

Team H의 MOZIP 서비스 프론트엔드 레포지토리입니다.

해당 레포지토리는 다음 내용을 포함하고 있습니다.

- MOZIP 웹 백엔드 서비스 코드


## 프로젝트에서 사용한 기술

- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Spring Security
- Spring Boot Mail
- jjwt
- MySQL


## Dev Server 실행 방법

```bash
./gradlew build
java -jar build/libs/mozip-server-0.0.1-SNAPSHOT.jar 
# 이후 localhost:8080 으로 접속
```


## Production 배포 방법

```bash
./gradlew build
nohup java -jar build/libs/mozip-server-0.0.1-SNAPSHOT.jar &
# 이후 localhost:8080 으로 접속
```


## 환경 변수 및 시크릿

`src/main/resources/application.yml` 경로에 아래와 같은 형식의 환경 변수 설정 파일이 존재해야 합니다.

```yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://~
    username: DB 사용자명
    password: DB 비밀번호
  jpa:
    hibernate:
      ddl-auto: update # DB 테이블이 미리 생성되어 있다면 지워도 무방합니다
auth:
  jwt:
    secret-key: JWT 시크릿키

```


## 기타
