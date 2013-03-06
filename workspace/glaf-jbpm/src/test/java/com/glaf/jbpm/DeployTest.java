package com.glaf.jbpm;

import java.io.File;

import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.junit.Test;

import com.glaf.core.util.FileUtils;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.Context;
import com.glaf.jbpm.deploy.MxJbpmProcessDeployer;

public class DeployTest {
	
	@Test
	public void testDeploy() {
		MxJbpmProcessDeployer deployer = new MxJbpmProcessDeployer();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			File dir = new File("jpdl");
			if (dir.exists() && dir.isDirectory()) {
				File contents[] = dir.listFiles();
				if (contents != null) {
					for (int i = 0; i < contents.length; i++) {
						if (contents[i].isFile()
								&& contents[i].getName().endsWith(".zip")) {
							deployer.deploy(jbpmContext,
									FileUtils.getBytes(contents[i]));
						} else {
							File path = contents[i];
							File chilrenContents[] = path.listFiles();
							if (chilrenContents != null) {
								for (int j = 0; j < chilrenContents.length; j++) {
									if (chilrenContents[j].isFile()
											&& chilrenContents[j].getName()
													.endsWith(".zip")) {
										deployer.deploy(jbpmContext, FileUtils
												.getBytes(chilrenContents[j]));
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			ex.printStackTrace();
			throw new JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

}
