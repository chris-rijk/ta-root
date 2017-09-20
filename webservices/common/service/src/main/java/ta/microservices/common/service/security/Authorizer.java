package ta.microservices.common.service.security;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Authorizer implements SecurityContext {

    public static final Authorizer AnyAuthorizer = new Authorizer(
            new String[]{AuthenticationRole.Any}, null, null, true);
    private final Set<String> roles;
    private final boolean isSecure;
    private final LoginUser user;

    public Authorizer(String[] roles, String username, Date expiration, boolean isSecure) {
        this.roles = new HashSet<>(Arrays.asList(roles));
        this.isSecure = isSecure;
        this.user = new LoginUser(username, expiration, roles);
    }

    @Override
    public Principal getUserPrincipal() {
        return user;
    }

    @Override
    public boolean isUserInRole(String role) {
        return AuthenticationRole.Any.equals(role) || roles.contains(role);
    }

    @Override
    public boolean isSecure() {
        return isSecure;
    }

    @Override
    public String getAuthenticationScheme() {
        return "Bearer";
    }
}
