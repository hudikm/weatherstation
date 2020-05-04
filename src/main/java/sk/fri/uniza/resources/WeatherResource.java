package sk.fri.uniza.resources;

import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.*;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.scanners.TypeElementsScanner;
import sk.fri.uniza.api.Location;
import sk.fri.uniza.api.WeatherData;
import sk.fri.uniza.core.Utility;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@SwaggerDefinition(
        info = @Info(
                description = "Získanie dát o počasí",
                version = "V1.0.0",
                title = "Meteorologická webová služba",
                contact = @Contact(
                        name = "Martin Húdik",
                        email = "martin.hudik@fri.uniza.sk"
                )
        ),
        consumes = {"application/json"},
        produces = {"application/json"},
        schemes = {SwaggerDefinition.Scheme.HTTP}

)

@Api("/weather")
@Path("/weather")
@Produces(MediaType.APPLICATION_JSON)
public class WeatherResource {

    Reflections reflections = new Reflections(WeatherData.class, new MethodParameterScanner(), new TypeElementsScanner());
    DateTimeFormatter timeFormatObj = DateTimeFormatter.ofPattern("HH:mm");
    DateTimeFormatter dateFormatObj = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ofPattern("d/M/yyyy HH:mm"))
            .appendOptional(DateTimeFormatter.ofPattern(("dd/MM/yyyy HH:mm")))
            .toFormatter();
    private LocalDate currentDate = LocalDate.of(2016, 1, 1);
    private List<WeatherData> weatherData;
    private Random random = new Random();

    public WeatherResource(List<WeatherData> weatherData) {

        this.weatherData = weatherData;
    }

    private String getCurrentDateString() {
        return LocalDate.now().format(dateFormatObj);
    }

    double getRandomValue(int minute, double min, double max) {
        random.setSeed(minute);
        return (random.nextDouble() * (max - min)) + min; //    This Will Create
    }

    WeatherData copyFields(WeatherData src, WeatherData dst, List<String> params) {
        for (String param : params) {
            Set<Field> fields = ReflectionUtils.getFields(WeatherData.class, ReflectionUtils.withName(param));
            fields.forEach(field -> {
                field.setAccessible(true);
                try {
                    field.set(dst, field.get(src));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        }
        return dst;
    }

    @GET
    @Path("{station}/current")
    @ApiOperation(value = "Aktuálne počasie", notes = "Získanie dát o aktuálnom počasí. Dáta sú obnovované každú " +
            "minútu.")
    @Timed
    public Optional<WeatherData> getCurrentWeather(@ApiParam(value = "ID meteo stanice", required = true,
            example =
            "station_1") @PathParam("station") String station,
                                                   @ApiParam(value = "Vyfiltrovania požadavaných údajov", allowableValues = "stationName, date, time, airTemperature, wetBulbTemperature, humidity, rainIntensity, intervalRain, totalRain, precipitationType, windDirection, windSpeed,maximumWindSpeed,barometricPressure, solarRadiation, heading, batteryLife, measurementTimestampLabel")
                                                   @QueryParam("fields") final List<String> fields) {
        Optional<WeatherData> first = weatherData.stream().filter(weatherData1 -> weatherData1.getStationName().equals(station)
                && weatherData1.getDate().equals(getCurrentDateString())
                && weatherData1.getLocalTime().equals(LocalTime.now().truncatedTo(ChronoUnit.HOURS))).findFirst();

        if (first.isPresent()) {
            try {
                first = Optional.ofNullable(first.get().clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        final Optional<WeatherData> firstClone = first;
        firstClone.ifPresent(weatherData1 -> {
            weatherData1.setTime(LocalTime.now().format(timeFormatObj));
        });

        LocalDateTime plusHour = LocalDateTime.now().plusHours(1);

        Optional<WeatherData> second = weatherData.stream().filter(weatherData1 -> weatherData1.getStationName().equals(station)
                && weatherData1.getDate().equals(plusHour.format(dateFormatObj))
                && weatherData1.getLocalTime().equals(plusHour.truncatedTo(ChronoUnit.HOURS).toLocalTime())).findFirst();

        if (firstClone.isPresent() && second.isPresent()) {

            Set<Field> allFields = ReflectionUtils.getAllFields(WeatherData.class, ReflectionUtils.withTypeAssignableTo(Number.class));
            int minute = LocalTime.now().getMinute();
            allFields.forEach(field -> {
                try {
                    field.setAccessible(true);

                    Number number1 = (Number) field.get(firstClone.get());
                    Number number2 = (Number) field.get(second.get());
                    double i;
                    Random random = new Random();
                    random.setSeed(minute);
                    if (number1 != null && number2 != null)
                        if (number1 instanceof Integer) {
                            i = number2.intValue() - number1.intValue();
                            field.set(firstClone.get(), (int) (number1.intValue() * getRandomValue(minute, 0.9, 1) + (int) (i / 60.0) * minute));

                        } else {
                            i = number2.doubleValue() - number1.doubleValue();
                            field.set(firstClone.get(), number1.doubleValue() * getRandomValue(minute, 0.9, 1) + (i / 60.0) * minute);
                        }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            });

        }

        if (!fields.isEmpty()) {
            return Optional.ofNullable(copyFields(firstClone.get(), new WeatherData(), fields));
        }
        return firstClone;
    }

    @GET
    @Path("{station}/history")
    @ApiOperation(value = "História počasia", notes="Vráti historické dáta o počasí z vybranej meteo stanice v " +
            "zadanom časovom rozmedzí")
    @Timed
    public List<WeatherData> getHistoryWeather(@ApiParam(value = "ID meteo stanice", required = true,
            example = "station_1") @PathParam(
            "station") String station,
                                               @ApiParam(value = "DateTime from: dd/MM/yyyy HH:mm", collectionFormat
                                                       = "dd/MM/yyyy HH:mm" ,required = true,
                                                       example = "20/01/2020 15:00") @QueryParam("from") String dateTimeFrom,
                                               @ApiParam(value = "DateTime from: dd/MM/yyyy HH:mm", collectionFormat
                                                       = "dd/MM/yyyy HH:mm", required = true, example = "21/01/2020 " +
                                                       "15:00") @QueryParam("to") String dateTimeTo,
                                               @ApiParam(value = "Vyfiltrovania požadavaných údajov", allowableValues = "stationName, date, time, airTemperature, wetBulbTemperature, humidity, rainIntensity, intervalRain, totalRain, precipitationType, windDirection, windSpeed,maximumWindSpeed,barometricPressure, solarRadiation, heading, batteryLife, measurementTimestampLabel")
                                               @QueryParam("fields") final List<String> fields) {

        try {
            LocalDateTime from = LocalDateTime.parse(dateTimeFrom, formatter);
            LocalDateTime to = LocalDateTime.parse(dateTimeTo, formatter);
            if (from.isBefore(to) || from.isEqual(to)) {
                List<WeatherData> first = weatherData.stream().filter(
                        weatherData1 -> weatherData1.getStationName().equals(station)
                                && ((weatherData1.getDateTime().isAfter(from) && weatherData1.getDateTime().isBefore(to))
                                || (weatherData1.getDateTime().isEqual(from) || weatherData1.getDateTime().isEqual(to))))
                        .collect(Collectors.toList());
                if (!fields.isEmpty()) {
                    List<WeatherData> collect = first.stream().map(weatherData1 -> copyFields(weatherData1, new WeatherData(), fields)).collect(Collectors.toList());
                    return collect;
                }
                return first;
            } else
                return new ArrayList<>();
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(), Response.Status.NOT_FOUND);
        }
    }

    @GET
    @Path("locations")
    @ApiOperation(value = "Zoznam meteo staníc")
    public List<Location> getLocations() {
        Map<String, Location> locationMap = Map.of("station_1", new Location("station_1", "Veľká okružná", "Žilna", "Slovakia",
                "49.219715, 18.744436"), "station_2", new Location("station_2", "Predmestská", "Žilna", "Slovakia",
                "49.2200445,18.7447796"), "station_3", new Location("station_3", "1. mája", "Žilna", "Slovakia",
                "49.2219346,18.7441314"));
        List<Location> collect = weatherData.stream()
                .filter(Utility.distinctByKey(weatherData1 -> weatherData1.getStationName()))
                .map(weatherData1 -> locationMap.get(weatherData1.getStationName()))
                .collect(Collectors.toList());
        return collect;
    }


}
