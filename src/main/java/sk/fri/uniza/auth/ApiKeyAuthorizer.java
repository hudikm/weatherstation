package sk.fri.uniza.auth;

import io.dropwizard.auth.Authorizer;

public class ApiKeyAuthorizer implements Authorizer<ApiKeyCredentials> {

    @Override
    public boolean authorize(ApiKeyCredentials apiKeyCredentials, String role) {
        return !apiKeyCredentials.getJwtToken().getClaim(role).isNull();
    }
}
