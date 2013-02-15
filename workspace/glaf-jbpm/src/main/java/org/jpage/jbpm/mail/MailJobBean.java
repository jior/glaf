/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
