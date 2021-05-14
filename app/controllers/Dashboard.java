package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Member;
import models.Station;
import models.Reading;
import play.Logger;
import play.mvc.Controller;

import static play.Logger.*;

public class Dashboard extends Controller {
  public static void index()
    {
      Logger.info("Rendering Dashboard");

      Member member = Accounts.getLoggedInMember();
      List<Station> stations = member.stations;
      render("dashboard.html", member, stations);
    }

  public static void deleteStation(Long id) {
    Logger.info("Deleting a Playlist");
    Member member = Accounts.getLoggedInMember();
    Station playlist = Station.findById(id);
    member.stations.remove(playlist);
    member.save();
    playlist.delete();
    redirect ("/dashboard");
  }

  public static void addStation(String name)
    {
      Logger.info("Adding a Playlist");
      Member member = Accounts.getLoggedInMember();
      Station station = new Station (name);
      member.stations.add(station);
      member.save();
      redirect ("/dashboard");
    }
}

