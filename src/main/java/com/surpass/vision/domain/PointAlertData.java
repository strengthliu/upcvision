package com.surpass.vision.domain;

import java.io.Serializable;

import com.surpass.vision.server.Point;

public class PointAlertData extends Point implements Serializable {
	Long alertType;
	Double alertValue;
	Long occuredTime;
	Long duration;
	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	Long alertBeginTime;
	Long alertEndTime;
	Double hihiLimit;
	Double hiLimit;
	Double loloLimit;
	Double loLimit;
	

	public Long getAlertType() {
		return alertType;
	}

	public void setAlertType(Long alerType) {
		this.alertType = alerType;
	}

	public Double getHihiLimit() {
		return hihiLimit;
	}

	public void setHihiLimit(Double hihiLimit) {
		this.hihiLimit = hihiLimit;
	}

	public Double getHiLimit() {
		return hiLimit;
	}

	public void setHiLimit(Double hiLimit) {
		this.hiLimit = hiLimit;
	}

	public Double getLoloLimit() {
		return loloLimit;
	}

	public void setLoloLimit(Double loloLimit) {
		this.loloLimit = loloLimit;
	}

	public Double getLoLimit() {
		return loLimit;
	}

	public void setLoLimit(Double loLimit) {
		this.loLimit = loLimit;
	}

	public void setAlertValue(Double alertValue) {
		this.alertValue = alertValue;
	}

	public PointAlertData(Point p) {
		super();
		this.setId(p.getId());
		this.setServerName(p.getServerName());
		this.setTagName(p.getTagName());
		this.setTagType(p.getTagType());
		this.setDesc(p.getDesc());
		this.setDeviceName(p.getDeviceName());
	}

	public double getAlertValue() {
		return alertValue;
	}

	public void setAlertValue(double alertValue) {
		this.alertValue = alertValue;
	}

	public Long getOccuredTime() {
		return occuredTime;
	}

	public void setOccuredTime(Long occuredTime) {
		this.occuredTime = occuredTime;
	}

	public Long getAlertBeginTime() {
		return alertBeginTime;
	}

	public void setAlertBeginTime(Long alertBeginTime) {
		this.alertBeginTime = alertBeginTime;
	}

	public Long getAlertEndTime() {
		return alertEndTime;
	}

	public void setAlertEndTime(Long alertEndTime) {
		this.alertEndTime = alertEndTime;
	}

}
