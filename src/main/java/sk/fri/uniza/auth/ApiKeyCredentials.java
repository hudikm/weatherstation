package sk.fri.uniza.auth;

import com.auth0.jwt.interfaces.DecodedJWT;

import javax.security.auth.Subject;
import java.security.Principal;

public class ApiKeyCredentials extends User {
    private DecodedJWT jwtToken;

    public ApiKeyCredentials() {
        super("ApiKey");
    }

    public ApiKeyCredentials(DecodedJWT jwtToken) {
        super("ApiKey");
        this.jwtToken = jwtToken;
    }

    public DecodedJWT getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(DecodedJWT jwtToken) {
        this.jwtToken = jwtToken;
    }
}
