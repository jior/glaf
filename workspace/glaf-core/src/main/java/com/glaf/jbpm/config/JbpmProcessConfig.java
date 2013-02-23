package com.glaf.jbpm.config;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.jbpm.file.def.FileDefinition;
import org.jbpm.graph.def.ProcessDefinition;

import com.glaf.core.config.SystemConfig;
import com.glaf.core.util.FileUtils;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.Context;

public class JbpmProcessConfig {

	private static ConcurrentMap<String, Object> cache = new ConcurrentHashMap<String, Object>();

	public static byte[] getGpd(Long processDefinitionId) {
		byte[] bytes = null;
		String cacheKey = "gpd_" + String.valueOf(processDefinitionId);
		if (cache.get(cacheKey) != null) {
			bytes = (byte[]) cache.get(cacheKey);
			return bytes;
		}

		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			if (jbpmContext.getSession() != null) {
				ProcessDefinition processDefinition = jbpmContext
						.getGraphSession().getProcessDefinition(
								processDefinitionId);
				if (processDefinition != null) {
					FileDefinition fd = processDefinition.getFileDefinition();
					bytes = fd.getBytes("gpd.xml");
					cache.put(cacheKey, bytes);
				}
			}
		} catch (Exception ex) {
			throw new JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return bytes;
	}

	public static byte[] getImage(Long processDefinitionId) {
		byte[] bytes = null;
		JbpmContext jbpmContext = null;
		try {
			String filename = SystemConfig.getAppPath() + "/deploy/"
					+ processDefinitionId + ".jpg";
			File file = new File(filename);
			if (file.exists()) {
				bytes = FileUtils.getBytes(file);
			} else {
				jbpmContext = ProcessContainer.getContainer()
						.createJbpmContext();
				if (jbpmContext.getSession() != null) {
					ProcessDefinition processDefinition = jbpmContext
							.getGraphSession().getProcessDefinition(
									processDefinitionId);
					if (processDefinition != null) {
						FileDefinition fd = processDefinition
								.getFileDefinition();
						bytes = fd.getBytes("processimage.jpg");
						FileUtils.save(filename, bytes);
					}
				}
			}
		} catch (Exception ex) {
			throw new JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return bytes;
	}

}
