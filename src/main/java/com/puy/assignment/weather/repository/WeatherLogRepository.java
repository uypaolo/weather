package com.puy.assignment.weather.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.puy.assignment.weather.model.WeatherLog;

public interface WeatherLogRepository extends CrudRepository<WeatherLog, Integer> {
	List<WeatherLog> findByLocationAndActualWeatherAndTemperature(String location, String actualWeather, String temperature);
	List<WeatherLog> findAllByOrderByDtimeInsertedAsc();
}
