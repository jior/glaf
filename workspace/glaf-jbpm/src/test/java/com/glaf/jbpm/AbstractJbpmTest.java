package com.glaf.jbpm;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.glaf.core.config.SystemConfig;
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
				// ctx.setRowId(Integer.valueOf(rowId));//��˱�ļ�¼ID,����Ҫƥ��
				ctx.setActorId("joe");// �����ߣ���¼�û��ʻ�
				ctx.setProcessInstanceId(processInstanceId);// ����ʵ�����
				ctx.setOpinion("OK");

				ctx.setDataFields(dataFields);// ���Ʋ���

				boolean isOK = ProcessContainer.getContainer()
						.completeTask(ctx);
				System.out.println("joe audit isOK=" + isOK);

				ctx.setActorId("jack");// �����ߣ���¼�û��ʻ�
				ctx.setOpinion("ͨ��");
				isOK = ProcessContainer.getContainer().completeTask(ctx);
				System.out.println("jack audit isOK=" + isOK);

				ctx.setActorId("rick");// �����ߣ���¼�û��ʻ�
				ctx.setOpinion("ͬ��");

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
		System.out.println("��ʼ����..................................");
		start = System.currentTimeMillis();
		System.out.println(SystemConfig.getConfigRootPath());
		// Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
	}

	@Test
	public void startProcess() {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			ProcessContext ctx = new ProcessContext();
			ctx.setRowId(UUID32.getUUID());// ��˱�ļ�¼ID,����Ҫƥ��
			ctx.setActorId("joy");// �����ߣ���¼�û��ʻ�
			ctx.setTitle("���ݱ�ţ�" + ctx.getRowId());// ���̱���
			ctx.setProcessName("SimpleProcess");// �������ƣ�Ӣ�ĵ����ƣ�

			Collection<DataField> datafields = new java.util.concurrent.CopyOnWriteArrayList<DataField>();

			DataField datafield = new DataField();
			datafield.setName("isAgree");// ���Ʋ�������
			datafield.setValue("true");// ���Ʋ���ֵ
			datafields.add(datafield);

			DataField datafield2 = new DataField();
			datafield2.setName("money");
			datafield2.setValue(2345.88D);
			datafields.add(datafield2);

			DataField datafield3 = new DataField();
			datafield3.setName("Auditor1");// ���Ʋ�������
			datafield3.setValue("joe,dane");// ���Ʋ���ֵ
			datafields.add(datafield3);

			DataField datafield4 = new DataField();
			datafield4.setName("Auditor2");// ���Ʋ�������
			datafield4.setValue("jack,rick");// ���Ʋ���ֵ
			datafields.add(datafield4);

			DataField datafield5 = new DataField();
			datafield5.setName("signatureDate");
			datafield5.setValue(new Date());
			datafields.add(datafield5);

			ctx.setDataFields(datafields);// ���Ʋ���

			processInstanceId = ProcessContainer.getContainer().startProcess(
					ctx);

			// �������ʵ����Ų�Ϊ�գ���ô�Ѿ��ɹ���������ʵ���������׳��쳣
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
		System.out.println("�ܹ���ʱ(����):" + times);
		System.out.println("������ɡ�");
	}

}
