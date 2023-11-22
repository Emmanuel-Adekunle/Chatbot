package test.weatherAPI;

import org.junit.jupiter.api.Test;
import trendsetters.weatherAPI.WeatherForecastApi;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class WeatherForecastApiTest {
    @Test
    public void testMain() throws Exception {
        // Prepare input stream with city and day of the week
        String input = "Boston\nMonday\n";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(in);

        // Call the main method
        WeatherForecastApi.main(null);
    }

    @Test
    public void testSuggestClothing() {
        // Test clothing suggestions for different weather conditions
        double temperature = 35.0;
        double windSpeed = 10.0;
        double precipitation = 0.0;
        double humidity = 50.0;
        String clothing = WeatherForecastApi.suggestClothing(temperature, windSpeed, precipitation, humidity);
        System.out.println("Clothing suggestion for temperature " + temperature + " Celsius, wind speed " + windSpeed + " mph, precipitation " + precipitation + " mm, and humidity " + humidity + "%: " + clothing);

        temperature = 25.0;
        windSpeed = 20.0;
        precipitation = 1.0;
        humidity = 80.0;
        clothing = WeatherForecastApi.suggestClothing(temperature, windSpeed, precipitation, humidity);
        System.out.println("Clothing suggestion for temperature " + temperature + " Celsius, wind speed " + windSpeed + " mph, precipitation " + precipitation + " mm, and humidity " + humidity + "%: " + clothing);

        temperature = 5.0;
        windSpeed = 5.0;
        precipitation = 0.2;
        humidity = 90.0;
        clothing = WeatherForecastApi.suggestClothing(temperature, windSpeed, precipitation, humidity);
        System.out.println("Clothing suggestion for temperature " + temperature + " Celsius, wind speed " + windSpeed + " mph, precipitation " + precipitation + " mm, and humidity " + humidity + "%: " + clothing);

        temperature = -5.0;
        windSpeed = 15.0;
        precipitation = 0.5;
        humidity = 60.0;
        clothing = WeatherForecastApi.suggestClothing(temperature, windSpeed, precipitation, humidity);
        System.out.println("Clothing suggestion for temperature " + temperature + " Celsius, wind speed " + windSpeed + " mph, precipitation " + precipitation + " mm, and humidity " + humidity + "%: " + clothing);
    }

}
