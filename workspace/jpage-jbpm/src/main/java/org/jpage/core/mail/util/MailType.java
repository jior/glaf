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


package org.jpage.core.mail.util;

public interface MailType {

	public final static String TEXT_PLAIN = "text/plain"; // 平信

	public final static String TEXT_HTML = "text/html"; // 网页

	public final static String MULTIPART = "multipart/*"; // 混合类型

	public final static String UNPROCESSED = "unprocessed"; // 邮件体是未处理的(直接从James邮件服务器接收但未处理的)

	public final static String DRAFT = "draft"; // 草稿

	public final static String HAVE_SEND = "haveSent"; // 已经发送的邮件

	public final static String SEND_FAILED = "sentFailed"; // 发送失败的邮件

}