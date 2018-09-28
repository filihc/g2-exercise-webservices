package com.g2.exercise.ws.test;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.g2.exercise.ws.WeatherBaloon;
import com.g2.exercise.ws.model.BalloonResult;

public class WeatherBalloonTest {
	
	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(WeatherBalloonTest.class);
	
	
	@Test
	public void testProcessWeatherBaloonFile1() {
		// given 
		String inFileName = "src/test/resources/testdata/input/weatherBalloonInputs.csv";
		String outFileName = "src/test/resources/testdata/output/weatherBalloonOutput.csv";
		String outObservatory = "AU";
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
		
		// then
		Assert.assertNotNull(br);
		Assert.assertEquals(new Integer("-238"), br.getMinTemp());
		Assert.assertEquals(new Integer("3290"), br.getMaxTemp());
		Assert.assertEquals(new Integer("135").intValue(), br.getMeanTemp().intValue());
	}

	

}
