package ta.microservices.common.service.jerseyfilters;

import ta.microservices.common.service.security.Authorizer;
import ta.microservices.common.service.security.TokenUtil;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Authorizer authorizer = getAuthorisation(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION));
        if (authorizer == null) {
            authorizer = Authorizer.AnyAuthorizer;
        }
        requestContext.setSecurityContext(authorizer);
    }

    private Authorizer getAuthorisation(String authorizationHeader) {
        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String strToken = authorizationHeader.substring("Bearer".length()).trim();

                TokenUtil tu = new TokenUtil(strToken);
                if (tu.isValid()) {
                    String name = tu.getName();
                    String[] roles = tu.getRoles();
                    int version = tu.getVersion();
                    if (name != null && roles.length != 0 && version != -1) {
                        return new Authorizer(roles, name, tu.getExpiration(),true);
                    }
                }
            }
        } catch (Exception e) {
            // todo:
        }

        return null;
    }
}