package ta.microservices.authentication.service.startup;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import ta.microservices.authentication.api.IAuthenticationService;
import ta.microservices.authentication.service.AuthenticationService;
import ta.microservices.authentication.service.HomePage;
import ta.microservices.authentication.service.interfaces.IUserDatabase;
import ta.microservices.common.service.lifecycle.JerseyConfig;

public class AppConfig extends JerseyConfig {

    public AppConfig() {
        this.RegisterDefault();
        register(HomePage.class);
        register(AuthenticationService.class, IAuthenticationService.class);
    }

    public void SetupUserDatabase(IUserDatabase userData) {
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(userData).to(IUserDatabase.class);
            }
        });
    }
}