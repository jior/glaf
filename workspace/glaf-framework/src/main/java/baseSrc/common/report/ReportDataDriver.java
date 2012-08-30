package baseSrc.common.report;

import org.pentaho.reporting.engine.classic.core.modules.misc.datafactory.sql.DriverConnectionProvider;

public class ReportDataDriver extends DriverConnectionProvider {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String getDriverClassName() {
		return super.getDriver();
	}

	public void setDriverClassName(String driverClassName) {
		super.setDriver(driverClassName);
	}

	public String getUsername() {
		return super.getProperty("user");
	}

	public void setUsername(String username) {
		super.setProperty("user", username);
	}

	public String getPassword() {
		return super.getProperty("password");
	}

	public void setPassword(String password) {
		super.setProperty("password", password);
	}


}
