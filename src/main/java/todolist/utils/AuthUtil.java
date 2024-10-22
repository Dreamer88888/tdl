package todolist.utils;

import io.jsonwebtoken.MalformedJwtException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import todolist.security.UserDetailsImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AuthUtil {

    public static UUID getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            return userDetails.getId();
        } else {
            Optional<String> authHeader = Optional.ofNullable(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization"));

            if (authHeader.isEmpty()) {
                throw new MalformedJwtException("JWT not found");
            }

            throw new AuthenticationCredentialsNotFoundException("JWT expired");
        }
    }

}
