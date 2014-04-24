package com.glaf.test.core;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.glaf.core.jdbc.JsonQueryHelper;

public class DialectTest {

	@Test
	public void testPageSQL() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		JsonQueryHelper helper = new JsonQueryHelper();
		JSONArray result = helper.getJSONArray("pdemo",
				"select  * from volume ", paramMap, 10, 20);
		System.out.println(result.toJSONString());
	}

}
