package com.glaf.base.modules.sys.model;

import java.io.Serializable;
import java.util.Date;

public class ExchangeRateHistory implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5457194816727528674L;
	private long id;
	private String rate;
	private Date createDate;
	private String creator;
	private int rateFlag;
	
	private Dictory dictory;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Dictory getDictory() {
		return dictory;
	}

	public void setDictory(Dictory dictory) {
		this.dictory = dictory;
	}

	public int getRateFlag() {
		return rateFlag;
	}

	public void setRateFlag(int rateFlag) {
		this.rateFlag = rateFlag;
	}


}

