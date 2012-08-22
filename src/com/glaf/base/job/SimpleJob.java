package com.glaf.base.job;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpage.util.DateTools;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SimpleJob implements Job {

	protected final static Log logger = LogFactory.getLog(SimpleJob.class);

	public SimpleJob() {

	}

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String jobName = context.getJobDetail().getFullName();
		logger.info("Executing job: " + jobName + " executing at "
				+ DateTools.getDateTime(new Date()));
	}

}