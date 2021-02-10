/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tvmediastuff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import twitter4j.JSONArray;
import twitter4j.JSONObject;

/**
 *
 * @author cafen
 */
public class weather {
    //variable initialization
    private static String weather, temp, feelsLike, humid;
    private static String API_KEY = "330c601446bd31496570a2ff076ba09b";
    static String LOCATION = getLocation();

    /**
     * updates the weather using openweathermap.org 
     */
    public static void update() {

        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + LOCATION + "&appid=" + API_KEY + "&units=metric";
        
        try {
            //Mr. R-D's sample code:
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

            //create JSON objects and arrays to find useful info
            JSONObject obj = new JSONObject(result.toString());
            JSONArray arr0 = obj.getJSONArray("weather");

            //find useful info
            weather = arr0.getJSONObject(0).getString("main");
            temp = obj.getJSONObject("main").getString("temp");
            //reformat with degrees celsius
            temp = getTemp() + "\u00B0C";
            feelsLike = obj.getJSONObject("main").getString("feels_like");
            //reformat with degrees celsius
            feelsLike = getFeelsLike() + "\u00B0C";
            humid = obj.getJSONObject("main").getString("humidity");
            humid += "%";
            //System.out.println("HAHA" + result);
            //System.out.println(humid);

        } catch (IOException e) {
            System.out.println("An error has occured: " + e.getMessage());
        }
    }
    
    private static String getLocation() {
        //Config file path
        File config = new File("config.txt");
        // If it dodesnt exist, create it
        if (!config.exists()) {
            try {
                config.createNewFile();
            } catch (IOException ex) {
                System.out.println("An error occured with the config file. Try again.");
            }
        }
        String line = "";
        Scanner scan;
        // Find the path name given by the user in the config file
        try {
            scan = new Scanner(config);
            for (int i = 0; i < 8; i++) {
                line = scan.nextLine();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("An error occured with finding the config flie. Try again.");
        }
        return line;
    }

    /**
     * @return the weather
     */
    public static String getWeather() {
        return weather;
    }

    /**
     * @return the temp
     */
    public static String getTemp() {
        return temp;
    }

    /**
     * @return the how the temperature feels like
     */
    public static String getFeelsLike() {
        return feelsLike;
    }

    /**
     * @return the humidity
     */
    public static String getHumidity() {
        return humid;
    }
}
