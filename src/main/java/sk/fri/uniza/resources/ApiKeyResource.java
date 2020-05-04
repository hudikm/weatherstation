package sk.fri.uniza.resources;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import io.swagger.annotations.*;
import sk.fri.uniza.api.Token;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;
@SwaggerDefinition(
        securityDefinition = @SecurityDefinition(
                basicAuthDefinitions = {
                        @BasicAuthDefinition(key = "basic",
                                description = "Basic Authentication (Login: admin/heslo)")
                }
        )
)

@Api(value = "/apikey", authorizations = {@Authorization(value = "basic")})
@Path("/apikey")
@Produces(MediaType.APPLICATION_JSON)
public class ApiKeyResource {
    Algorithm algorithm = Algorithm.HMAC256("secret");
    public enum Claims{
        history("history"),
        current("current"),
        locations("locations"),
        all("all");

        public String getClaim() {
            return claim;
        }

        private String claim;

        Claims(String claim) {
            this.claim = claim;
        }

        @Override
        public String toString() {
            return claim;
        }
    }

    @POST
    @Path("/createjwt")
    @ApiOperation(value = "Získanie JWT tókenu. Zabezpečené pomocou BasicAuth(Meno: admin, Heslo: heslo)", notes =
            "JWT tokén" +
            " je potrebný " +
            "na " +
            "prístup " +
            "k zabezpečeným " +
            "zdrojom: " +
            "Značené ako \'Auth\' zdroje")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RolesAllowed("ADMIN")
    public Token createJwt(@ApiParam(value = "Požiadavky na určenie opravnení pre vygenerovaný tóken",
            required = true) @FormParam(
            "claims") List<Claims> claims){
        try {
            JWTCreator.Builder tBuilder = JWT.create()
                    .withIssuer("auth0");
            claims.forEach(claim -> tBuilder.withClaim(claim.getClaim(),true));
            String token = tBuilder.sign(algorithm);

            return new Token(token);
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
            throw new WebApplicationException("JWT token nemože byť vygenerovany");
        }
    }
}
