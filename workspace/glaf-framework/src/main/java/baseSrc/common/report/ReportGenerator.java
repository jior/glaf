package baseSrc.common.report;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.pentaho.reporting.engine.classic.core.ClassicEngineBoot;
import org.pentaho.reporting.engine.classic.core.DataFactory;
import org.pentaho.reporting.engine.classic.core.Element;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.PageFooter;
import org.pentaho.reporting.engine.classic.core.TableDataFactory;
import org.pentaho.reporting.engine.classic.core.metadata.ElementType;
import org.pentaho.reporting.engine.classic.core.modules.misc.datafactory.sql.DriverConnectionProvider;
import org.pentaho.reporting.engine.classic.core.modules.misc.datafactory.sql.SQLReportDataFactory;
import org.pentaho.reporting.engine.classic.core.modules.output.pageable.pdf.PdfReportUtil;
import org.pentaho.reporting.engine.classic.core.modules.output.table.html.HtmlReportUtil;
import org.pentaho.reporting.engine.classic.core.modules.output.table.xls.ExcelReportUtil;
import org.pentaho.reporting.engine.classic.core.wizard.RelationalAutoGeneratorPreProcessor;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;
import org.pentaho.reporting.libraries.resourceloader.Resource;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;

import baseSrc.framework.BaseException;
import baseSrc.framework.BaseConstants.ReportType;

public class ReportGenerator {

	private DriverConnectionProvider driverConnectionProvider;
	
	public DriverConnectionProvider getDriverConnectionProvider() {
		return driverConnectionProvider;
	}

	public void setDriverConnectionProvider(
			DriverConnectionProvider driverConnectionProvider) {
		this.driverConnectionProvider = driverConnectionProvider;
	}

	public ReportGenerator() {
		// Initialize the reporting engine
		ClassicEngineBoot.getInstance().start();
	}

	public MasterReport getReportDefinition(final String reportTpl) {
		try {
			// Using the classloader, get the URL to the reportDefinition file
			final ClassLoader classloader = this.getClass().getClassLoader();
			final URL reportDefinitionURL = classloader.getResource(reportTpl);

			// Parse the report file
			final ResourceManager resourceManager = new ResourceManager();
			resourceManager.registerDefaults();
			final Resource directly = resourceManager.createDirectly(
					reportDefinitionURL, MasterReport.class);
			return (MasterReport) directly.getResource();
		} catch (ResourceException e) {
			e.printStackTrace();
		}
		return null;
	}
	  @SuppressWarnings("deprecation")
	public DataFactory getDataFactory(MasterReport report,HashMap<String, String> reportQuerys)
	  {
		  
	    final SQLReportDataFactory dataFactory = new SQLReportDataFactory(this.driverConnectionProvider);

	    if (null != reportQuerys && 0 != reportQuerys.size()) {
			for (String key : reportQuerys.keySet()) {
//				dataFactory.setQuery(key,reportQuerys.get(key));
				dataFactory.setQuery(key, reportQuerys.get(key), "", "");
			}	    	
		}	    
	    
	    return dataFactory;
	  }

	public void generateReport(final ReportType reportType,
			final String reportTpl, final HashMap<String, String> reportQuerys,
			final HashMap<String, Object> reportParameters,HttpServletResponse resp) {

		try {

			// Get the report and data factory
			
			
			final MasterReport report = getReportDefinition(reportTpl);
		    final DataFactory dataFactory = getDataFactory(report,reportQuerys);
			
			// Set the data factory for the report
		    if (dataFactory != null) {
		        report.setDataFactory(dataFactory);
		      }

			// Add any parameters to the report
			if (null != reportParameters) {
				for (String key : reportParameters.keySet()) {
					report.getParameterValues().put(key,
							reportParameters.get(key));
				}
			}
			PageFooter p = report.getPageFooter();
			Element[] es = p.getElementArray();
			// Greate the report processor for the specified output type
			switch (reportType) {

			case PDF: {
				resp.setContentType("application/pdf");
				PdfReportUtil.createPDF(report, "D://xx.prpt");
			    PdfReportUtil.createPDF(report, resp.getOutputStream());
			    
				break;
			}

			case EXCEL: {
				resp.setContentType("application/vnd.ms-excel");
				//resp.setHeader("Content-disposition", "inline;");      
				ExcelReportUtil.createXLS(report, resp.getOutputStream());
				//ExcelReportUtil.createXLS(report, outputStream);
				/*final PageableExcelOutputProcessor target = new PageableExcelOutputProcessor(
						report.getConfiguration(), outputStream,
						report.getResourceManager());
				reportProcessor = new PageableReportProcessor(report, target);*/
				break;
			}
			case HTML: {

				resp.setContentType("text/html");
		      HtmlReportUtil.createStreamHTML(report,  resp.getOutputStream());
				break;
			}
			
			default: {
				throw new BaseException(
						"Type of Report is not be found!(PDF,EXCEL)");
			}
			
			}

		} catch (Exception ex) {
			throw new BaseException("Report generating Error ", ex);
		} finally {
			try {
				if (resp.getOutputStream() != null) {
					resp.getOutputStream().close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
