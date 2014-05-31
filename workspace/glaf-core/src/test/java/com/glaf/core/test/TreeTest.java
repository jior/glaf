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

import java.util.List;

import org.junit.Test;

import com.glaf.core.base.TreeModel;
import com.glaf.test.AbstractTest;

public class TreeTest extends AbstractTest {

	@Test
	public void testTree() {
		List<TreeModel> treeModels = com.glaf.core.security.IdentityFactory
				.getChildrenTreeModels(3L);
		if (treeModels != null && !treeModels.isEmpty()) {
			for (TreeModel treeModel : treeModels) {
				logger.debug(treeModel.toJsonObject().toJSONString());
			}
		}
	}

}
