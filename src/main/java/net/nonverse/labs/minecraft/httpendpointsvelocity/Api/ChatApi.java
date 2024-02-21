package net.nonverse.labs.minecraft.httpendpointsvelocity.Api;

import com.velocitypowered.api.proxy.Player;
import io.javalin.http.Context;
import io.javalin.http.UnprocessableContentResponse;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ChatApi {

    /**
     * Send a message to the player whose user is making the request
     *
     * @param ctx Request context
     */
    public void sendMessage(@NotNull Context ctx) {
        try {
            Component message = Component.text(Objects.requireNonNull(ctx.formParam("message")));
            Player player = Objects.requireNonNull(ctx.attribute("user"));

            player.sendMessage(message);
        } catch (NullPointerException e) {
            throw new UnprocessableContentResponse();
        }
    }
}
