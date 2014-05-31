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

import com.glaf.core.base.DataFile;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.domain.BlobItemEntity;
import com.glaf.core.service.IBlobService;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.UUID32;
import com.glaf.test.AbstractTest;

public class BlobTest extends AbstractTest {

	protected IBlobService blobService;

	@Test
	public void testAddFile() {
		blobService = super.getBean("blobService");
		System.out.println(blobService.getClass().getName());
		String appPath = SystemProperties.getAppPath();
		System.out.println("appPath:" + appPath);
		DataFile blobData = new BlobItemEntity();
		String id = UUID32.getUUID();
		blobData.setId(id);
		blobData.setBusinessKey("test_123");
		blobData.setCreateBy("test");
		blobData.setData(FileUtils.getBytes("e:/project/glaf/readme.txt"));
		blobData.setFilename("readme.txt");
		blobData.setLastModified(System.currentTimeMillis());
		blobData.setName("readme");
		blobData.setServiceKey("readme");
		blobData.setStatus(0);
		blobData.setType("txt");
		blobService.insertBlob(blobData);

		blobData = blobService.getBlobById(id);
		blobData.setData(FileUtils.getBytes("e:/project/glaf/LICENSE.txt"));
		blobService.updateBlobFileInfo(blobData);
	}

}
