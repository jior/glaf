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
package com.glaf.activiti.test;

 
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.glaf.core.util.UUID32;
import org.junit.Test;

import com.glaf.activiti.container.ProcessContainer;
import com.glaf.activiti.model.DataField;
import com.glaf.activiti.model.ProcessContext;

public class StartAndCompleteTest extends AbstractActivitiTest {

	protected String processInstanceId = null;

	@Test
	public void testStartProcess() {
		try {
			ProcessContext ctx = new ProcessContext();
			ctx.setBusinessKey(UUID32.getUUID());// ��˱�ļ�¼ID,����Ҫƥ��
			ctx.setActorId("joy");// �����ߣ���¼�û��ʻ�
			ctx.setTitle("���ݱ�ţ�" + ctx.getBusinessKey());// ���̱���
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
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		complateTask(processInstanceId);
	}

	public void complateTask(String processInstanceId) {
		System.out.println("----------testComplateTask()----------------");
		System.out.println("processInstanceId:" + processInstanceId);
		try {
			if (processInstanceId != null) {
				DataField datafield = new DataField();
				datafield.setName("approve");
				datafield.setValue(true);
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

				ctx.setActorId("dane");// �����ߣ���¼�û��ʻ�
				isOK = ProcessContainer.getContainer().completeTask(ctx);
				System.out.println("dane audit isOK=" + isOK);

				ctx.setActorId("jack");// �����ߣ���¼�û��ʻ�
				ctx.setOpinion("ͨ��");
				isOK = ProcessContainer.getContainer().completeTask(ctx);
				System.out.println("jack audit isOK=" + isOK);

				datafield.setValue(false);
				ctx.setActorId("rick");// �����ߣ���¼�û��ʻ�
				ctx.setOpinion("��ͬ��");

				isOK = ProcessContainer.getContainer().completeTask(ctx);
				System.out.println("rick audit isOK=" + isOK);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
}
