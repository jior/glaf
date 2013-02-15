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

package org.jpage.core.mail.service;

import javax.mail.MessagingException;
import javax.mail.Session;

import org.jpage.util.UUID32;

public class CustomMimeMessage extends javax.mail.internet.MimeMessage {

	public CustomMimeMessage(Session session) {
		super(session);
	}

	public CustomMimeMessage(Session session, java.io.InputStream is)
			throws MessagingException {
		super(session, is);
	}

	public void updateMessageID() {
		try {
			super.setHeader("Message-ID", "<" + UUID32.getUUID() + ">");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
