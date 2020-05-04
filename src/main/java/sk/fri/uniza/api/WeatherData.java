package sk.fri.uniza.api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Station Name",
        "Date",
        "Time",
        "Air Temperature",
        "Wet Bulb Temperature",
        "Humidity",
        "Rain Intensity",
        "Interval Rain",
        "Total Rain",
        "Precipitation Type",
        "Wind Direction",
        "Wind Speed",
        "Maximum Wind Speed",
        "Barometric Pressure",
        "Solar Radiation",
        "Heading",
        "Battery Life",
        "Measurement Timestamp Label"
})
public class WeatherData implements Cloneable {

    @JsonProperty("Station Name")
    private String stationName;
    @JsonProperty("Date")
    private LocalDate date;
    @JsonProperty("Time")
    private LocalTime time;
    @JsonSerialize(using = CustomDoubleSerializer.class)
    @JsonProperty("Air Temperature")
    private Double airTemperature;
    @JsonSerialize(using = CustomDoubleSerializer.class)
    @JsonProperty("Wet Bulb Temperature")
    private Double wetBulbTemperature;
    @JsonProperty("Humidity")
    private Integer humidity;
    @JsonSerialize(using = CustomDoubleSerializer.class)
    @JsonProperty("Rain Intensity")
    private Double rainIntensity;
    @JsonSerialize(using = CustomDoubleSerializer.class)
    @JsonProperty("Interval Rain")
    private Double intervalRain;
    @JsonProperty("Total Rain")
    private Integer totalRain;
    @JsonProperty("Precipitation Type")
    private Integer precipitationType;
    @JsonProperty("Wind Direction")
    private Integer windDirection;
    @JsonSerialize(using = CustomDoubleSerializer.class)
    @JsonProperty("Wind Speed")
    private Double windSpeed;
    @JsonSerialize(using = CustomDoubleSerializer.class)
    @JsonProperty("Maximum Wind Speed")
    private Double maximumWindSpeed;
    @JsonSerialize(using = CustomDoubleSerializer.class)
    @JsonProperty("Barometric Pressure")
    private Double barometricPressure;
    @JsonProperty("Solar Radiation")
    private Integer solarRadiation;
    @JsonProperty("Heading")
    private Integer heading;
    @JsonProperty("Battery Life")
    private Integer batteryLife;
    @JsonProperty("Measurement Timestamp Label")
    private String measurementTimestampLabel;
    static private int plusYears = LocalDate.now().getYear() - 2016;

    @JsonProperty("Station Name")
    public String getStationName() {
        return stationName;
    }

    @JsonProperty("Station Name")
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @JsonProperty("Date")
    public String getDate() {
        if (date == null) return null;
        return date.format(dateFormatter);
    }

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    @JsonIgnore
    public void setPlusYears(int years) {
        this.plusYears = years;
    }

    @JsonIgnore
    public LocalDateTime getDateTime() {
        return LocalDateTime.of(date, time);
    }

    @JsonSetter("Date")
    public void setDate(String date) {
        this.date = LocalDate.parse(date, dateFormatter);
        this.date = this.date.plusYears(plusYears);
    }

    @JsonProperty("Time")
    public String getTime() {
        if (time == null) return null;
        return time.format(timeFormatter);
    }

    @JsonIgnore
    public LocalTime getLocalTime() {
        return time;
    }

    @JsonProperty("Time")
    public void setTime(String time) {
        this.time = LocalTime.parse(time, timeFormatter);

    }

    @JsonProperty("Air Temperature")
    public Double getAirTemperature() {
        return airTemperature;
    }

    @JsonProperty("Air Temperature")
    public void setAirTemperature(Double airTemperature) {
        this.airTemperature = airTemperature;
    }

    @JsonProperty("Wet Bulb Temperature")
    public Double getWetBulbTemperature() {
        return wetBulbTemperature;
    }

    @JsonProperty("Wet Bulb Temperature")
    public void setWetBulbTemperature(Double wetBulbTemperature) {
        this.wetBulbTemperature = wetBulbTemperature;
    }

    @JsonProperty("Humidity")
    public Integer getHumidity() {
        return humidity;
    }

    @JsonProperty("Humidity")
    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    @JsonProperty("Rain Intensity")
    public Double getRainIntensity() {
        return rainIntensity;
    }

    @JsonProperty("Rain Intensity")
    public void setRainIntensity(Double rainIntensity) {
        this.rainIntensity = rainIntensity;
    }

    @JsonProperty("Interval Rain")
    public Double getIntervalRain() {
        return intervalRain;
    }

    @JsonProperty("Interval Rain")
    public void setIntervalRain(Double intervalRain) {
        this.intervalRain = intervalRain;
    }

    @JsonProperty("Total Rain")
    public Integer getTotalRain() {
        return totalRain;
    }

    @JsonProperty("Total Rain")
    public void setTotalRain(Integer totalRain) {
        this.totalRain = totalRain;
    }

    @JsonProperty("Precipitation Type")
    public Integer getPrecipitationType() {
        return precipitationType;
    }

    @JsonProperty("Precipitation Type")
    public void setPrecipitationType(Integer precipitationType) {
        this.precipitationType = precipitationType;
    }

    @JsonProperty("Wind Direction")
    public Integer getWindDirection() {
        return windDirection;
    }

    @JsonProperty("Wind Direction")
    public void setWindDirection(Integer windDirection) {
        this.windDirection = windDirection;
    }

    @JsonProperty("Wind Speed")
    public Double getWindSpeed() {
        return windSpeed;
    }

    @JsonProperty("Wind Speed")
    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    @JsonProperty("Maximum Wind Speed")
    public Double getMaximumWindSpeed() {
        return maximumWindSpeed;
    }

    @JsonProperty("Maximum Wind Speed")
    public void setMaximumWindSpeed(Double maximumWindSpeed) {
        this.maximumWindSpeed = maximumWindSpeed;
    }

    @JsonProperty("Barometric Pressure")
    public Double getBarometricPressure() {
        return barometricPressure;
    }

    @JsonProperty("Barometric Pressure")
    public void setBarometricPressure(Double barometricPressure) {
        this.barometricPressure = barometricPressure;
    }

    @JsonProperty("Solar Radiation")
    public Integer getSolarRadiation() {
        return solarRadiation;
    }

    @JsonProperty("Solar Radiation")
    public void setSolarRadiation(Integer solarRadiation) {
        this.solarRadiation = solarRadiation;
    }

    @JsonProperty("Heading")
    public Integer getHeading() {
        return heading;
    }

    @JsonProperty("Heading")
    public void setHeading(Integer heading) {
        this.heading = heading;
    }

    @JsonProperty("Battery Life")
    public Integer getBatteryLife() {
        return batteryLife;
    }

    @JsonProperty("Battery Life")
    public void setBatteryLife(Integer batteryLife) {
        this.batteryLife = batteryLife;
    }

    @JsonProperty("Measurement Timestamp Label")
    public String getMeasurementTimestampLabel() {
        return measurementTimestampLabel;
    }

    @JsonProperty("Measurement Timestamp Label")
    public void setMeasurementTimestampLabel(String measurementTimestampLabel) {
        this.measurementTimestampLabel = measurementTimestampLabel;
    }

    @JsonIgnore
    public WeatherData clone() throws CloneNotSupportedException {
        return (WeatherData) super.clone();
    }
}