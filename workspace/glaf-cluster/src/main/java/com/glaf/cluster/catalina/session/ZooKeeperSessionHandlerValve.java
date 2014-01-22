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

package com.glaf.cluster.catalina.session;

import org.apache.catalina.Session;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;

import javax.servlet.ServletException;
import java.io.IOException;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

public class ZooKeeperSessionHandlerValve extends ValveBase {
	private final Log log = LogFactory.getLog(ZooKeeperSessionManager.class);
	private ZooKeeperSessionManager manager;

	@Override
	public void invoke(Request request, Response response) throws IOException,
			ServletException {
		try {
			getNext().invoke(request, response);
		} finally {
			final Session session = request.getSessionInternal(false);
			storeOrRemoveSession(session);
			manager.afterRequest();
		}
	}

	public void setZooKeeperSessionManager(ZooKeeperSessionManager manager) {
		this.manager = manager;
	}

	private void storeOrRemoveSession(Session session) {
		try {
			if (session != null) {
				if (session.isValid()) {
					log.trace("Request with session completed, saving session "
							+ session.getId());
					if (session.getSession() != null) {
						log.trace("HTTP Session present, saving "
								+ session.getId());
						manager.save(session);
					} else {
						log.trace("No HTTP Session present, Not saving "
								+ session.getId());
					}
				} else {
					log.trace("HTTP Session has been invalidated, removing :"
							+ session.getId());
					manager.remove(session);
				}
			}
		} catch (Exception e) {
		}
	}
}
