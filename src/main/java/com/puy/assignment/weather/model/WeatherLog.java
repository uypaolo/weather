package com.puy.assignment.weather.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="weatherlog")
public class WeatherLog {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@JsonIgnore
	private int id;
	
	@Column(name="responseid", unique=true)
	@JsonIgnore
	private String responseId;
	
	private String location;
	
	@Column(name="actualweather")
	private String actualWeather;
	
	private String temperature;
	
	@Column(name="dtimeinserted")
	@JsonIgnore
	private Date dtimeInserted;
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getActualWeather() {
		return actualWeather;
	}
	public void setActualWeather(String actualWeather) {
		this.actualWeather = actualWeather;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getResponseId() {
		return responseId;
	}
	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}
	public Date getDtimeInserted() {
		return dtimeInserted;
	}
	public void setDtimeInserted(Date dtimeInserted) {
		this.dtimeInserted = dtimeInserted;
	}
	@Override
	public String toString() {
		return "WeatherLog [id=" + id + ", responseId=" + responseId + ", location=" + location + ", actualWeather="
				+ actualWeather + ", temperature=" + temperature + ", dtimeInserted=" + dtimeInserted + "]";
	}
}
