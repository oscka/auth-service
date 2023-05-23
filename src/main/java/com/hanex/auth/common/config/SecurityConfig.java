package com.hanex.auth.common.config;

import com.hanex.auth.common.security.FilterResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CorsConfig corsConfig;


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 1. CSRF 해제
        http.csrf().disable();

        // 2. jSessionId 사용 거부 (STATELESS 로 설정하면 쿠키에 세션키를 저장하지 않는다.)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 3. form 로그인 해제 (UsernamePasswordAuthenticationFilter 비활성화)
        http.formLogin().disable();

        // 4. 로그인 인증창이 뜨지 않게 비활성화
        http.httpBasic().disable();


        // 5. 인증 실패 처리
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            log.warn("인증되지 않은 사용자가 resource 접근 : {}",authException.getMessage());
            FilterResponseUtil.unAuthorized(response, new RuntimeException("인증되지 않았습니다"));
        });

        // h2-console
        http.headers().addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));
        http.headers().frameOptions().sameOrigin();


        // path setting
        http
                .authorizeRequests().requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .and()
                .authorizeRequests().antMatchers("/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/h2-console/**").permitAll();

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder: Spring Security에서 제공하는 비밀번호 암호화 객체
        // service 에서 비밀번호를 암호화,Match  할수 있도록 bean 으로 등록
        return new BCryptPasswordEncoder(12);
    }

}
