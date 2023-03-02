import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherApp {
    // Copy your API-KEY here
    public final static String apiKey = "2fe8472f9bc3496b93a42519230103";
    // TODO: Write main function
    public static void main(String[] args)
    {
        Scanner scannner = new Scanner(System.in);

        System.out.println("Enter the name of city: ");

        String city = scannner.next();

        String json_result = getWeatherData(city);

        if(json_result == null)
        {
            System.out.println("An error has been occured.");
        }
        else
        {
            double temp = getTemperature(json_result);

            System.out.println("The temperature of "+ city + " is: " +  temp);

            int humidity = getHumidity(json_result);

            System.out.println("The humidity of "+ city + " is: " +  humidity);
        }
    }

    /**
     * Retrieves weather data for the specified city.
     *
     * @param city the name of the city for which weather data should be retrieved
     * @return a string representation of the weather data, or null if an error occurred
     */
    public static String getWeatherData(String city) {
        try {
            URL url = new URL("https://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + city);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            InputStream input_stream = conn.getInputStream();
            InputStreamReader input_stream_reader = new InputStreamReader(input_stream);
            BufferedReader reader = new BufferedReader(input_stream_reader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // TODO: Write getTemperature function returns celsius temperature of city by given json string
    public static double getTemperature(String weatherJson){
        double answer;

        JSONObject obj = new JSONObject(weatherJson);

        JSONObject current_json_object = obj.getJSONObject("current");

        answer = current_json_object.getDouble("temp_c");

        return answer;
    }

    // TODO: Write getHumidity function returns humidity percentage of city by given json string
    public static int getHumidity(String weatherJson){
        int answer;

        JSONObject obj = new JSONObject(weatherJson);

        JSONObject current_json_object = obj.getJSONObject("current");

        answer = current_json_object.getInt("humidity");

        return answer;
    }
}
