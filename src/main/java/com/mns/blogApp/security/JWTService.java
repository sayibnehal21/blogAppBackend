package com.mns.blogApp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {
    // TODO: Move the key to a separate .properties file (not in git)
    private static final String JWT_SECRET_KEY = "7892bd932d87fc1777a1dd810897534a9adc16fdef0782ec6a09d0446073deb9";
    private Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET_KEY);

    public String createJwt(Long userId) {
        return JWT.create()
                .withSubject(userId.toString())
                .withIssuedAt(new Date())
                // .withExpiresAt() // TODO: setup and expiry parameter
                .sign(algorithm);
    }

    public Long retrieveUserId(String jwtString) {
        var decodedJWT = JWT.decode(jwtString);
        var userId = Long.valueOf(decodedJWT.getSubject());
        return userId;
    }
}
