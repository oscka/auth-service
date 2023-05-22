package com.hanex.authservice.domain;

import com.hanex.authservice.common.util.encrypt.EncryptString;
import com.hanex.authservice.domain.user.common.UserRole;
import com.hanex.authservice.domain.user.common.UserState;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class BaseUser {

    private String loginId;

    private String name;

    private UserState state;

    private UserRole role;

    private EncryptString email;

    private String password;

    private String phone;
}
