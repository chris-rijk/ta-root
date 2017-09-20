package ta.microservices.common.service.security;

import java.security.Principal;
import java.util.Date;

public class LoginUser implements Principal {
    private final String name;
    private final Date expiration;
    private final String[] roles;

    public LoginUser(String name, Date expiration, String[] roles) {
        this.name = name;
        this.expiration = expiration;
        this.roles = roles;
    }

    @Override
    public String getName() {
        return name;
    }

    public Date getExpiration() { return expiration; }

    public String[] getRoles() { return roles; }
}
