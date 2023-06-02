package com.hanex.auth.common.security;

public class SecurityConstant {

    public static final String TOKEN_HEADER = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String TOKEN_ISSUER = "http://auth-service";

    private static final int EXP = 1000 * 60 * 60* 24; // 24시간

}
