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

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jpage.core.mail.service.MailMessage;
import org.jpage.core.mail.util.MailTools;
import org.jpage.core.threads.ThreadFactory;
import org.jpage.jbpm.model.TaskItem;
import org.jpage.jbpm.service.PersistenceContainer;
import org.jpage.jbpm.service.ProcessContainer;

public class MailJobBean {

 

	public MailJobBean() {
		 
		 
	}

	public void execute() {
		Map userMap = PersistenceContainer.getContainer().getUserMap();
		if (userMap == null || userMap.size() == 0) {
			return;
		}
		List rows = ProcessContainer.getContainer().getAllTaskItems();
		if (rows != null && rows.size() > 0) {
			Iterator iterator = rows.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = (TaskItem) iterator.next();
				String actorId = taskItem.getActorId();
				if (userMap.get(actorId) != null) {
					org.jpage.actor.User user = (org.jpage.actor.User) userMap
							.get(actorId);
					if (user != null && MailTools.isMailAddress(user.getMail())) {
						MailMessage mailMessage = new MailMessage();
						mailMessage.setTo(user.getMail());
						mailMessage.setSubject("您的新任务："
								+ taskItem.getTaskDescription());
						mailMessage.setText("您好：\n    任务 \""
								+ taskItem.getTaskDescription()
								+ "\" 已经分配给您，请及时处理，谢谢！。");
						MailThread thread = new MailThread(mailMessage);
						ThreadFactory.run(thread);
					}
				}
			}
		}
	}
}
