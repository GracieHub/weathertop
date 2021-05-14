package models;

import javax.persistence.Entity;

import play.db.jpa.Model;
@Entity
public class Reading extends Model
{
  public int code;
  public float temperature;
  public double windSpeed;
  public int pressure;
  public double windDirection;
  
  public Reading(int code, float temperature, double windSpeed, int pressure, int windDirection)
  {
    this.code = code;
    this.temperature = temperature;
    this.windSpeed = windSpeed;
    this.pressure = pressure;
    this.windDirection = windDirection;
  }

  public int getCode(){                       //getters
    return this.code;
  }

  public float getTemperature(){
    return this.temperature;
  }

  public double getWindSpeed(){
    return this.windSpeed;
  }

  public int getPressure(){
    return this.pressure;
  }

  public void setCode(int code){          //setters
    this.code = code;
  }

  public void setTemperature(float temperature){
    this.temperature = temperature;
  }

  public void setWindSpeed(double windSpeed){
    this.windSpeed = windSpeed;
  }

  public void setPressure(int pressure){
    this.pressure = pressure;
  }
}
