package ta.microservices.common.service.lifecycle;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Optional;

public class SystemConfiguration {
    public String getHostname() {
        return Optional.ofNullable(System.getenv("HOSTNAME")).orElse("localhost");
    }

    public String getPort()
    {
        return Optional.ofNullable(System.getenv("PORT")).orElse("18080");
    }

    public URI getBindURI() {
        return UriBuilder.fromUri("http://" + getHostname() + "/").port(Integer.parseInt(getPort())).build();
    }
}
