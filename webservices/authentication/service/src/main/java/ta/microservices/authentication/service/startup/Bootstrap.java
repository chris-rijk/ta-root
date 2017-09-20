package ta.microservices.authentication.service.startup;

import ta.microservices.authentication.service.interfaces.IUserDatabase;
import ta.microservices.common.service.lifecycle.StartHttpServer;
import ta.microservices.common.service.lifecycle.SystemConfiguration;
import ta.microservices.common.service.security.AuthenticationRole;

import java.io.IOException;

public class Bootstrap {

    public static void main(String[] args) throws IOException {
        AppConfig app = new AppConfig();
        app.SetupUserDatabase(new MyUserDatabase());
        StartHttpServer.Start(new SystemConfiguration(), app);
    }

    private static class MyUserDatabase implements IUserDatabase {
        @Override
        public String getValue() {
            return "for realz";
        }

        @Override
        public String[] getRolesForUser(String username, String password) {
            if ("john.doe".equals(username) && "foobar".equals(password)) {
                return new String[]{AuthenticationRole.Admin};
            }
            if ("joe.blogs".equals(username) && "wibble".equals(password)) {
                return new String[]{AuthenticationRole.User};
            }
            return null;
        }
    }
}
