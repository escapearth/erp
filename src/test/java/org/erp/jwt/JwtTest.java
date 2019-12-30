package org.erp.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@SpringBootTest
@Slf4j
public class JwtTest {

    private static String JWT_TOKEN = null;

    private static final String TOKEN_KEY = "ZmQ0ZGI5NjQ0MDQwY2I4MjMxY2Y3ZmI3MjdhN2ZmMjNhODViOTg1ZGE0NTBjMGM4NDA5NzYxMjdjOWMwYWRmZTBlZjlhNGY3ZTg4Y2U3YTE1ODVkZDU5Y2Y3OGYwZWE1NzUzNWQ2YjFjZDc0NGMxZWU2MmQ3MjY1NzJmNTE0MzI=";

    private static final LocalDateTime EXPIRES_LOCAL = LocalDateTime.now();

    private static final Date EXPIRES_DATE = Date.from(EXPIRES_LOCAL.toInstant(ZoneOffset.ofHours(9)));

    private static final Long DAY = 3600L * 24;

    private Key key;

    @BeforeAll
    static void beforeClass_createJwtToken() throws Exception {
        try {
            JWT_TOKEN = Jwts.builder()
                    .setIssuedAt(EXPIRES_DATE)
                    .setIssuer("halfdev")
                    .setExpiration(EXPIRES_DATE)
                    .signWith(SignatureAlgorithm.HS256,(TOKEN_KEY))
                    .compact();

        } catch (IllegalArgumentException createEx) {
            log.info("Create Error");
            createEx.printStackTrace();
        }
    }

    @Test
    void test_decodeJwtToken() {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(TOKEN_KEY);
            this.key = Keys.hmacShaKeyFor(keyBytes);
        } catch (MalformedJwtException decodeEx) {
            log.info("Decode Error");
            decodeEx.printStackTrace();
        }
    }

}
