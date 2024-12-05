package com.jwt_auth.services;

import com.jwt_auth.models.tables.Users;
import com.jwt_auth.repositories.UserTokensRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Data
public class JWTService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    @Autowired
    UserTokensRepository userTokensRepository;

    @Autowired
    JwtEncoder encoder;

    @Autowired
    JwtDecoder decoder;

    public static final String CLAIM_SCOPE = "scope";
    public static final String EMAIL = "email";
    public static final int SECONDS_IN_AN_HOUR = 60 * 60;

    public Map<String, Object> extractAllClaims(String token) {
        return decoder.decode(token).getClaims();
    }

    public String generateToken(Users user) {

        String scope = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(""));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(SECONDS_IN_AN_HOUR))
                .subject(user.getUsername())
                .claim(CLAIM_SCOPE, scope)
                .claim(EMAIL, user.getEmail())
                .build();

        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public long getExpirationTime() {
        return SECONDS_IN_AN_HOUR;
    }
}
