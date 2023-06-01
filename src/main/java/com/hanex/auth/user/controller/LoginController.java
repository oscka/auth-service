package com.hanex.auth.user.controller;

import com.hanex.auth.user.dto.UserDto;
import com.hanex.auth.user.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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

