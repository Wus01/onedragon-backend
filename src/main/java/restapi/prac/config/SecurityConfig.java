package restapi.prac.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // 비밀번호 인코딩
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // CSRF 설정 (람다식 사용)
            .csrf(csrf -> csrf.disable())

            // HTTP Basic 인증 팝업창 비활성화
            .httpBasic(httpBasic -> httpBasic.disable())

            // 경로별 권한 설정
            .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/userInfo/login", "/api/userInfo/findId", "/api/userInfo/findPw").permitAll()
            .anyRequest().authenticated() // 그 외는 인증 필요
        );

        return http.build();
    }
}
