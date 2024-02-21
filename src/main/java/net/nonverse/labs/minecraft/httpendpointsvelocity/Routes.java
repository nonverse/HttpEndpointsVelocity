package net.nonverse.labs.minecraft.httpendpointsvelocity;

import net.nonverse.labs.minecraft.httpendpointsvelocity.Api.ApiInitializer;
import org.slf4j.Logger;


public final class Routes {

    public static void registerRoutes(WebServer server, Logger log) {
        ApiInitializer api = new ApiInitializer();

        // Player
        server.post("player/message", api.chatApi()::sendMessage);
    }
}
