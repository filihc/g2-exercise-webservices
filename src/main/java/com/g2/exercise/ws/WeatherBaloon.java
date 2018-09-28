package com.g2.exercise.ws;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.g2.exercise.ws.exception.BusinessException;
import com.g2.exercise.ws.model.BalloonRecord;
import com.g2.exercise.ws.model.BalloonResult;
import com.g2.exercise.ws.util.G2Utils;

public class WeatherBaloon {
	
	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(WeatherBaloon.class);
	
	/**
	 * file name
	 */
	private String inFileName;
	
	/**
	 * file name
	 */
	private String outFileName;
	
	/**
	 * out observatory
	 */
	private String outObservatory;
	
	
	public WeatherBaloon(String inFileName, String outFileName, String outObservatory) {
		this.inFileName = inFileName;
		this.outFileName = outFileName;
		this.outObservatory = outObservatory;
	}

	/**
	 * Process data from Weather Balloon 
	 * @return result BalloonResult
	 */
	public BalloonResult processWeatherBalloon() {
		
		BalloonResult result = null; 
		LineIterator it = null;
		BalloonRecord br = null;
		List<BalloonRecord> lstRecords = new ArrayList<>();
		Integer minTemp = null;
		Integer maxTemp = null;
		Double meanTemp = 0.0;
		
		try {
			it = FileUtils.lineIterator(new File(inFileName), "UTF-8");
			try {
			    while (it.hasNext()) {
			        String line = it.nextLine();
			        logger.debug("read: [{}]", line);
			        try {
						br = createBaloonRecord(line);
						//minTemp
						minTemp = (br.getTemperature() == null) ?
								br.getTemperature() :
								(minTemp != null && minTemp < br.getTemperature()) ?
										minTemp : br.getTemperature();
						//maxTemp
						maxTemp = (br.getTemperature() == null) ?
								br.getTemperature() :
								(maxTemp != null && maxTemp > br.getTemperature()) ?
										maxTemp : br.getTemperature();
						//meanTemp
						meanTemp += br.getTemperature();
						lstRecords.add(br);
					} catch (BusinessException e) {
						logger.debug(e.getMessage());
					}
			    }
			    //meanTemp
			    meanTemp = meanTemp / lstRecords.size();
			    // write file
			    result = createResultFile(lstRecords);
			    result.setMaxTemp(maxTemp);
			    result.setMinTemp(minTemp);
			    result.setMeanTemp(meanTemp);
			} finally {
			    it.close();
			}
		} catch (IOException e1) {
			logger.error("{}", e1);
		} catch (BusinessException e) {
			logger.debug("{}", e);
		}
		
		if(result != null) {
			logger.info("The minimum temperature: {}", result.getMinTemp());
			logger.info("The maximum temperature: {}", result.getMaxTemp());
			logger.info("The mean temperature: {}", result.getMeanTemp());
			logger.info("The number of observations from each observatory: {}", result.getMapObserv().toString());
			logger.info("The total distance travelled: {}", result.getDistanceTraveled());
		} 
		return result;
	}
	
	
	private BalloonResult createResultFile(List<BalloonRecord> lstRecords) throws BusinessException {
		lstRecords.sort(Comparator.comparing(BalloonRecord::getTimestamp));
		return G2Utils.createFileFormList(outFileName, lstRecords);
	}

	/**
	 * Create a new baloon record
	 * @param line
	 * @return baloonRecord
	 * @throws BusinessException 
	 */
	private BalloonRecord createBaloonRecord(String line) throws BusinessException {
		BalloonRecord br = new BalloonRecord();
		String []record = line.split("\\|");
		if(record.length != 4) {
			throw new BusinessException("Input Pattern does not match: " + line);
		}
		String timestamp = record[0];
		String location = record[1].replaceAll("\"", "");
		String temperature = record[2];
		String observatory = record[3];
		try{
			br.setTimestamp(G2Utils.getCalendar(timestamp));
			br.setxLocation(getXLocation(location, observatory));
			br.setyLocation(getYLocation(location, observatory));
			br.setLocation(
					br.getxLocation() 
					+ ","
					+ br.getyLocation() 
					);
			br.setTemperature(getTemperature(temperature, observatory));
			br.setInObservatory(observatory);
			br.setObservatory(outObservatory);
		} catch (DateTimeParseException e) {
			throw new BusinessException("Invalid datetime: " + timestamp);
		}
		logger.debug("write: [{}]", br);
		return br;
	}

