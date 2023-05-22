package com.hanex.authservice.controller.user;

import com.hanex.authservice.controller.user.dto.UserDto;
import com.hanex.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("/v1/account")
@RequiredArgsConstructor
@RestController
public class UserRestController {

	private final UserService userService;

	@PostMapping
	private ResponseEntity<Void> register(UserDto.SaveRequest save){
		userService.register(save);
		return ResponseEntity.ok().build();
	}


	@GetMapping("/{loginId}")
	private UserDto.UserInfoResponse findByLoginId(@PathVariable String loginId){
		return userService.findByLoginId(loginId);
	}

	@PutMapping("/{id}")
	private ResponseEntity<Void> update(@PathVariable UUID id,@Valid @RequestBody UserDto.UpdateRequest update){
		userService.update(id,update);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	private ResponseEntity<Void> delete(@PathVariable UUID id){
		userService.delete(id);
		return ResponseEntity.ok().build();
	}



}
