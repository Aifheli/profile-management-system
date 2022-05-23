package ayo.profile.management.security;

import ayo.profile.management.jwt.JWTService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
/**
 * Created by Aifheli Maganya on 2022/05/19.
 */
public abstract class JWTAuthenticationFilter extends BasicAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
    private boolean bypassEnabled;
    private JWTService jwtService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
        this(authenticationManager, jwtService, false);
    }

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTService jwtService, boolean bypassEnabled) {
        super(authenticationManager);
        this.jwtService = jwtService;
        this.bypassEnabled = bypassEnabled;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException {
        try {
            String token = request.getHeader("Authorization");
            token = token != null ? token.replace("Bearer ", "") : "";
            String username;
            if (this.bypassEnabled) {
                username = "bypass";
            } else {
                Claims claimsJws = this.jwtService.parseJwtToken(token);
                username = claimsJws.getIssuer();
            }

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, (Object)null, new ArrayList()));
            chain.doFilter(request, response);
        } catch (Exception var7) {
            log.error("Failure validation JWT token ", var7);
            response.setContentType("application/json");
            response.setStatus(401);
            response.getOutputStream().println(this.createError("JWT_INVALID", var7.getMessage()));
        }
    }

    public abstract String createError(String var1, String var2) throws JsonProcessingException;
}