	/**
	 * 
	 * @param location
	 * @param observatory
	 * @return
	 * @throws BusinessException
	 */
	private Integer getXLocation(String location, String observatory) throws BusinessException {
		Integer x = null;
		try {
			x = getDistanceMeasurement(
					new Integer(location.substring(0, location.indexOf(","))),
					observatory);
		} catch (Exception e) {
			throw new BusinessException("Invalid location X: " + location);
		}
		return x;
	}
	
	
	private Integer getYLocation(String location, String observatory) throws BusinessException {
		Integer y = null;
		try {
			y = getDistanceMeasurement(
					new Integer(location.substring(location.indexOf(",") + 1)),
					observatory);
		} catch (Exception e) {
			throw new BusinessException("Invalid location Y: " + location);
		}
		return y;
	}
	
	/**
	 * Get temperature form observatory and temperature 
	 * @param temperature
	 * @param observatory unit of measurement 
	 * @return
	 * @throws BusinessException
	 */
	private Integer getTemperature(String temperature, String observatory) throws BusinessException {
		Integer t = null;
		try {
			if(outObservatory.equals(observatory)) {
				t = new Integer(temperature);
			} else {
				switch(outObservatory) {
				case G2Utils.OBSERVATORY_AU:
					if(observatory.equals(G2Utils.OBSERVATORY_US)) {
						t = G2Utils.getCelsiusFromFahrenheit(new Double(temperature)).intValue();
					} else {
						t = G2Utils.getCelsiusFromKelvin(new Double(temperature)).intValue();
					}
					break;
				case G2Utils.OBSERVATORY_US:
					if(observatory.equals(G2Utils.OBSERVATORY_AU)) {
						t = G2Utils.getFahrenheitFromCelsius(new Double(temperature)).intValue();
					} else {
						t = G2Utils.getFahrenheitFromKelvin(new Double(temperature)).intValue();
					}
					break;
				case G2Utils.OBSERVATORY_FR:
					if(observatory.equals(G2Utils.OBSERVATORY_AU)) {
						t = G2Utils.getKelvinFromCelsius(new Double(temperature)).intValue();
					} else if(observatory.equals(G2Utils.OBSERVATORY_US)) {
						t = G2Utils.getKelvinFromFahrenheit(new Double(temperature)).intValue();
					} else {
						t = new Integer(temperature);
					}
					break;
				default:
					if(observatory.equals(G2Utils.OBSERVATORY_AU)) {
						t = G2Utils.getKelvinFromCelsius(new Double(temperature)).intValue();
					} else if(observatory.equals(G2Utils.OBSERVATORY_US)) {
						t = G2Utils.getKelvinFromFahrenheit(new Double(temperature)).intValue();
					} else {
						t = new Integer(temperature);
					}
					break;
				}
			}
			
		} catch (Exception e) {
			throw new BusinessException("Invalid temperature: " + temperature);
		}
		return t;
	}
	
	/**
	 * Get measurement from observatory and number
	 * @param number
	 * @param observatory
	 * @return
	 * @throws BusinessException 
	 */
	private Integer getDistanceMeasurement(Integer number, String observatory) {
		Integer n = null;
		
		if(outObservatory.equals(observatory)) {
			n = new Integer(number);
		} else {
			switch(outObservatory) {
			case G2Utils.OBSERVATORY_AU:
				if(observatory.equals(G2Utils.OBSERVATORY_US)) {
					n = G2Utils.getKmFromMiles(number);
				} else if(observatory.equals(G2Utils.OBSERVATORY_FR)) {
					n = G2Utils.getKMFromMeters(number);
				} else {
					n = number;
				}
				break;
			case G2Utils.OBSERVATORY_US:
				if(observatory.equals(G2Utils.OBSERVATORY_FR)) {
					n = G2Utils.getMilesFromMeters(number);
				} else {
					n = G2Utils.getMilesFromKM(number);
				}
				break;
			case G2Utils.OBSERVATORY_FR:
				if(observatory.equals(G2Utils.OBSERVATORY_US)) {
					n = G2Utils.getMetersFromMiles(number);
				} else {
					n = G2Utils.getMetersFromKM(number);
				}
				break;
			default:
				if(observatory.equals(G2Utils.OBSERVATORY_US)) {
					n = G2Utils.getKmFromMiles(number);
				} else if(observatory.equals(G2Utils.OBSERVATORY_FR)) {
					n = G2Utils.getKMFromMeters(number);
				} else {
					n = new Integer(number);
				}
				break;
			}
		}
		
		return n;
	}
	
	public static void main(String []args) {
		
		if (args != null && args.length >= 3) {
			String inFileName = args[0];
			String outFileName = args[1];
			String outObservatory = args[2];
			
			WeatherBaloon wb = new WeatherBaloon(inFileName, outFileName, outObservatory);
			BalloonResult br = null;
			Long start = System.currentTimeMillis();
			Long end = 0L;
			
			// when
			logger.info("Start: {}", start);
			br = wb.processWeatherBalloon();
			end = System.currentTimeMillis();
			logger.info("End: {}", end);
			logger.info("Time: {} (sec)", (((end-start)/1000)%60));
		} else {
			logger.error("Number of inputs does not match");
		}
		
		
		
	}
}
