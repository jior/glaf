/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.jbpm.web.springmvc;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glaf.jbpm.config.JbpmProcessConfig;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.Context;

@Controller("/jbpm/image")
@RequestMapping("/jbpm/image")
public class MxJbpmImageController {
	private static final Log logger = LogFactory
			.getLog(MxJbpmImageController.class);

	@RequestMapping
	@ResponseBody
	public byte[] showImage(
			@RequestParam(value = "processDefinitionId", required = false) String processDefinitionId,
			@RequestParam(value = "processName", required = false) String processName) {
		byte[] bytes = null;
		JbpmContext jbpmContext = null;
		try {
			if (StringUtils.isNotEmpty(processDefinitionId)
					&& StringUtils.isNumeric(processDefinitionId)) {
				bytes = JbpmProcessConfig.getImage(Long
						.parseLong(processDefinitionId));
			} else if (StringUtils.isNotEmpty(processName)) {
				jbpmContext = ProcessContainer.getContainer()
						.createJbpmContext();
				ProcessDefinition processDefinition = jbpmContext
						.getGraphSession().findLatestProcessDefinition(
								processName);
				if (processDefinition != null) {
					bytes = JbpmProcessConfig.getImage(processDefinition
							.getId());
				}
			}
		} catch (Exception ex) {
			logger.debug(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return bytes;
	}
}