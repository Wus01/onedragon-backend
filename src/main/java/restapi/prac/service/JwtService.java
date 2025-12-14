package restapi.prac.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKeyString;

    @Value("${jwt.expiration-time}")
    private long expirationTime; // 밀리초 (ms)

    private SecretKey getSigningKey() {
        // 설정 파일의 문자열 키를 HMAC-SHA 알고리즘에 사용할 SecretKey 객체로 변환
        return Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 사용자 ID를 기반으로 JWT 토큰을 생성
     * @param userId 토큰에 담을 사용자 고유 ID
     * @return 생성된 JWT 문자열
     */
    public String generateToken(String userId) {
        // 1. Claims (Payload) 설정: 토큰에 담을 정보
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", "USER"); // 권한 정보 등 필요시 추가

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        // 2. 토큰 생성 및 서명
        return Jwts.builder()
                .setClaims(claims)           // 데이터 (Payload)
                .setSubject(userId)          // 토큰 제목 (일반적으로 사용자 ID)
                .setIssuedAt(now)            // 토큰 발행 시간
                .setExpiration(expiryDate)   // 토큰 만료 시간
                .signWith(getSigningKey(), Jwts.SIG.HS256) // HMAC SHA-256 알고리즘과 비밀 키로 서명
                .compact(); // 토큰 생성 완료
    }
}