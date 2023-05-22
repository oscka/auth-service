package com.hanex.authservice.common.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        log.info("JWT Filter 작동");
        String token = resolveTokenFromCookie(request.getCookies());
        if (token != null && jwtTokenProvider.validateToken(token)) {
            log.info("토큰 검증 로직 시작");
            // 토큰이 유효한 경우에는 해당 요청을 처리하도록 설정
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(request));
        }
        filterChain.doFilter(request, response);
    }

    private String resolveTokenFromCookie(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accessToken")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }


    protected Authentication getAuthentication(HttpServletRequest request) {
        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accessToken")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰이 유효한 경우, 토큰에서 사용자 정보를 추출하여 Authentication 객체 생성
            String userName = jwtTokenProvider.extractUserName(token);
            String role = jwtTokenProvider.extractRole(token);
            List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(role);
            UserDetails userDetails = new User(userName, "", authorities);
            return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
        }
        return null;
    }
}

