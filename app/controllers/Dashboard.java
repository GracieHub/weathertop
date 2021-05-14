package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Station;
import models.Reading;
import play.Logger;
import play.mvc.Controller;

import static models.Station.codeToString;
import static play.Logger.*;

public class Dashboard extends Controller {
  public static void index() {
    info("Rendering Dashboard");

  //  List<Station> stations = Station.findAll();
  //  render("dashboard.html", stations);

    List<Station> stations = Station.findAll(); //finding all Station from the db
    for (Station s : stations) {
      if (s.readings.size() > 0) {
        s.setWeatherReport(codeToString(s.readings.get(s.readings.size() - 1).code));
      }
    }
    render("dashboard.html", stations);
  }

  public static void deleteStation(Long id) {
    Station station = Station.findById(id);
    info("Removing" + station.name);
    station.delete();
    redirect("/dashboard");
  }

  public static void addStation(String name) {
    Station station = new Station(name);
    info("Adding a new station called " + name);
    station.save();
    redirect("/dashboard");
  }
}

