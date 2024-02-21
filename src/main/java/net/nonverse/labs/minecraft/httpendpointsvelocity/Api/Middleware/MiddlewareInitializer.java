package net.nonverse.labs.minecraft.httpendpointsvelocity.Api.Middleware;

import java.nio.file.Path;

public class MiddlewareInitializer {
    private final Auth authMiddleware;

    public MiddlewareInitializer(Path dataDirectory) {
        // Instantiate middleware handlers
        this.authMiddleware = new Auth(dataDirectory);
    }

    public Auth auth() {
        return this.authMiddleware;
    }
}
