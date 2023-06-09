package com.hanex.auth.common.api;

import com.hanex.auth.common.enums.EnumType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiResponseCode implements EnumType {

    OK("요청이 성공하였습니다."),
    BAD_PARAMETER("요청 파라미터가 잘못되었습니다."),
    NOT_FOUND("리소스를 찾지 못했습니다."),
    UNAUTHORIZED("인증에 실패하였습니다."),
    FORBIDDEN("사용권한이 없는 리소스입니다."),
    SERVER_ERROR("서버 에러입니다.");

    private final String message;

    public String getId() {
        return name();
    }

    @Override
    public String getText() {
        return message;
    }
}

