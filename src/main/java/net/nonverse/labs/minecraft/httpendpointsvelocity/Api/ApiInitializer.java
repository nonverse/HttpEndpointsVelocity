package net.nonverse.labs.minecraft.httpendpointsvelocity.Api;

public class ApiInitializer {

    private final ChatApi chatApi;

    public ApiInitializer() {

        // Instantiate api handlers
        this.chatApi = new ChatApi();
    }

    public ChatApi chatApi() {
        return this.chatApi;
    }
}
