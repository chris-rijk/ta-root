package ta.microservices.common.service.lifecycle;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import ta.microservices.common.service.jerseyfilters.SecurityFilter;

public class JerseyConfig extends ResourceConfig {

    protected void RegisterDefault() {
        RegisterSerializer();
        RegisterSecurityRoleNames();
        RegisterJWTSecurityFilter();
    }

    protected void RegisterJWTSecurityFilter() {
        register(SecurityFilter.class);
    }

    protected void RegisterSecurityRoleNames() {
        register(RolesAllowedDynamicFeature.class);
    }

    protected void RegisterSerializer() {
        register(JacksonFeature.class);
    }
}
