# auth-service
한 Express 템플릿 - auth-service

## Project 기술 스택
- Spring Boot 2.7.12 , jdk 17, gradle 7.6.1
- Spring Security (with JWT token)
- Spring Data JDBC + Spring JDBC
- Postgresql (prod,dev) , H2 (local,test)
- log4j2 , MDC
- i18n (message source)
- Spring Data Redis

### auth-service 에서 발급한 토큰 example
```
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2ODU2MTA1OTIsImV4cCI6MTY4NTY5Njk5MiwiaXNzIjoiaGFuZXgiLCJhdWQiOiJ1c2VyIiwic3ViIjoiZTI1MjRmYmUtOTE2Ni00N2ZjLWI2M2UtNzYxYmI0YWI3YzRmIiwibG9naW5JZCI6ImFkbWluIiwicm9sZSI6IlJPTEVfQURNSU4ifQ.IiWVElaKtto6fJEcxsTsHr2p6z7jsohb3Ue_rZlVxz8
```
JWT 토큰은 base64 로 인코딩돼있는데, 해당 토큰을 [jwt.io](https://jwt.io/) 에서 decode 하면 아래 내용을 확인 할 수 있다.


###  HEADER : ALGORITHM & TOKEN TYPE
```json
{
  "typ": "JWT",
  "alg": "HS256"
}
```

### PAYLOAD : DATA
```json
{
  "iat": 1685610592,
  "exp": 1685696992,
  "iss": "hanex",
  "aud": "user",
  "sub": "e2524fbe-9166-47fc-b63e-761bb4ab7c4f",
  "loginId": "admin",
  "role": "ROLE_ADMIN"
}
```
VERIFY SIGNATURE

jwt 토큰은 점(.) 을 기준으로 총 3 part 로 이뤄져있다
HEADER + PAYLOAD + VERIFY SIGNATURE

- Header
  - JWT에서 사용할 타입과 해시 알고리즘 종류
- Payload
  - 서버에서 첨부한 사용자 권한 정보와 실데이터
- Signature
  - Base65 URL-safe Encode를 한 이후, Header에 명시된 해시함수를 적용하고 개인키(Private Key)로 서명한 전자서명