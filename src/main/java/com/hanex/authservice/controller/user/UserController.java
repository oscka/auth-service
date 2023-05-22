package com.hanex.authservice.controller.user;

import com.hanex.authservice.common.security.JwtTokenProvider;
import com.hanex.authservice.domain.user.User;
import com.hanex.authservice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/security")
@AllArgsConstructor
public class UserController {

    private JwtTokenProvider jwtTokenProvider;

    private UserService userService;

    // 토큰 생성 테스트 localhost:portnumber/security?username=무작위값 입력하면 토큰 받환환
    @GetMapping("/createtoken")
    public Map<String, Object> createToken(@RequestParam(value = "loginId") String loginId) {
        String token = jwtTokenProvider.createAccessToken(loginId);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("result", token);
        return map;
    }

    @PostMapping("/jwtlogin")
    @Transactional
    public ResponseEntity<Map<String, String>> jwtLogin(@RequestBody User user, HttpServletResponse response) {
        String loginId = user.getLoginId();
        String password = user.getPassword();
        User foundUser = userService.findByLoginId2(loginId);
        log.info("JWT 로그인 기능 작동");

        // 사용자 정보 확인
        if (foundUser != null && foundUser.getPassword().equals(password)) {
            log.info("로그인 성공 + 토큰도 발급 + 쿠키에 담아 발급.");

            // 엑세스 토큰 생성
            String jwtAccessToken = jwtTokenProvider.createAccessToken(foundUser.getLoginId());

            // Refresh 토큰 생성
            String jwtRefreshToken = jwtTokenProvider.createRefreshToken(foundUser.getLoginId());
            log.info("JWT 엑세스 토큰: " + jwtAccessToken); // JWT 토큰 출력
            log.info("JWT 리프래쉬 토큰: " + jwtRefreshToken); // JWT 토큰 출력

            // 쿠키에 토큰 정보를 설정
            Cookie accessTokenCookie = new Cookie("accessToken", jwtAccessToken);
            accessTokenCookie.setHttpOnly(true);
            accessTokenCookie.setMaxAge(24 * 60 * 60); // 토큰의 유효 기간 설정 (예: 24시간)
            response.addCookie(accessTokenCookie);

            Cookie refreshTokenCookie = new Cookie("refreshToken", jwtRefreshToken);
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 토큰의 유효 기간 설정 (예: 7일)
            response.addCookie(refreshTokenCookie);

            // JSON 응답 생성
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("accessToken", jwtAccessToken);
            responseMap.put("refreshToken", jwtRefreshToken);
            return ResponseEntity.ok(responseMap);
        } else {
            log.info("로그인 실패");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}

