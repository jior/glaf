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
package com.glaf.core.test;

import org.junit.Test;

import com.glaf.test.AbstractTest;
import com.glaf.transport.domain.FileTransport;
import com.glaf.transport.provider.SmbTransportProvider;
import com.glaf.transport.service.FileTransportService;

public class SmbTest extends AbstractTest {

	protected FileTransportService fileTransportService;

	@Test
	public void testSave() {
		fileTransportService = super.getBean("fileTransportService");
		FileTransport t = fileTransportService.getFileTransport(3L);
		System.out.println("key=" + t.getKey());
		SmbTransportProvider p = new SmbTransportProvider();
		p.saveFile(t, "test.txt", "24680xyzABC".getBytes());
		System.out.println(new String(p.getBytes(t, "test.txt")));
	}

}
