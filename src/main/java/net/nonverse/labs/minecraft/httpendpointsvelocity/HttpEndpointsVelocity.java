package net.nonverse.labs.minecraft.httpendpointsvelocity;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.nio.file.Path;

@Plugin(
        id = "httpendpointsvelocity",
        name = "HttpEndpointsVelocity",
        version = BuildConstants.VERSION
)
public class HttpEndpointsVelocity {
    private Logger logger;
    private final ProxyServer server;
    private final Path dataDirectory;
    public static HttpEndpointsVelocity instance;

    @Inject
    public HttpEndpointsVelocity(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        startWebServer();

        instance = this;
    }

    /**
     * Start the webserver and configure all routes and middlewares
     */
    private void startWebServer() {
        WebServer server = new WebServer(logger);

        server.start();

        Routes.registerRoutes(server, logger);
        Middlewares.registerMiddlewares(server, logger, this.dataDirectory);
    }

    public ProxyServer getServer() {
        return this.server;
    }

    public static HttpEndpointsVelocity getInstance() {
        return instance;
    }
}
