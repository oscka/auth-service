-- user_tb
-- 컬럼이 예약어인경우 "" 쌍따옴표 처리를 해줘야한다.
INSERT INTO user_tb (id,login_id,"name",state,"role",email,"password",phone,created_at,updated_at) VALUES
	 ('063c6f67-662e-44e1-aace-70cd618dd008','test','홍길동','ACTIVE','ROLE_CLIENT','test@demo.com','$2a$10$rwS/IZMHjoM0gokMjslGYuezKpVlZweT.xW9qzyqh3E7WOPqKLLUS','01012345678','2023-05-23 13:18:25.35562','2023-05-23 13:18:25.403039');

