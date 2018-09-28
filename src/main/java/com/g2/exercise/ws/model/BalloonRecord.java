package com.g2.exercise.ws.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.g2.exercise.ws.util.G2Utils;

public class BalloonRecord {
	/**
	 * timestamp is yyyy-MM-ddThh:mm in UTC
	 */
	private Calendar timestamp;
	
	/**
	 * location is a co-ordinate x,y. And x, and y are natural numbers in observatory specific units
	 */
	private String location;
	
	/**
	 * y is natural numbers in observatory specific units
	 */
	private Integer xLocation;
	
	/**
	 * y is natural numbers in observatory specific units
	 */
	private Integer yLocation;
	
	/**
	 * temperature is an integer representing temperature in observatory specific units
	 */
	private Integer temperature;
	
	/**
	 * observatory is a code indicating where the measurements were relayed from
	 */
	private String observatory;
	
	/**
	 * observatory is a code indicating where the measurements were relayed from
	 */
	private String inObservatory;

	/**
	 * @return the timestamp
	 */
	public Calendar getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Calendar timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the xLocation
	 */
	public Integer getxLocation() {
		return xLocation;
	}

	/**
	 * @param xLocation the xLocation to set
	 */
	public void setxLocation(Integer xLocation) {
		this.xLocation = xLocation;
	}

	/**
	 * @return the yLocation
	 */
	public Integer getyLocation() {
		return yLocation;
	}

	/**
	 * @param yLocation the yLocation to set
	 */
	public void setyLocation(Integer yLocation) {
		this.yLocation = yLocation;
	}

	/**
	 * @return the temperature
	 */
	public Integer getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(Integer temperature) {
		this.temperature = temperature;
	}

	/**
	 * @return the observatory
	 */
	public String getObservatory() {
		return observatory;
	}

	/**
	 * @param observatory the observatory to set
	 */
	public void setObservatory(String observatory) {
		this.observatory = observatory;
	}
	
	/**
	 * @return the inObservatory
	 */
	public String getInObservatory() {
		return inObservatory;
	}

	/**
	 * @param inObservatory the inObservatory to set
	 */
	public void setInObservatory(String inObservatory) {
		this.inObservatory = inObservatory;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new SimpleDateFormat(G2Utils.DATETIME_PATTERN).format(timestamp.getTime()) 
				+ "|" + location + "|" 
				+ temperature + "|" 
				+ observatory;
	}
	
	
}
