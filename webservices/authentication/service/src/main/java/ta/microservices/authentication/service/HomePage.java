package ta.microservices.authentication.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Path("")
@Produces(MediaType.TEXT_HTML)
public class HomePage {

    @GET
    public BufferedReader index() {
        InputStream in = getClass().getResourceAsStream("/index.html");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        return reader;
    }
}
