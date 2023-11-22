package trendsetters.weatherAPI;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class WeatherForecastApi {

    public static void main(String[] args) throws Exception {
        // Enter your OpenWeatherMap API key here
        String apiKey = "e0417d19013a1482055a96e2dd22ebd5";

        // Get user input for city and day of the week
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //chatbot.chatReply();

        System.out.print("Enter city name: ");
        String city = reader.readLine();
        System.out.print("Enter day of the week: ");
        String day = reader.readLine();

        // Convert day of the week to corresponding OpenWeatherMap API parameter
        int dayParam = -1;
        switch (day.toLowerCase()){
            case "monday":
                dayParam = 1;
                break;
            case "tuesday":
                dayParam = 2;
                break;
            case "wednesday":
                dayParam = 3;
                break;
            case "thursday":
                dayParam = 4;
                break;
            case "friday":
                dayParam = 5;
                break;
            case "saturday":
                dayParam = 6;
                break;
            case "sunday":
                dayParam = 7;
                break;
            default:
                System.out.println("Invalid day of the week entered. Please try again.");
                System.exit(0);
        }

        // Get weather data from OpenWeatherMap API
        URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?q=" + city + "&appid=" + apiKey);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

// Find weather data for specified day of the week
        String[] lines = response.toString().split("\n");
        String weather = null;
        double temperature = 0.0;
        double windSpeedMph = 0.0;
        double precipitation = 0.0;
        double humidity = 0.0;

        for (String line : lines) {
            if (line.contains("\"dt_txt\"") && line.contains(Integer.toString(dayParam))) {
                int startIndex = line.indexOf("\"weather\":") + 12;
                int endIndex = line.indexOf("}],");
                weather = line.substring(startIndex, endIndex);
                startIndex = line.indexOf("\"temp\":") + 7;
                endIndex = line.indexOf(",\"feels_like\"");
                if (endIndex < 0) {
                    endIndex = line.length();
                }
                temperature = Double.parseDouble(line.substring(startIndex, endIndex)) - 273.15; // Convert temperature from Kelvin to Celsius

                // Get the precipitation
                startIndex = line.indexOf("pop:") + 4;
                endIndex = line.indexOf(",sys:");
                if (endIndex < 0 || endIndex >= line.length()) {
                    endIndex = line.length();
                }
                precipitation = Double.parseDouble(line.substring(startIndex, endIndex));

                // Get the humidity
                startIndex = line.indexOf("humidity:") + 9;
                endIndex = line.indexOf(",temp_kf:");
                if (endIndex < 0 || endIndex >= line.length()) {
                    endIndex = line.length();
                }
                humidity = Double.parseDouble(line.substring(startIndex, endIndex));

                break;
            }
        }



        if (weather != null) {
            System.out.printf("On %s, the weather in %s will be with a temperature of %.2f Celsius.\n", day, city, temperature);
            //suggestClothing(temperature);
            System.out.println(suggestClothing(temperature,windSpeedMph,precipitation,humidity));
        } else {
            System.out.printf("No weather data available for %s in %s.\n", day, city);
        }
    }

    public static String suggestClothing(double temperatureCelsius, double windSpeedMph, double precipitation, double humidity) {
        String clothing;

        if (temperatureCelsius > 30.0) {
            clothing = "Light, breathable clothing such as cotton or linen, shorts, skirts, and dresses are recommended. It is also important to wear sunscreen and a hat to protect yourself from the strong sun.";
        } else if (temperatureCelsius > 20.0) {
            if (windSpeedMph > 15.0 || precipitation > 0.3 || humidity > 70.0) {
                clothing = "It is recommended to wear a rain jacket or waterproof gear to protect against the wind and rain. Additionally, wear layers to adjust for changing weather conditions.";
            } else {
                clothing = "Lightweight or windproof/thermal running pants, depending on the wind-chill. On top, wear a light long sleeve base layer paired with a windbreaker. Gloves or mittens and a hat are essential, and thermal socks may be needed to keep toes warm.";
            }
        } else if (temperatureCelsius > 10.0) {
            if (windSpeedMph > 10.0 || precipitation > 0.1 || humidity > 80.0) {
                clothing = "A light rain jacket or waterproof gear is recommended. Additionally, wear layers to adjust for changing weather conditions.";
            } else {
                clothing = "You can afford to pull off your trendy minimal outerwear like a parka, biker jacket or leather jacket. You can also get away with wearing your favorite shirt and jeans or dress combo, so long as you layer it with some form of outerwear.";
            }
        } else if (temperatureCelsius > 0.0) {
            if (windSpeedMph > 5.0 || precipitation > 0.05 || humidity > 90.0) {
                clothing = "It is recommended to wear a warm winter jacket, gloves, a hat, and a scarf to protect against the cold and wind. Additionally, wear layers to adjust for changing weather conditions.";
            } else {
                clothing = "As temperatures drop into the single digits, forget your lightweight tops; instead, pack in thicker sweaters, jumpers, and turtlenecks to keep you extra warm underneath your coat. Choosing the right fabrics (like knits, cashmere, wool) is also as important as what you layer.";
            }
        } else {
            clothing = "It is important to cover all bases and consider the head, hands, and feet. Wear gloves and socks that provide ample warmth, and make sure to keep the face and ears warm with hats, scarves, or even ski masks.";
        }
        return clothing;
    }

    public static boolean checkCity(String cityName) {
        String apiKey = "e0417d19013a1482055a96e2dd22ebd5";

        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + apiKey;

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    public static HttpURLConnection getConnection (String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }
}



