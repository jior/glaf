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

package com.glaf.core.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.core.base.BlobItem;
import com.glaf.core.query.BlobItemQuery;

@Component
public interface BlobItemMapper {

	void deleteBlobItems(BlobItemQuery query);

	void deleteBlobItemById(String id);

	void deleteBlobItemsByFileId(String fileId);

	void deleteBlobItemsByBusinessKey(String businessKey);

	BlobItem getBlobItemById(String id);

	List<BlobItem> getBlobItemsByFileId(String fileId);
	
	List<BlobItem> getBlobItemsByFilename(String filename);

	int getBlobItemCount(BlobItemQuery query);

	List<BlobItem> getBlobItems(BlobItemQuery query);

	void insertBlobItem(BlobItem model);
	
	void insertBlobItem_postgres(BlobItem model);

	void updateBlobItem(BlobItem model);

}