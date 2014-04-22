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
package com.glaf.shiro.tag;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.tags.RoleTag;

public class HasAnyRolesOrPermissionsTag extends RoleTag {

	private static final long serialVersionUID = 1L;

	private static final String NAMES_DELIMETER = ",";

	protected boolean showTagBody(String names) {
		boolean hasPermission = false;
		Subject subject = getSubject();
		if (subject != null) {
			if (subject.hasRole(names.trim())) {
				hasPermission = true;
			}
			if (names.indexOf(NAMES_DELIMETER) != -1) {
				for (String role : names.split(NAMES_DELIMETER)) {
					if (subject.hasRole(role.trim())) {
						hasPermission = true;
						break;
					}
				}
			}
			if (subject.isPermitted(names)) {
				hasPermission = true;
			}
			if (names.indexOf(NAMES_DELIMETER) != -1) {
				for (String p : names.split(NAMES_DELIMETER)) {
					if (subject.isPermitted(p.trim())) {
						hasPermission = true;
						break;
					}
				}
			}
		}

		return hasPermission;
	}

}
