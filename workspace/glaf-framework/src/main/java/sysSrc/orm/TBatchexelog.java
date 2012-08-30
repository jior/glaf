package sysSrc.orm;
import java.util.Date;

//default package


/**
* TBatchexelog entity. @author MyEclipse Persistence Tools
*/

public class TBatchexelog implements java.io.Serializable {

	// Fields

	private Long id;
	private String batname;
	private Date exedate;
	private String execontext;
	private String exeresult;
	private String exeuser;
	private String logfile;
	private Date exeenddate;
	private String batbusname;
	private String exeresultmemo;
	private String checkerrordata;
	private String logfilepath;

	// Constructors

	/** default constructor */
	public TBatchexelog() {
	}

	/** minimal constructor */
	public TBatchexelog(Long id, String batname, Date exedate) {
		this.id = id;
		this.batname = batname;
		this.exedate = exedate;
	}

	/** full constructor */
	public TBatchexelog(Long id, String batname, Date exedate,
			String execontext, String exeresult, String exeuser,
			String logfile, Date exeenddate, String batbusname,
			String exeresultmemo, String checkerrordata, String logfilepath) {
		this.id = id;
		this.batname = batname;
		this.exedate = exedate;
		this.execontext = execontext;
		this.exeresult = exeresult;
		this.exeuser = exeuser;
		this.logfile = logfile;
		this.exeenddate = exeenddate;
		this.batbusname = batbusname;
		this.exeresultmemo = exeresultmemo;
		this.checkerrordata = checkerrordata;
		this.logfilepath = logfilepath;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBatname() {
		return this.batname;
	}

	public void setBatname(String batname) {
		this.batname = batname;
	}

	public Date getExedate() {
		return this.exedate;
	}

	public void setExedate(Date exedate) {
		this.exedate = exedate;
	}

	public String getExecontext() {
		return this.execontext;
	}

	public void setExecontext(String execontext) {
		this.execontext = execontext;
	}

	public String getExeresult() {
		return this.exeresult;
	}

	public void setExeresult(String exeresult) {
		this.exeresult = exeresult;
	}

	public String getExeuser() {
		return this.exeuser;
	}

	public void setExeuser(String exeuser) {
		this.exeuser = exeuser;
	}

	public String getLogfile() {
		return this.logfile;
	}

	public void setLogfile(String logfile) {
		this.logfile = logfile;
	}

	public Date getExeenddate() {
		return this.exeenddate;
	}

	public void setExeenddate(Date exeenddate) {
		this.exeenddate = exeenddate;
	}

	public String getBatbusname() {
		return this.batbusname;
	}

	public void setBatbusname(String batbusname) {
		this.batbusname = batbusname;
	}

	public String getExeresultmemo() {
		return this.exeresultmemo;
	}

	public void setExeresultmemo(String exeresultmemo) {
		this.exeresultmemo = exeresultmemo;
	}

	public String getCheckerrordata() {
		return this.checkerrordata;
	}

	public void setCheckerrordata(String checkerrordata) {
		this.checkerrordata = checkerrordata;
	}

	public String getLogfilepath() {
		return this.logfilepath;
	}

	public void setLogfilepath(String logfilepath) {
		this.logfilepath = logfilepath;
	}

}