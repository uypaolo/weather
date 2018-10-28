package com.puy.assignment.weather.utility;

import java.util.Date;
import com.puy.assignment.weather.model.OpenWeatherBean;
import com.puy.assignment.weather.model.WeatherLog;

public class WeatherLogUtility {
	public static WeatherLog generateWeatherLog(OpenWeatherBean openWeather) {
		WeatherLog log = new WeatherLog();
		log.setActualWeather(openWeather.getWeather()[0].getMain());
		log.setLocation(openWeather.getName());
		log.setTemperature(openWeather.getMain().getTemp()+"");
		log.setDtimeInserted(new Date());
		return log;
	}
}
