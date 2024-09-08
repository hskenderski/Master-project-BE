package svc.library.unibitdiplomna.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "rsa")
public class RsaKeyProperties {
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    public RSAPrivateKey getPrivateKey()
    {
        return privateKey;
    }

    public RSAPublicKey getPublicKey()
    {
        return publicKey;
    }

    public void setPrivateKey(RSAPrivateKey privateKey)
    {
        this.privateKey = privateKey;
    }

    public void setPublicKey(RSAPublicKey publicKey)
    {
        this.publicKey = publicKey;
    }
}