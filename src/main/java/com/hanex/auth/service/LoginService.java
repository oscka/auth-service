package com.hanex.auth.service;

import com.hanex.auth.common.security.JwtTokenProvider;
import com.hanex.auth.controller.user.dto.UserDto;
import com.hanex.auth.domain.user.User;
import com.hanex.auth.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginService {

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;


    /**
     * login > jwt access token 발급
     */
    public Map<String, String> jwtLogin (UserDto.LoginRequest loginRequest, HttpServletResponse response){


        String loginId = loginRequest.getLoginId();
        String password = loginRequest.getPassword();

        User foundUser = userRepository.findByLoginId(loginId).orElseThrow(()-> new RuntimeException("존재하지 않는 사용자 입니다."));

        // 입력한 비밀번호와 DB 의 비밀번호 match
        if(!passwordEncoder.matches(password,foundUser.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 엑세스 토큰 생성
        String jwtAccessToken = jwtTokenProvider.createAccessToken(foundUser);

        // Refresh 토큰 생성
        String jwtRefreshToken = jwtTokenProvider.createRefreshToken(foundUser);

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
        return responseMap;
    }
}
