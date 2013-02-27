package com.glaf.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.CustomProperties;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.UUID32;

public class CacheTest {

	protected long start = 0L;

	@Before
	public void setUp() throws Exception {
		System.out.println("��ʼ����..................................");
		start = System.currentTimeMillis();
		CustomProperties.reload();
		System.out.println(SystemProperties.getConfigRootPath());
	}

	@After
	public void tearDown() throws Exception {
		long times = System.currentTimeMillis() - start;
		System.out.println("�ܹ���ʱ(����):" + times);
		System.out.println("������ɡ�");
	}

	@Test
	public void testPut() {
		for (int i = 0; i < 2000; i++) {
			CacheFactory.put("cache_" + i, "value_"+i+"#"+UUID32.getUUID());
		}
	}

	@Test
	public void testGet() {
		for (int i = 500; i < 600; i++) {
			System.out.println(CacheFactory.get("cache_" + i));
		}
	}

}
