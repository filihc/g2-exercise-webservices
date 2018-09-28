package com.g2.exercise.ws.model;

import java.util.Map;

public class BalloonResult {
	
	/**
	 * The minimum temperature
	 */
	private Integer minTemp;
	
	/**
	 * The maximum temperature
	 */
	private Integer maxTemp;
	
	/**
	 * The mean temperature
	 */
	private Double meanTemp;
	
	/**
	 * The number of observations from each observatory
	 */
	private Map<String, Long> mapObserv;
	
	/**
	 * The total distance travelled
	 */
	private Double distanceTraveled;

	/**
	 * @return the minTemp
	 */
	public Integer getMinTemp() {
		return minTemp;
	}

	/**
	 * @param minTemp the minTemp to set
	 */
	public void setMinTemp(Integer minTemp) {
		this.minTemp = minTemp;
	}

	/**
	 * @return the maxTemp
	 */
	public Integer getMaxTemp() {
		return maxTemp;
	}

	/**
	 * @param maxTemp the maxTemp to set
	 */
	public void setMaxTemp(Integer maxTemp) {
		this.maxTemp = maxTemp;
	}

	/**
	 * @return the meanTemp
	 */
	public Double getMeanTemp() {
		return meanTemp;
	}

	/**
	 * @param meanTemp the meanTemp to set
	 */
	public void setMeanTemp(Double meanTemp) {
		this.meanTemp = meanTemp;
	}

	/**
	 * @return the mapObserv
	 */
	public Map<String, Long> getMapObserv() {
		return mapObserv;
	}

	/**
	 * @param mapObserv the mapObserv to set
	 */
	public void setMapObserv(Map<String, Long> mapObserv) {
		this.mapObserv = mapObserv;
	}

	/**
	 * @return the distanceTraveled
	 */
	public Double getDistanceTraveled() {
		return distanceTraveled;
	}

	/**
	 * @param distanceTraveled the distanceTraveled to set
	 */
	public void setDistanceTraveled(Double distanceTraveled) {
		this.distanceTraveled = distanceTraveled;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BalloonResult [minTemp=" + minTemp + ", maxTemp=" + maxTemp + ", meanTemp=" + meanTemp + ", mapObserv="
				+ mapObserv + ", distanceTraveled=" + distanceTraveled + "]";
	}
	
	
	
}
