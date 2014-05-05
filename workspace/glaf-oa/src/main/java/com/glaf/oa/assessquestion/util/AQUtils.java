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
package com.glaf.oa.assessquestion.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.BaseDataInfo;
import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.base.modules.sys.model.SysTree;

public class AQUtils {

	/**
	 * 按CODE获得list
	 * 
	 * @param code
	 * @return
	 */
	public static List<BaseDataInfo> getBaseInfoByCode(String code) {

		return BaseDataManager.getInstance().getBaseData(code);
	}

	/**
	 * 把基础数据转成MAP，KEY为CODE，VALUE为BaseDataInfo对象
	 * 
	 * @param baseList
	 * @return
	 */
	public static Map<String, BaseDataInfo> baseList2MapByCode(
			List<BaseDataInfo> baseList) {
		Map<String, BaseDataInfo> baseMap = new HashMap<String, BaseDataInfo>();
		for (BaseDataInfo info : baseList) {
			baseMap.put(info.getCode(), info);
		}
		return baseMap;
	}

	/**
	 * 按Code取基础数据值
	 * 
	 * @param baseDataMap
	 * @param code
	 * @return 当没有对应code时返回 new BaseDataInfo
	 */
	public static BaseDataInfo getValueByCode(
			Map<String, BaseDataInfo> baseDataMap, String code) {

		BaseDataInfo baseInfo = baseDataMap.get(code);
		if (baseInfo == null) {
			baseInfo = new BaseDataInfo();
		}
		return baseInfo;
	}

	/**
	 * 按 ID获得tree
	 * 
	 * @param id
	 * @return
	 */
	public static SysTree getTreeById(long id) {
		return BaseDataManager.getInstance().getSysTreeService().findById(id);
	}

	/**
	 * 按 ID获得dictory
	 * 
	 * @param id
	 * @return
	 */
	public static Dictory getDictoryById(long id) {
		return BaseDataManager.getInstance().getDictoryService().find(id);
	}

	public static List<BaseDataInfo> getBaseListByParentId(
			Map<String, BaseDataInfo> baseMap, int parentId) {
		List<BaseDataInfo> resutList = new ArrayList<BaseDataInfo>();
		Iterator<BaseDataInfo> iter = baseMap.values().iterator();
		while (iter.hasNext()) {
			BaseDataInfo baseInfo = iter.next();
			if (baseInfo.getParentId() == parentId) {
				resutList.add(baseInfo);
			}
		}
		return resutList;
	}

}