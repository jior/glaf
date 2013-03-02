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

package com.glaf.ui.mapper;

import java.util.*;

import org.springframework.stereotype.Component;

import com.glaf.ui.model.*;
import com.glaf.ui.query.SkinQuery;

@Component
public interface SkinMapper {

	void deleteSkins(SkinQuery query);

	void deleteSkinById(String id);

	void deleteSkinInstanceByActorId(String actorId);

	Skin getSkinById(String id);

	Skin getUserSkin(String actorId);

	int getSkinCount(SkinQuery query);

	List<Skin> getSkins(SkinQuery query);

	void insertSkin(Skin model);

	void insertSkinInstance(SkinInstance skinInstance);

	void updateSkin(Skin model);

}