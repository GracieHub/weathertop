package controllers;

import models.Member;
import models.Station;
import play.Logger;
import play.mvc.Controller;
import utils.StationAnalytics;

import java.util.List;

public class Dashboard extends Controller {
    public static void index() {
        Logger.info("Rendering Dashboard");
        Member member = Accounts.getLoggedInMember();
        List<Station> stations = member.stations;

        for (Station s : stations) {
            if (s.readings.size() > 0) {
                s.setWeatherReport(s.codeToString(s.readings.get(s.readings.size() - 1).code));
                s.setWeatherIcon(s.weatherIcon(s.readings.get(s.readings.size() - 1).code));
                s.minimumTemp = StationAnalytics.getMinTemp(s.readings).temperature;
                s.maximumTemp = StationAnalytics.getMaxTemp(s.readings).temperature;
                s.minimumWind = StationAnalytics.getMinWind(s.readings).windSpeed;
                s.maximumWind = StationAnalytics.getMaxWind(s.readings).windSpeed;
                s.maximumPressure = StationAnalytics.getMaxPressure(s.readings).pressure;
                s.minimumPressure = StationAnalytics.getMinPressure(s.readings).pressure;
            }
        }
        render("dashboard.html", member, stations);

    }

    public static void deleteStation(Long id) {
        Logger.info("Deleting a Station");
        Member member = Accounts.getLoggedInMember();
        Station playlist = Station.findById(id);
        member.stations.remove(playlist);
        member.save();
        playlist.delete();
        redirect("/dashboard");
    }

    public static void addStation(String name, double latitude, double longitude) {
        Member member = Accounts.getLoggedInMember();
        Station station = new Station(name, latitude, longitude);
        station.save();
        member.stations.add(station);
        member.save();
        Logger.info("Adding a Station" + station + "to user" + member.lastname);
        redirect("/dashboard");
    }
}

