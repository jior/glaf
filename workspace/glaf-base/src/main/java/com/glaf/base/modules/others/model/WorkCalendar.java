package com.glaf.base.modules.others.model;

import java.io.Serializable;

public class WorkCalendar implements Serializable{
	private static final long serialVersionUID = -5396045849722935648L;
	private long id;
	private int freeDay;
	private int freeMonth;
	private int freeYear;
	public int getFreeDay() {
		return freeDay;
	}
	public void setFreeDay(int freeDay) {
		this.freeDay = freeDay;
	}
	public int getFreeMonth() {
		return freeMonth;
	}
	public void setFreeMonth(int freeMonth) {
		this.freeMonth = freeMonth;
	}
	public int getFreeYear() {
		return freeYear;
	}
	public void setFreeYear(int freeYear) {
		this.freeYear = freeYear;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
}
