package com.dev.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;
@Component
public class JwtTokenUtil implements Serializable {

    private final Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final long serialVersionUID = -3301605591108950415L;

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_AUDIENCE = "audience";
    static final String CLAIM_KEY_CREATED = "created";
    static final String CLAIM_KEY_ID = "id";
    static final String CLAIM_KEY_ACCESS_NUMBER = "access_number";
    static final String CLAIM_KEY_COMPANY_ID = "company_id";

    private static final String AUDIENCE_UNKNOWN = "unknown";
    private static final String AUDIENCE_WEB = "web";
    private static final String AUDIENCE_MOBILE = "mobile";
    private static final String AUDIENCE_TABLET = "tablet";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Autowired
    UserDetailsService userDetailsService;
    public String access_number;
    public String userId;

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = (String) claims.get(CLAIM_KEY_USERNAME);
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Object getIdFromToken(String token) throws Exception {
        log.debug("Request to get employee id from token: {}", token);
        Object id = null;
        try {
            final Claims claims = getClaimsFromToken(token);
            id = claims.get(CLAIM_KEY_ID);
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return id;
    }

    public Object getAccessFromToken(String token) {
        Object access;
        try {
            final Claims claims = getClaimsFromToken(token);
            access = claims.get(CLAIM_KEY_ACCESS_NUMBER);
        } catch (Exception e) {
            access = null;
        }
        return access;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = getClaimsFromToken(token);
            audience = (String) claims.get(CLAIM_KEY_AUDIENCE);
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    private Claims getClaimsFromToken(String token) throws Exception {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return claims;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + 10800000);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Boolean ignoreTokenExpiration(String token) {
        String audience = getAudienceFromToken(token);
        return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        try {
            claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
            claims.put(CLAIM_KEY_AUDIENCE, "web");
            claims.put(CLAIM_KEY_CREATED, new Date());
            claims.put(CLAIM_KEY_ID, userDetails.getUsername());
            claims.put(CLAIM_KEY_ACCESS_NUMBER, access_number);

        } catch (Exception e) {
            log.debug(e.getMessage());
            e.printStackTrace();
        }
        return generateToken(claims);
    }

    public String getAccessnumber() {
        return access_number;
    }

    public String getUserId() {
        return userId;
    }

    String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512 , secret)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getCreatedDateFromToken(token);

        return (username.equals(user.getUsername())
                && !isTokenExpired(token)
        );
    }
}
