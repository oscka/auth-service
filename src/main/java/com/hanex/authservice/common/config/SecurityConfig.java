//package com.hanex.starter.common.config;
//
//import com.hanex.starter.common.security.JwtTokenFilter;
//import com.hanex.starter.common.security.JwtTokenProvider;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@RequiredArgsConstructor
//@EnableWebSecurity
//@Configuration
//public class SecurityConfig {
//
//    private final CorsConfig corsConfig;
//
//    private final JwtTokenProvider jwtTokenProvider;
//
//    @Bean
//    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .formLogin().disable()
//                .httpBasic().disable()
//                .apply(new MyCustomDsl()) // 커스텀 필터 등록
//                .and()
//                .authorizeRequests()
//                .antMatchers("/**").permitAll()
//                .and().build();
//    }
//
//    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
//        @Override
//        public void configure(HttpSecurity http) throws Exception {
//            http
//                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
//                .addFilter(corsConfig.corsFilter());
//        }
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(12);
//    }
//
//}
