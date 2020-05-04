package sk.fri.uniza.auth;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.basic.BasicCredentials;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;

import java.security.Principal;

public class ApiKeyAuthFilter<P extends Principal> extends AuthFilter<String, P> {

    private static final String API_KEY_AUTH = "API_KEY";

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String token = containerRequestContext.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (!this.authenticate(containerRequestContext, token, API_KEY_AUTH)) {
            throw new WebApplicationException(this.unauthorizedHandler.buildResponse(this.prefix, this.realm));
        }
    }
    public static class Builder<P extends Principal> extends AuthFilterBuilder<String, P, ApiKeyAuthFilter<P>> {
        public Builder() {
        }

        protected ApiKeyAuthFilter newInstance() {
            return new ApiKeyAuthFilter();
        }
    }

}
