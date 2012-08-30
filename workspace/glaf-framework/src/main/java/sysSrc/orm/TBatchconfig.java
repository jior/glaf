package sysSrc.orm;
// default package

import java.math.BigDecimal;
import java.util.Date;

/**
 * TBatchconfig entity. @author MyEclipse Persistence Tools
 */

public class TBatchconfig implements java.io.Serializable {

	// Fields

	private String batname;
	private Date lastexedate;
	private BigDecimal interval;
	private String what;
	private String lockflag;
	private String enable;
	private String batclassname;
	private String batbusname;

	// Constructors

	/** default constructor */
	public TBatchconfig() {
	}

	/** minimal constructor */
	public TBatchconfig(String batname) {
		this.batname = batname;
	}

	/** full constructor */
	public TBatchconfig(String batname, Date lastexedate,
			BigDecimal interval, String what, String lockflag, String enable,
			String batclassname, String batbusname) {
		this.batname = batname;
		this.lastexedate = lastexedate;
		this.interval = interval;
		this.what = what;
		this.lockflag = lockflag;
		this.enable = enable;
		this.batclassname = batclassname;
		this.batbusname = batbusname;
	}

	// Property accessors

	public String getBatname() {
		return this.batname;
	}

	public void setBatname(String batname) {
		this.batname = batname;
	}

	public Date getLastexedate() {
		return this.lastexedate;
	}

	public void setLastexedate(Date lastexedate) {
		this.lastexedate = lastexedate;
	}

	public BigDecimal getInterval() {
		return this.interval;
	}

	public void setInterval(BigDecimal interval) {
		this.interval = interval;
	}

	public String getWhat() {
		return this.what;
	}

	public void setWhat(String what) {
		this.what = what;
	}

	public String getLockflag() {
		return this.lockflag;
	}

	public void setLockflag(String lockflag) {
		this.lockflag = lockflag;
	}

	public String getEnable() {
		return this.enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getBatclassname() {
		return this.batclassname;
	}

	public void setBatclassname(String batclassname) {
		this.batclassname = batclassname;
	}

	public String getBatbusname() {
		return this.batbusname;
	}

	public void setBatbusname(String batbusname) {
		this.batbusname = batbusname;
	}

}