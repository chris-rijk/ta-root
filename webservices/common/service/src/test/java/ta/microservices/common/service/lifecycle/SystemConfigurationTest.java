package ta.microservices.common.service.lifecycle;

import org.junit.Test;

import static org.junit.Assert.*;

public class SystemConfigurationTest {
    @Test
    public void URI() {
        SystemConfiguration sc = new SystemConfiguration();
        assertEquals("http://localhost:18080/", sc.getBindURI().toString());
    }
}