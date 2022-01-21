package springboot.jewelry.api.security.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jewelry.app.jwt-secret}")
    private String jwtSecret;

    @Value("${jewelry.app.jwt-duration}")
    private Long jwtDuration;

    public String generateJwtToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtDuration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("JWT Token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT Token is unsupported: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("JWT Token is invalid: {}", e.getMessage());
        } catch (SignatureException e) {
            logger.error("JWT Token has incorrect signature: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT Claims is empty: {}", e.getMessage());
        }

        return false;
    }

    public String getTokenFromRequest(HttpServletRequest request){
        String header = request.getHeader("Authorization");

        if(header != null && header.startsWith("Bearer "))
            return header.substring("Bearer ".length());

        return null;
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
}
