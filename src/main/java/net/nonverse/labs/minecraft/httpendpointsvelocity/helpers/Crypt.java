package net.nonverse.labs.minecraft.httpendpointsvelocity.helpers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Crypt {

    /**
     * Load RSAPublicKey from .key or .pem file
     *
     * @param keyFile
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static RSAPublicKey loadRsaKey(File keyFile) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String key = new String(Files.readAllBytes(keyFile.toPath()));
        key = key
                .replaceAll("-+[^-]+-+", "")
                .replace("\n", "")
                .replace("\r", "");

        byte[] keyBytes = Base64.getDecoder().decode(key);


        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");

        return (RSAPublicKey) kf.generatePublic(spec);
    }
}

