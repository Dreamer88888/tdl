package todolist.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import todolist.security.UserDetailsImpl;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expirationInMs}")
    private int expirationInMs;

    private static final String INVALID_MESSAGE = "Invalid";
    private static final String EXPIRED_MESSAGE = "Expired";

    public String generateJwt(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("authorities", userPrincipal.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expirationInMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String getNameFromJwt(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public String isJwtValid(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(authToken)
                    .getBody();
            return null;
        } catch (MalformedJwtException exception) {
            log.error("JWT is invalid: {}", exception.toString());
            return INVALID_MESSAGE;
        } catch (ExpiredJwtException exception) {
            log.error("JWT is expired: {}", exception.toString());
            return EXPIRED_MESSAGE;
        } catch (UnsupportedJwtException exception) {
            log.error("JWT is unsupported: {}", exception.getMessage());
            return INVALID_MESSAGE;
        } catch (IllegalArgumentException exception) {
            log.error("JWT claims string is empty: {}", exception.getMessage());
            return INVALID_MESSAGE;
        }
    }

}
