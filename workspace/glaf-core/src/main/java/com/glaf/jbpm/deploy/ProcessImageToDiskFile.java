package com.glaf.jbpm.deploy;

import java.io.File;
import java.util.List;

import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.jbpm.file.def.FileDefinition;
import org.jbpm.graph.def.ProcessDefinition;

import com.glaf.core.util.FileUtils;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.Context;

public class ProcessImageToDiskFile {

	public static void main(String[] args) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			if (jbpmContext.getSession() != null) {
				ProcessImageToDiskFile bean = new ProcessImageToDiskFile();
				bean.publish(jbpmContext, new File("./images"));
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			throw new JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	public void publish(JbpmContext jbpmContext, File saveDir) {
		List<ProcessDefinition> list = jbpmContext.getGraphSession()
				.findAllProcessDefinitions();
		if (list != null && !list.isEmpty()) {
			for (ProcessDefinition pd : list) {
				FileDefinition fd = pd.getFileDefinition();
				byte[] bytes = fd.getBytes("processimage.jpg");
				try {
					String filename = saveDir.getAbsolutePath() + "/"
							+ pd.getId() + ".jpg";
					FileUtils.save(filename, bytes);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				try {
					String filename = saveDir.getAbsolutePath() + "/"
							+ pd.getName() + "_" + pd.getVersion() + ".jpg";
					FileUtils.save(filename, bytes);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public void publish(JbpmContext jbpmContext,
			ProcessDefinition processDefinition, File saveDir) {
		FileDefinition fd = processDefinition.getFileDefinition();
		byte[] bytes = fd.getBytes("processimage.jpg");
		try {
			String filename = saveDir.getAbsolutePath() + "/"
					+ processDefinition.getId() + ".jpg";
			FileUtils.save(filename, bytes);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			String filename = saveDir.getAbsolutePath() + "/"
					+ processDefinition.getName() + "_"
					+ processDefinition.getVersion() + ".jpg";
			FileUtils.save(filename, bytes);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void publish(JbpmContext jbpmContext, String processName,
			File saveDir) {
		List<ProcessDefinition> list = jbpmContext.getGraphSession()
				.findAllProcessDefinitionVersions(processName);
		if (list != null && !list.isEmpty()) {
			for (ProcessDefinition pd : list) {
				FileDefinition fd = pd.getFileDefinition();
				byte[] bytes = fd.getBytes("processimage.jpg");
				try {
					String filename = saveDir.getAbsolutePath() + "/"
							+ pd.getId() + ".jpg";
					FileUtils.save(filename, bytes);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				try {
					String filename = saveDir.getAbsolutePath() + "/"
							+ pd.getName() + "_" + pd.getVersion() + ".jpg";
					FileUtils.save(filename, bytes);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}
