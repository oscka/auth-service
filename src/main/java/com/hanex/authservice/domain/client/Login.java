package com.hanex.authservice.domain.client;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Login {

    private String loginId;

    private String password;

    private String authId;
}

