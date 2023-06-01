package com.hanex.auth.controller.user;

import com.hanex.auth.common.security.JwtTokenProvider;
import com.hanex.auth.controller.user.dto.UserDto;
import com.hanex.auth.domain.user.User;
import com.hanex.auth.service.LoginService;
import com.hanex.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
public class LoginController {


    private final LoginService loginService;

    @Operation(description = "토큰 생성 테스트")
    @PostMapping("/jwtlogin")
    @Transactional
    public ResponseEntity<Map<String, String>> jwtLogin (@Valid  @RequestBody UserDto.LoginRequest loginRequest, HttpServletResponse response) {
        return ResponseEntity.ok(loginService.jwtLogin(loginRequest,response));
    }
}

