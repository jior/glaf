package com.glaf.base.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AbstractTest {
	protected static final Log logger = LogFactory.getLog(AbstractTest.class);

	protected static String configurationResource = "/conf/spring/spring-config.xml";

	protected static org.springframework.context.ApplicationContext ctx;

	protected long start = 0L;

	@Before
	public void setUp() throws Exception {
		System.out.println("��ʼ����..................................");
		start = System.currentTimeMillis();
		if (ctx == null) {
			ctx = new ClassPathXmlApplicationContext(configurationResource);
		}
	}

	@After
	public void tearDown() throws Exception {
		long times = System.currentTimeMillis() - start;
		System.out.println("�ܹ���ʱ(����):" + times);
		System.out.println("������ɡ�");
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(String name) {
		if (ctx == null) {
			ctx = new ClassPathXmlApplicationContext(configurationResource);
		}
		return (T) ctx.getBean(name);
	}

	@Test
	public void showBeans() {
		String[] ids = ctx.getBeanDefinitionNames();
		for (String id : ids) {
			System.out.println(id);
		}
	}

}
