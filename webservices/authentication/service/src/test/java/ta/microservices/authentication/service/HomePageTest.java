package ta.microservices.authentication.service;

import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class HomePageTest extends TestBase {
    @Test
    public void testHomePage() {
        Response response = target().path("/").request().get();
        assertEquals(200, response.getStatus());
    }
}
