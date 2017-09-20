package ta.microservices.common.service.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

public class TokenUtil {

    private static final Key key = MacProvider.generateKey();

    private JwtBuilder builder;
    private Jws<Claims> claimsJws;

    public TokenUtil(String username, String[] roles, int version, Date expires) {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
        if (username == null) {
            throw new IllegalArgumentException("null username is illegal");
        }
        if (roles == null) {
            throw new IllegalArgumentException("null roles are illegal");
        }
        if (expires == null) {
            throw new IllegalArgumentException("null expires is illegal");
        }
        if (key == null) {
            throw new IllegalArgumentException("null key is illegal");
        }

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        builder = Jwts
                .builder()
                .setIssuer("Jersey-Security-Basic")
                .setSubject(username)
                .setAudience(Arrays.stream(roles).collect(Collectors.joining(", ")))
                .setExpiration(expires)
                .setIssuedAt(new Date())
                .setId(String.valueOf(version))
                .signWith(signatureAlgorithm, key);
    }

    public TokenUtil(String jwsToken) {
        claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(jwsToken);
    }

    public String getTokenString() {
        return builder.compact();
    }

    public boolean isValid() {
        return claimsJws != null;
    }

    public String getName() {
        if (claimsJws != null) {
            return claimsJws.getBody().getSubject();
        }
        return null;
    }

    public String[] getRoles() {
        if (claimsJws != null) {
            return claimsJws.getBody().getAudience().split(",");
        }
        return new String[]{};
    }

    public int getVersion() {
        if (claimsJws != null) {
            return Integer.parseInt(claimsJws.getBody().getId());
        }
        return -1;
    }

    public Date getExpiration() {
        if (claimsJws != null) {
            return claimsJws.getBody().getExpiration();
        }
        return null;
    }

}