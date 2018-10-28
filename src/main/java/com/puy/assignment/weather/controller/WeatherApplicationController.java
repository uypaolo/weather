package com.puy.assignment.weather.controller;


import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.puy.assignment.weather.model.OpenWeatherBean;
import com.puy.assignment.weather.model.WeatherLog;
import com.puy.assignment.weather.repository.WeatherLogRepository;
import com.puy.assignment.weather.utility.WeatherLogUtility;

import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@PropertySource("classpath:openweather.properties")
public class WeatherApplicationController {
	@Value("${api-url}")
	private String OPEN_WEATHER_API_URL;
	
	@Value("${appid}")
	private String OPEN_WEATHER_APPID;

	@Value("${current-weather-by-city-name}")
	private String OPEN_WEATHER_CURRENT_BY_CITY_NAME;
	
	@Value("${appid-equals}")
	private String OPEN_WEATHER_APPID_EQUALS;
	
	private final String RESPONSE_ID_PREFIX = "RESPONSE-";
	
	@Autowired
	private WeatherLogRepository weatherLogRepository;
	
	@RequestMapping(path="/weather", method=RequestMethod.GET)
    public String weatherAll() {
		RestTemplate restTemplate = new RestTemplate();
		OpenWeatherBean openWeatherLondon = restTemplate.getForObject(OPEN_WEATHER_API_URL+OPEN_WEATHER_CURRENT_BY_CITY_NAME+"London"+OPEN_WEATHER_APPID_EQUALS+OPEN_WEATHER_APPID, OpenWeatherBean.class);
		OpenWeatherBean openWeatherPrague = restTemplate.getForObject(OPEN_WEATHER_API_URL+OPEN_WEATHER_CURRENT_BY_CITY_NAME+"Prague"+OPEN_WEATHER_APPID_EQUALS+OPEN_WEATHER_APPID, OpenWeatherBean.class);
		OpenWeatherBean openWeatherSanFrancisco = restTemplate.getForObject(OPEN_WEATHER_API_URL+OPEN_WEATHER_CURRENT_BY_CITY_NAME+"San+Francisco"+OPEN_WEATHER_APPID_EQUALS+OPEN_WEATHER_APPID, OpenWeatherBean.class);

		WeatherLog logLondon = processToSave(openWeatherLondon);
		WeatherLog logPrague = processToSave(openWeatherPrague);
		WeatherLog logSanFrancisco = processToSave(openWeatherSanFrancisco);
		
		ObjectMapper mapper = new ObjectMapper();
		WeatherLog[] weatherArray = {logLondon,logPrague,logSanFrancisco};
		try {
			return mapper.writeValueAsString(weatherArray);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return "";
    }
	
	private WeatherLog processToSave(OpenWeatherBean openWeatherBean) {
		WeatherLog log = WeatherLogUtility.generateWeatherLog(openWeatherBean);
		List<WeatherLog> matchLog = weatherLogRepository.findByLocationAndActualWeatherAndTemperature(log.getLocation(), log.getActualWeather(), log.getTemperature());
		
		if(matchLog.isEmpty()) {
			if(weatherLogRepository.count() >= 5) {
				System.out.println("GREATER THAN 5");
				List<WeatherLog> allLogs = weatherLogRepository.findAllByOrderByDtimeInsertedAsc();
				weatherLogRepository.delete(allLogs.get(0));
				weatherLogRepository.save(log);
				log.setResponseId(RESPONSE_ID_PREFIX + log.getId());
				weatherLogRepository.save(log);
			} else {
				System.out.println("SAVING");
				weatherLogRepository.save(log);
				log.setResponseId(RESPONSE_ID_PREFIX + log.getId());
				weatherLogRepository.save(log);
			}
		} else {
			System.out.println("DO NOTHING");
			System.out.println(matchLog);
		}
		
		return log;
	}
	
}
