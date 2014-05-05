package com.glaf.jbpm;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.UUID32;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.Context;
import com.glaf.jbpm.context.ProcessContext;
import com.glaf.jbpm.datafield.DataField;

public class AbstractJbpmTest {

	protected long start = 0L;

	protected Long processInstanceId = null;

	public void complateTask(Long processInstanceId) {
		System.out
				.println("---------------testComplateTask()--------------------");
		System.out.println("processInstanceId:" + processInstanceId);
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			if (processInstanceId != null) {
				DataField datafield = new DataField();
				datafield.setName("isAgree");
				datafield.setValue("true");
				List<DataField> dataFields = new java.util.concurrent.CopyOnWriteArrayList<DataField>();
				dataFields.add(datafield);
				ProcessContext ctx = new ProcessContext();
				// ctx.setRowId(Integer.valueOf(rowId));//审核表的记录ID,类型要匹配
				ctx.setActorId("joe");// 参与者，登录用户帐户
				ctx.setProcessInstanceId(processInstanceId);// 流程实例编号
				ctx.setOpinion("OK");

				ctx.setDataFields(dataFields);// 控制参数

				boolean isOK = ProcessContainer.getContainer()
						.completeTask(ctx);
				System.out.println("joe audit isOK=" + isOK);

				ctx.setActorId("jack");// 参与者，登录用户帐户
				ctx.setOpinion("通过");
				isOK = ProcessContainer.getContainer().completeTask(ctx);
				System.out.println("jack audit isOK=" + isOK);

				ctx.setActorId("rick");// 参与者，登录用户帐户
				ctx.setOpinion("同意");

				isOK = ProcessContainer.getContainer().completeTask(ctx);
				System.out.println("rick audit isOK=" + isOK);
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			ex.printStackTrace();
			throw new JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("开始测试..................................");
		start = System.currentTimeMillis();
		System.out.println(SystemProperties.getConfigRootPath());
		// Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
	}

	@Test
	public void startProcess() {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			ProcessContext ctx = new ProcessContext();
			ctx.setRowId(UUID32.getUUID());// 审核表的记录ID,类型要匹配
			ctx.setActorId("joy");// 参与者，登录用户帐户
			ctx.setTitle("单据编号：" + ctx.getRowId());// 流程标题
			ctx.setProcessName("SimpleProcess");// 流程名称（英文的名称）

			Collection<DataField> datafields = new java.util.concurrent.CopyOnWriteArrayList<DataField>();

			DataField datafield = new DataField();
			datafield.setName("isAgree");// 控制参数名称
			datafield.setValue("true");// 控制参数值
			datafields.add(datafield);

			DataField datafield2 = new DataField();
			datafield2.setName("money");
			datafield2.setValue(2345.88D);
			datafields.add(datafield2);

			DataField datafield3 = new DataField();
			datafield3.setName("Auditor1");// 控制参数名称
			datafield3.setValue("joe,dane");// 控制参数值
			datafields.add(datafield3);

			DataField datafield4 = new DataField();
			datafield4.setName("Auditor2");// 控制参数名称
			datafield4.setValue("jack,rick");// 控制参数值
			datafields.add(datafield4);

			DataField datafield5 = new DataField();
			datafield5.setName("signatureDate");
			datafield5.setValue(new Date());
			datafields.add(datafield5);

			ctx.setDataFields(datafields);// 控制参数

			processInstanceId = ProcessContainer.getContainer().startProcess(
					ctx);

			// 如果流程实例编号不为空，那么已经成功创建流程实例，否则抛出异常
			if (processInstanceId != null) {
				System.out.println("processInstanceId=" + processInstanceId);
				System.out.println("OK!");
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			ex.printStackTrace();
			throw new JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
		complateTask(processInstanceId);
	}

	@After
	public void tearDown() throws Exception {
		long times = System.currentTimeMillis() - start;
		System.out.println("总共耗时(毫秒):" + times);
		System.out.println("测试完成。");
	}

}
