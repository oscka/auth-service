package com.hanex.auth.service;

import com.hanex.auth.controller.user.dto.UserDto;
import com.hanex.auth.domain.user.User;
import com.hanex.auth.domain.user.UserRepository;
import com.hanex.auth.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	/**
	 * 회원가입
	 * @param save
	 */
	public void register(UserDto.SaveRequest save){

//		if (existsByLoginId(save.getLoginId())){
//			throw new RuntimeException("아이디가 이미 존재합니다.");
//		}

		userRepository.insert(save.toEntity());
	}

	/**
	 * 중복 아이디 Check
	 * @param loginId
	 * @return
	 */
	public boolean existsByLoginId(String loginId){
		return userRepository.existsByLoginId(loginId);
	}

	/**
	 * 회원정보 by loginID
	 * @param loginId
	 * @return
	 */
	public UserDto.UserInfoResponse findByLoginId(String loginId){
		User user = userRepository.findByLoginId(loginId).orElseThrow(()->new NotFoundException("존재하지 않는 계정입니다."));
		return user.toDto();
	}

	public User findByLoginId2(String loginId) {
		return userRepository.findByLoginId(loginId).orElseThrow(() -> new NotFoundException("존재하지 않는 계정입니다."));
	}

	/**
	 * 회원정보 수정
	 * @param id
	 * @param update
	 */
	public void update(UUID id, UserDto.UpdateRequest update){
		User user = userRepository.findById(id).orElseThrow(()->new NotFoundException("존재하지 않는 계정입니다."));
		userRepository.changeUserInfo(id,update.toEntity());
	}


	/**
	 * 회원 탈퇴
	 * @param id
	 */
	public void delete(UUID id){
		User account = userRepository.findById(id).orElseThrow(()->new NotFoundException("존재하지 않는 계정입니다."));
		userRepository.delete(account);
	}




}
