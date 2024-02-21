package net.nonverse.labs.minecraft.httpendpointsvelocity;

import net.nonverse.labs.minecraft.httpendpointsvelocity.Api.Middleware.MiddlewareInitializer;
import org.slf4j.Logger;

import java.nio.file.Path;

public final class Middlewares {

    public static void registerMiddlewares(WebServer server, Logger log, Path dataDirectory) {
        MiddlewareInitializer middlewares = new MiddlewareInitializer(dataDirectory);

        // Player middlewares
        server.middleware("player/*", middlewares.auth()::online);

        // Root
        server.middleware("*", middlewares.auth()::jwt);
    }
}