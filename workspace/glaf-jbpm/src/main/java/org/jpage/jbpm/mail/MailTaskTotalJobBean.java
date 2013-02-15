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


package org.jpage.jbpm.mail;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jpage.core.mail.service.MailMessage;
import org.jpage.core.mail.util.MailTools;
import org.jpage.core.threads.ThreadFactory;
import org.jpage.jbpm.model.TaskItem;
import org.jpage.jbpm.service.PersistenceContainer;
import org.jpage.jbpm.service.ProcessContainer;

public class MailTaskTotalJobBean {

	 
	public MailTaskTotalJobBean() {
		 
		 
	}

	public void execute() {
		Map userMap = PersistenceContainer.getContainer().getUserMap();
		if (userMap == null || userMap.size() == 0) {
			return;
		}
		List rows = ProcessContainer.getContainer().getAllTaskItems();
		if (rows != null && rows.size() > 0) {
			Map taskMap = new HashMap();
			Iterator iterator = rows.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = (TaskItem) iterator.next();
				String actorId = taskItem.getActorId();
				if (userMap.get(actorId) != null) {
					org.jpage.actor.User user = (org.jpage.actor.User) userMap
							.get(actorId);
					if (user != null && MailTools.isMailAddress(user.getMail())) {
						Integer total = (Integer) taskMap.get(actorId);
						if (total == null) {
							total = new Integer(0);
						}
						total = new Integer(total.intValue() + 1);
						taskMap.put(actorId, total);
					}
				}
			}

			if (taskMap.size() > 0) {
				Iterator iterator2 = taskMap.keySet().iterator();
				while (iterator2.hasNext()) {
					String actorId = (String) iterator2.next();
					Integer total = (Integer) taskMap.get(actorId);
					org.jpage.actor.User user = (org.jpage.actor.User) userMap
							.get(actorId);
					MailMessage mailMessage = new MailMessage();
					mailMessage.setTo(user.getMail());
					mailMessage.setSubject("流程管理：您有" + total + "项新任务");
					mailMessage.setText("您好：\n     已经有 \"" + total
							+ "\" 项新任务分配给您，请及时处理，谢谢！。");
					MailThread thread = new MailThread(mailMessage);
					ThreadFactory.run(thread);
				}
			}
		}
	}
}
