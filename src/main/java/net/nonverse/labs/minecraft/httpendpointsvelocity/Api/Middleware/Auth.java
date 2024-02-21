package net.nonverse.labs.minecraft.httpendpointsvelocity.Api.Middleware;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.UnauthorizedResponse;
import net.nonverse.labs.minecraft.httpendpointsvelocity.HttpEndpointsVelocity;
import net.nonverse.labs.minecraft.httpendpointsvelocity.helpers.Crypt;

import java.io.File;
import java.nio.file.Path;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

public class Auth {

    private final Path dataFolder;

    public Auth(Path dataFolder) {
        this.dataFolder = dataFolder;
    }

    public void jwt(Context ctx) {
        this.validateJwt(ctx);
    }

    public void online(Context ctx) {
        DecodedJWT decodedJWT = this.validateJwt(ctx);

        if (decodedJWT.getSubject().isEmpty()) {
            throw new ForbiddenResponse("No user present in request");
        }

        if (HttpEndpointsVelocity.getInstance().getServer().getPlayer(UUID.fromString(decodedJWT.getSubject())).isEmpty()) {
            throw new ForbiddenResponse("Player is not online");
        }

        ctx.attribute("user", HttpEndpointsVelocity.getInstance().getServer().getPlayer(UUID.fromString(decodedJWT.getSubject())).get());
    }

    private DecodedJWT validateJwt(Context ctx) {
        String tokenHeader = ctx.header("Authorization");
        if (tokenHeader == null) {
            throw new UnauthorizedResponse();
        }

        String token = tokenHeader.replace("Bearer ", "");
        DecodedJWT decodedToken;

        try {
            RSAPublicKey pubKey = Crypt.loadRsaKey(new File(this.dataFolder.toString(), "xs-public.key"));

            Algorithm algorithm = Algorithm.RSA256(pubKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("https://api.nonverse.test/")
                    .withAudience("https://mc.labs.nonverse.test/")
                    .withClaim("ttp", "labs:mc:xs")
                    .build();

            return verifier.verify(token);

        } catch (Exception e) {
            throw new UnauthorizedResponse();
        }
    }
}
