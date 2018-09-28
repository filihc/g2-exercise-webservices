package com.g2.exercise.ws.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.g2.exercise.ws.exception.BusinessException;
import com.g2.exercise.ws.model.BalloonRecord;

public class WriteRecords {
	
	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(WriteRecords.class);
	
	
	public static void processList(String fileName, List<BalloonRecord> lstRecords) 
			throws BusinessException {
		File outputFile = new File(fileName);

	    try (FileChannel fileChannel = 
	    		new FileOutputStream(outputFile).getChannel()) {
	    	for (BalloonRecord br: lstRecords) {
				byte[] byteData = br.toString().getBytes("UTF-8");
				ByteBuffer buffer = ByteBuffer.wrap(byteData);
				fileChannel.write(buffer);
	    	}
	    	fileChannel.close();
	    } catch (IOException e1) {
	      throw new BusinessException(e1.getMessage());
	    }
	}

}
