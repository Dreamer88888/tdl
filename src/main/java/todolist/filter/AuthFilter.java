package todolist.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import todolist.config.ResponseProperties;
import todolist.dto.ResponseDto;
import todolist.security.UserDetailsServiceImpl;
import todolist.utils.JwtUtil;
import todolist.utils.ResponseDtoUtil;

import java.io.IOException;
import java.util.stream.Stream;

import static todolist.utils.ResponseDtoUtil.generatePayload;

public class AuthFilter extends OncePerRequestFilter {

    @Value("${jwt.header}")
    private String jwtHeader;

    @Value("${jwt.prefix}")
    private String jwtPrefix;

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final ResponseProperties responseProperties;
    private final ObjectMapper objectMapper;
    private final PathMatcher pathMatcher;

    private static final String[] allowedURI = {
            "/api/auth/login",
            "/api/user",
            "/actuator/**"
//            "/api/project"
    };

    @Autowired
    public AuthFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService, ResponseProperties responseProperties, ObjectMapper objectMapper, PathMatcher pathMatcher) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.responseProperties = responseProperties;
        this.objectMapper = objectMapper;
        this.pathMatcher = pathMatcher;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return Stream.of(allowedURI).anyMatch(x -> pathMatcher.match(x, request.getRequestURI()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, PATCH, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "*");

        if (request.getMethod().equals(HttpMethod.OPTIONS.name())) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String jwt = parseJwt(request);
            String errorType;

            if (jwt == null) {
                handleNullJwt(response);
                return;
            }

            errorType = jwtUtil.isJwtValid(jwt);

            if (errorType != null) {
                handleInvalidJWT(response, errorType);
                return;
            }

            String name = jwtUtil.getNameFromJwt(jwt);

            UserDetails userDetails = userDetailsService.loadUserByUsername(name);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            name,
                            userDetails.getAuthorities()
                    );

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (MalformedJwtException exception) {
            handleInvalidJWT(response, "Invalid");
            return;
        } catch (ExpiredJwtException exception) {
            handleInvalidJWT(response, "Expired");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String authHeader = request.getHeader(jwtHeader);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith(jwtPrefix)) {
            return authHeader.substring(7);
        }

        return null;
    }

    private void handleNullJwt(HttpServletResponse response) throws IOException {
        ResponseDto<Object> responseDto = ResponseDtoUtil.generateResponse(responseProperties.getAccessDenied().getCode().getInvalid(),
                responseProperties.getAccessDenied().getMessage().getInvalid(), generatePayload("JWT is null"));

        response.setStatus(403);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(responseDto));
    }

    private void handleInvalidJWT(HttpServletResponse response, String errorType) throws IOException {
        ResponseDto<Object> responseDto;

        if (errorType.equals("Expired")) {
            responseDto = ResponseDtoUtil.generateResponse(responseProperties.getAccessDenied().getCode().getInvalid(),
                    responseProperties.getAccessDenied().getMessage().getInvalid(), generatePayload("JWT has expired"));
        } else {
            responseDto = ResponseDtoUtil.generateResponse(responseProperties.getAccessDenied().getCode().getInvalid(),
                    responseProperties.getAccessDenied().getMessage().getInvalid(), generatePayload("Invalid JWT"));
        }

        response.setStatus(403);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(responseDto));
    }

}
