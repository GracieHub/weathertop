package controllers;

import java.util.List;

import models.Member;
import models.Station;
import models.Reading;
import play.Logger;
import play.mvc.Controller;
import utils.StationAnalytics;
import java.util.Date;


public class StationCtrl extends Controller
{
    public static void index(Long id)
    {
        Station station = Station.findById(id);
        Logger.info ("Station id = " + id);

        Member member = Accounts.getLoggedInMember();
        List<Station> stations = member.stations;

        for (Station s : stations) {
            if (s.readings.size() > 0) {
                s.setWeatherReport(s.codeToString(s.readings.get(s.readings.size() - 1).code));
                s.setWeatherIcon(s.weatherIcon(s.readings.get(s.readings.size() -1).code));
                s.minimumTemp = StationAnalytics.getMinTemp(s.readings).temperature;
                s.maximumTemp = StationAnalytics.getMaxTemp(s.readings).temperature;
                s.minimumWind = StationAnalytics.getMinWind(s.readings).windSpeed;
                s.maximumWind = StationAnalytics.getMaxWind(s.readings).windSpeed;
                s.maximumPressure = StationAnalytics.getMaxPressure(s.readings).pressure;
                s.minimumPressure = StationAnalytics.getMinPressure(s.readings).pressure;
            }
        }
        render("station.html", member, station);
    }

    public static void deletereading(Long id, Long readingid)
    {
        Station station = Station.findById(id);
        Reading reading = Reading.findById(readingid);
        Logger.info ("Removing " + reading.code);
        station.readings.remove(reading);
        station.save();
        reading.delete();
        render("station.html", station);
    }

    public static void addReading(Long id, int code, double temperature, double windSpeed, int pressure, int windDirection)
    {
        Station station = Station.findById(id);
        Reading newReading = new Reading(new Date(), code, temperature, windSpeed, pressure, windDirection);
        station.readings.add(newReading);
        station.save();
        redirect ("/stations/" + id);
    }

}
