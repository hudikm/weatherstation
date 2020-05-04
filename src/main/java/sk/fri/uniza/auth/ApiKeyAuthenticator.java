package sk.fri.uniza.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

import java.util.Optional;

public class ApiKeyAuthenticator implements Authenticator<String, ApiKeyCredentials> {
    //HMAC
    Algorithm algorithmHS = Algorithm.HMAC256("secret");

    @Override
    public Optional<ApiKeyCredentials> authenticate(String token) throws AuthenticationException {
        try {
            JWTVerifier verifier = JWT.require(algorithmHS)
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return Optional.of(new ApiKeyCredentials(jwt));
        } catch (JWTVerificationException exception) {
            return Optional.empty();
        }

    }
}
