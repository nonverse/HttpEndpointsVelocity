package net.nonverse.labs.minecraft.httpendpointsvelocity;

import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Handler;
import io.javalin.http.HandlerType;
import org.slf4j.Logger;

public class WebServer {

    private final int port;
    private final Logger log;
    private final Javalin javalin;

    /**
     * Construct the javalin web server
     *
     * @param log
     */
    public WebServer(Logger log) {
        this.port = 80;
        this.log = log;

        this.javalin = Javalin.create(config -> configureJavalin(config));
    }

    /**
     * Configure the javalin web server
     *
     * @param config
     */
    private void configureJavalin(JavalinConfig config) {
        config.http.defaultContentType = "application/json";
        config.showJavalinBanner = false;
    }

    /**
     * Add a specified middleware handler to a route
     *
     * @param route
     * @param handler
     */
    public void middleware(String route, Handler handler) {
        this.javalin.before(route, handler);
    }

    /**
     * Register a GET route on the web server
     *
     * @param route
     * @param handler
     */
    public void get(String route, Handler handler) {
        this.javalin.addHttpHandler(HandlerType.GET, route, handler);
    }

    /**
     * Register a POST route on the web server
     *
     * @param route
     * @param handler
     */
    public void post(String route, Handler handler) {
        this.javalin.addHttpHandler(HandlerType.POST, route, handler);
    }

    /**
     * Start the web server
     */
    public void start() {
        this.javalin.start(this.port);
    }

    /**
     * Stop the web server
     */
    public void stop() {
        this.javalin.stop();
    }

    /**
     * Get the javalin instance of the web server
     *
     * @return
     */
    public Javalin getJavalin() {
        return this.javalin;
    }
}
