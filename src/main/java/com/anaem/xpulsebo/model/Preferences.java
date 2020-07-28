package com.anaem.xpulsebo.model;

public class Preferences {
	private String currencyCode;
	private String timeFrame;
	
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getTimeFrame() {
		return timeFrame;
	}
	public void setTimeFrame(String timeFrame) {
		this.timeFrame = timeFrame;
	}
	@Override
	public String toString() {
		return "Preferences [currencyCode=" + currencyCode + ", timeFrame=" + timeFrame + "]";
	}
	
}
