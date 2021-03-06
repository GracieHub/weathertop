package models;

import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity
public class Station extends Model {
    public String name;
    public double latitude;
    public double longitude;
    @OneToMany(cascade = CascadeType.ALL)
    public List<Reading> readings = new ArrayList<Reading>();
    public String weatherReport;
    public String weatherIcon;
    public double minimumTemp;
    public double maximumTemp;
    public double minimumWind;
    public double maximumWind;
    public double maximumPressure;
    public double minimumPressure;

    public Station(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static String codeToString(int code) {

        switch (code) {
            case 100:
                return "Clear";

            case 200:
                return "Partial Clouds";

            case 300:
                return "Cloudy";

            case 400:
                return "Light Showers";

            case 500:
                return "Heavy Showers";

            case 600:
                return "Rain";

            case 700:
                return "Snow";

            case 800:
                return "Thunder";
        }
        return " ";
    }

    public void setWeatherReport(String weatherReport) {
        this.weatherReport = weatherReport;
    }

    public String latestTemperature() {
        if (this.readings.size() == 0) {
            return "No Reading";
        } else {
            String lastTemp = " ";

            lastTemp = lastTemp + this.readings.get(this.readings.size() - 1).temperature;

            return lastTemp;
        }
    }

    public String latestPressure() {
        if (this.readings.size() == 0) {
            return "No reading";
        } else {
            String lastPressure = "";

            lastPressure = lastPressure + this.readings.get(this.readings.size() - 1).pressure;

            return lastPressure;

        }
    }

    public double celciusToF() {

        if (this.readings.size() == 0) {
            return 0;
        } else {
            double Fahrenheit = 0;

            Fahrenheit = Fahrenheit + this.readings.get(this.readings.size() - 1).temperature * (9 / 5) + 32;

            return Fahrenheit;
        }
    }

    private int calcBeaufort() {
        if (this.readings.size() == 0) {
            return 0;
        }
        double windSpeed = this.readings.get(this.readings.size() - 1).windSpeed;
        if (windSpeed <= 1) {
            return 0;
        } else if (windSpeed <= 5) {
            return 1;
        } else if (windSpeed <= 11) {
            return 2;
        } else if (windSpeed <= 19) {
            return 3;
        } else if (windSpeed <= 28) {
            return 4;
        } else if (windSpeed <= 38) {
            return 5;
        } else if (windSpeed <= 49) {
            return 6;
        } else if (windSpeed <= 61) {
            return 7;
        } else if (windSpeed <= 74) {
            return 8;
        } else if (windSpeed <= 88) {
            return 9;
        } else if (windSpeed <= 102) {
            return 10;
        } else if (windSpeed <= 117) {
            return 11;
        } else {
            return -1;
        }
    }

    private String windDirectionText() {
        if (this.readings.size() == 0) {
            return "no Reading";
        }
        double windDirection = this.readings.get(this.readings.size() - 1).windDirection;

        if (windDirection >= 11.25 && windDirection <= 33.75)
            return "North North East";
        if (windDirection >= 33.75 && windDirection <= 56.25)
            return "North East";
        if (windDirection >= 56.25 && windDirection <= 78.75)
            return "East North East";
        if (windDirection >= 78.75 && windDirection <= 101.25)
            return "East";
        if (windDirection >= 101.25 && windDirection <= 123.75)
            return "East South East";
        if (windDirection >= 123.75 && windDirection <= 146.25)
            return "South East";
        if (windDirection >= 146.25 && windDirection <= 168.75)
            return "South South East";
        if (windDirection >= 168.75 && windDirection <= 191.25)
            return "South";
        if (windDirection >= 191.25 && windDirection <= 213.75)
            return "South South West";
        if (windDirection >= 213.75 && windDirection <= 236.25)
            return "South West";
        if (windDirection >= 236.25 && windDirection <= 258.75)
            return "West South West";
        if (windDirection >= 258.75 && windDirection <= 281.25)
            return "West";
        if (windDirection >= 281.25 && windDirection <= 303.75)
            return "West North West";
        if (windDirection >= 303.75 && windDirection <= 326.25)
            return "North West";
        if (windDirection >= 326.25 && windDirection <= 348.75)
            return "North North West";
        if (windDirection >= 348.75 && windDirection <= 11.25)
            return "North";

        return "Unknown";
    }

    private String windChill() {
        if (this.readings.size() == 0) {
            return "No Reading";
        }
        double t = this.readings.get(this.readings.size() - 1).temperature;
        double v = this.readings.get(this.readings.size() - 1).windSpeed;

        double wc = 13.12 + 0.6215 * t - 11.37 * (Math.pow(v, 0.16)) + 0.3965 * t * (Math.pow(v, 0.16));
        DecimalFormat decimalformat = new DecimalFormat("0.00");
        return decimalformat.format(wc);
    }

    public String weatherIcon(int code) {

        HashMap<Integer, String> weatherIcons = new HashMap<>();
        weatherIcons.put(100, "sun icon");
        weatherIcons.put(200, "cloud sun icon");
        weatherIcons.put(300, "cloud icon");
        weatherIcons.put(400, "cloud rain icon");
        weatherIcons.put(500, "cloud showers heavy icon");
        weatherIcons.put(600, "snowflake icon");
        weatherIcons.put(700, "snowflake icon");
        weatherIcons.put(800, "poo storm icon");

        return weatherIcons.get(code);
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

}
