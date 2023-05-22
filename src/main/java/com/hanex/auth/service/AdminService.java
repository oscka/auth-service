package com.hanex.auth.service;

import com.hanex.auth.domain.user.User;
import com.hanex.auth.domain.user.UserRepository;
import com.hanex.auth.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final UserRepository userRepository;

    public void findById(UUID id){
        User account = userRepository.findById(id).orElseThrow(()->new NotFoundException("존재하지 않는 계정입니다."));
    }
}
