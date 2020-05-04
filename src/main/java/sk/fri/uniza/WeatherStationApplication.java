package sk.fri.uniza;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.auth.chained.ChainedAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import sk.fri.uniza.api.WeatherData;
import sk.fri.uniza.auth.*;
import sk.fri.uniza.resources.ApiKeyResource;
import sk.fri.uniza.resources.WeatherResource;
import sk.fri.uniza.resources.WeatherResourceAuth;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class WeatherStationApplication extends Application<WeatherStationConfiguration> {

    public static void main(final String[] args) throws Exception {
        new WeatherStationApplication().run(args);
    }

    @Override
    public String getName() {
        return "WeatherStation";
    }

    @Override
    public void initialize(final Bootstrap<WeatherStationConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<WeatherStationConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(WeatherStationConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public void run(final WeatherStationConfiguration configuration,
                    final Environment environment) {
        List<WeatherData> weatherData = null;

        ObjectMapper mapper = new ObjectMapper();
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("weather.json");
        try {

            weatherData = mapper.readValue(resourceAsStream, new TypeReference<List<WeatherData>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        AuthFilter authFilter = new ApiKeyAuthFilter.Builder()
                .setAuthenticator(new ApiKeyAuthenticator())
                .setAuthorizer(new ApiKeyAuthorizer())
                .setPrefix("Bearer")
                .buildAuthFilter();

        AuthFilter basicCredentialAuthFilter = new BasicCredentialAuthFilter.Builder()
                .setAuthenticator(new BasicAuthenticator())
                .setAuthorizer(new BasicAuthorizer())
                .setPrefix("Basic")
                .buildAuthFilter();

        List<AuthFilter> filters = Lists.newArrayList(basicCredentialAuthFilter, authFilter);
        environment.jersey().register(new AuthDynamicFeature(new ChainedAuthFilter(filters)));
        environment.jersey().register(RolesAllowedDynamicFeature.class);

        WeatherResource weatherResource = new WeatherResource(weatherData);
        environment.jersey().register(weatherResource);
        WeatherResourceAuth weatherResourceAuth = new WeatherResourceAuth(weatherData);
        environment.jersey().register(weatherResourceAuth);
        environment.jersey().register(new ApiKeyResource());

    }

}
