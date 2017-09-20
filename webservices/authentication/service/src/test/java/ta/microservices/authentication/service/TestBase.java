package ta.microservices.authentication.service;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import ta.microservices.authentication.service.interfaces.IUserDatabase;
import ta.microservices.authentication.service.startup.AppConfig;

import static org.mockito.Mockito.mock;

public abstract class TestBase extends JerseyTest {
    protected IUserDatabase injectionMock;

    @Override
    protected ResourceConfig configure() {
        injectionMock = mock(IUserDatabase.class);
        AppConfig config = new AppConfig();
        config.SetupUserDatabase(injectionMock);
        return config;
    }
}
