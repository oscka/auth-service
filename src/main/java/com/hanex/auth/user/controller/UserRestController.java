package com.hanex.auth.user.controller;

import com.hanex.auth.user.dto.UserDto;
import com.hanex.auth.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("/v1/users")
@RequiredArgsConstructor
@RestController
public class UserRestController {

	private final UserService userService;

	@Operation(description = "회원가입")
	@PostMapping
	private ResponseEntity<Void> register(@Valid @RequestBody UserDto.SaveRequest save){
		userService.register(save);
		return ResponseEntity.ok().build();
	}

	@Operation(description = "로그인아이디로 회원 정보 조회")
	@GetMapping("/{loginId}")
	private UserDto.UserInfoResponse findByLoginId(@PathVariable String loginId){
		return userService.findByLoginId(loginId);
	}

	@Operation(description = "회원 정보 수정")
	@PutMapping("/{id}")
	private ResponseEntity<Void> update(@PathVariable UUID id,@Valid @RequestBody UserDto.UpdateRequest update){
		userService.update(id,update);
		return ResponseEntity.ok().build();
	}

	@Operation(description = "회원 탈퇴")
	@DeleteMapping("/{id}")
	private ResponseEntity<Void> delete(@PathVariable UUID id){
		userService.delete(id);
		return ResponseEntity.ok().build();
	}



}
