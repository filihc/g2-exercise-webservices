package com.g2.exercise.ws.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.g2.exercise.ws.exception.BusinessException;
import com.g2.exercise.ws.model.BalloonRecord;
import com.g2.exercise.ws.model.BalloonResult;

public class G2Utils {
	
	/**
	 * AU Observatory
	 */
	public static final String OBSERVATORY_AU = "AU";
	
	/**
	 * US Observatory
	 */
	public static final String OBSERVATORY_US = "US";
	
	/**
	 * FR Observatory
	 */
	
	public static final String OBSERVATORY_FR = "FR";
	
	/**
	 * OT Observatory
	 */
	public static final String OBSERVATORY_OT = "OT";	
	
	/**
	 * Create File from a list 
	 * @param fileName name of file
	 * @param lstRecords list of records
	 * @throws BusinessException throw exception 
	 */
	public static BalloonResult createFileFormList(String fileName, List<BalloonRecord> lstRecords) 
			throws BusinessException {
		BalloonResult result = new BalloonResult();
		File outputFile = new File(fileName);
		Long auCount = 0L;
		Long usCount = 0L;
		Long frCount = 0L;
		Long otCount = 0L;
		Double distanceTravelled = 0.0;
		Integer x1 = 0;
		Integer x2 = 0;
		Integer y1 = 0;
		Integer y2 = 0;
		
		try (FileChannel fileChannel = 
	    		new FileOutputStream(outputFile).getChannel()) {
	    	for (BalloonRecord br: lstRecords) {
				byte[] byteData = (br.toString() + "\n").getBytes("UTF-8");
				ByteBuffer buffer = ByteBuffer.wrap(byteData);
				fileChannel.write(buffer);
				switch(br.getInObservatory()) {
					case OBSERVATORY_AU:
						auCount++;
						break;
					case OBSERVATORY_US:
						usCount++;
						break;
					case OBSERVATORY_FR:
						frCount++;
						break;
					default:
						otCount++;
						break;
				}
				// distanceTravelled
				x2 = br.getxLocation();
				y2 = br.getyLocation();
				distanceTravelled = distanceTravelled + getDistance(x1, x2, y1, y2);
				x1 = br.getxLocation();
				y1 = br.getyLocation();
	    	}
	    	fileChannel.close();
	    } catch (IOException e1) {
	      throw new BusinessException(e1.getMessage());
	    }
		Map<String, Long> mapObser = new HashMap<>();
		mapObser.put(OBSERVATORY_AU, auCount);
		mapObser.put(OBSERVATORY_US, usCount);
		mapObser.put(OBSERVATORY_FR, frCount);
		mapObser.put(OBSERVATORY_OT, otCount);
		
		result.setDistanceTraveled(distanceTravelled);
		result.setMapObserv(mapObser);
		
	    return result;
	}

	
	/**
     * Datetime pattern yyyy-MM-ddTHH:mm
     */
    public static final String DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm";
    
    
	/**
     * Formatter with yyyy-MM-ddTHH:mm structure.
     */
    public static final DateTimeFormatter DATETIME_FORMATER = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
    
    /**
     * Get distance between 2 points
     * @param x1
     * @param x2
     * @param y1
     * @param y2
     * @return
     */
    public static Double getDistance(Integer x1, Integer x2, Integer y1, Integer y2) {
    	Double distance = 0.0;
    	distance = Math.sqrt(
    			Math.pow((x2-x1),2) + Math.pow((y2-y1),2));
    	return distance;
    }
    
	/**
     * Method for convert string to localdatetime.
     * @param stringLocaldatetime string to convert.
     * @return localdatime string converted to LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(final String stringLocaldatetime) {
    	return LocalDateTime.parse(stringLocaldatetime, DATETIME_FORMATER);
    }
    
    /**
     * Create Calendar from string timestamp
     * @param timestamp date and time in expected format
     * @return cal Calendar Object
     */
    public static Calendar getCalendar(String timestamp) {
    	ZoneId zoneId = ZoneId.of("UTC");
		Calendar cal = GregorianCalendar.from(
    			ZonedDateTime.of(G2Utils.toLocalDateTime(timestamp), zoneId));
    	return cal;
    }
    
    /**
     * Convert Fahrenheit to Celsius
     * @param f grados Fahrenheit
     * @return Celsius 
     */
    public static Double getCelsiusFromFahrenheit(Double f) {
    	return ((f-32)/1.8);
    }
    
    /**
     * Convert Celsius to Fahrenheit
     * @param c grados celsius
     * @return Fahrenheit
     */
    public static Double getFahrenheitFromCelsius(Double c) {
    	return (c * 1.8 + 32);
    }
    
    /**
     * Convert Fahrenheit to kelvin
     * @param f grados Fahrenheit
     * @return 
     */
    public static Double getKelvinFromFahrenheit(Double f) {
    	return ( f + 459.67) / 1.8;
    }
    
    /**
     * Convert kelvin to Fahrenheit
     * @param k grados Kelvin
     * @return Fahrenheit
     */
    public static Double getFahrenheitFromKelvin(Double k) {
    	return (k * 1.8) - 459.67;
    }


    /**
     * Convert Fahrenheit to kelvin
     * @param f grados Fahrenheit
     * @return 
     */
    public static Double getKelvinFromCelsius(Double c) {
    	return (c + 273.15);
    }
    
    /**
     * Convert kelvin to Fahrenheit
     * @param k grados Kelvin
     * @return Fahrenheit
     */
    public static Double getCelsiusFromKelvin(Double k) {
    	return (k-273.15);
    }
    
    /**
     * Convert miles to KM
     * @param miles
     * @return km
     */
    public static Integer getKmFromMiles(Integer miles) {
    	return new Double(miles / 1.609344).intValue();
    }
    
    /**
     * Convert KM to miles
     * @param km kilometers
     * @return miles
     */
    public static Integer getMilesFromKM(Integer km) {
    	return new Double(km * 1.609344).intValue();
    }
    
    /**
     * Convert KM to meters
     * @param km
     * @return meters
     */
    public static Integer getMetersFromKM(Integer km) {
    	return new Double(km * 1000).intValue();
    }
    
    /**
     * Convert KM to meters
     * @param meters 
     * @return miles
     */
    public static Integer getKMFromMeters(Integer meters) {
    	return new Double(meters / 1000).intValue();
    }
    
    /**
     * Convert miles to meters
     * @param km
     * @return meters
     */
    public static Integer getMetersFromMiles(Integer miles) {
    	return new Double(getKmFromMiles(miles) * 1000).intValue();
    }
    
    /**
     * Convert meters to miles
     * @param km kilometers
     * @return miles
     */
    public static Integer getMilesFromMeters(Integer meters) {
    	return getMilesFromKM(getKMFromMeters(meters));
    }
}
