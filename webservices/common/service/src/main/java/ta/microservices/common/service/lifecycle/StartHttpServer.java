package ta.microservices.common.service.lifecycle;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartHttpServer {
    public static void Start(SystemConfiguration sysConfig, ResourceConfig config) throws IOException {
        URI uri = sysConfig.getBindURI();
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
        Logger.getLogger(StartHttpServer.class.getName()).log(Level.INFO, "Application accessible at {0}", uri.toString());

        //gracefully exit Grizzly services when app is shut down
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                Logger.getLogger(StartHttpServer.class.getName()).info("Exiting......");
                server.shutdownNow();
                Logger.getLogger(StartHttpServer.class.getName()).info("REST services stopped");
            }
        }));
        server.start();
    }
}
