package utils;

import models.Reading;

import java.util.List;

public class StationAnalytics {

    public static double getMinTemp (List<Reading> readings) {
        Reading minTemp = null;
        if (readings.size() > 0) {
            minTemp = readings.get(0);
            for (Reading reading : readings) {
                if (reading.temperature < minTemp.temperature) {
                    minTemp = reading;
                }
            }
        }
        return minTemp.temperature;
    }

 /*   public static Reading getMaxTemp(List<Reading> readings) {
        Reading maxTemp = null;
        if (readings.size() > 0) {
            maxTemp = readings.get(0);
            for (Reading reading : readings) {
                if (reading.temperature > maxTemp.temperature) {
                    maxTemp = reading;
                }
            }
        }
        return maxTemp.temperature;
    }*/
}

