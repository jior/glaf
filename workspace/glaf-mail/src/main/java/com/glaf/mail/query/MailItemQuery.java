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

package com.glaf.mail.query;

import java.util.*;
import com.glaf.core.query.BaseQuery;

public class MailItemQuery extends BaseQuery {
	private static final long serialVersionUID = 1L;
	protected String tableName;
	protected String taskId;
	protected String itemId;
	protected String lastRow;
	protected Date sendDateGreaterThanOrEqual;
	protected Date sendDateLessThanOrEqual;
	protected Integer sendStatus;
	protected Integer sendStatusGreaterThanOrEqual;
	protected Integer sendStatusLessThanOrEqual;
	protected Integer retryTimesGreaterThanOrEqual;
	protected Integer retryTimesLessThanOrEqual;
	protected Date receiveDateGreaterThanOrEqual;
	protected Date receiveDateLessThanOrEqual;
	protected Integer receiveStatus;
	protected Integer receiveStatusGreaterThanOrEqual;
	protected Integer receiveStatusLessThanOrEqual;

	public MailItemQuery() {

	}

	public String getItemId() {
		return itemId;
	}

	public String getLastRow() {
		return lastRow;
	}

	public String getOrderBy() {
		if (sortField != null) {
			String a_x = " asc ";
			if (getSortOrder() != null) {
				a_x = " desc ";
			}

			if ("sendDate".equals(sortField)) {
				orderBy = "E.SENDDATE_" + a_x;
			}

			if ("sendStatus".equals(sortField)) {
				orderBy = "E.SENDSTATUS_" + a_x;
			}

			if ("retryTimes".equals(sortField)) {
				orderBy = "E.RETRYTIMES_" + a_x;
			}

			if ("receiveDate".equals(sortField)) {
				orderBy = "E.RECEIVEDATE_" + a_x;
			}

			if ("receiveStatus".equals(sortField)) {
				orderBy = "E.RECEIVESTATUS_" + a_x;
			}

		}
		return orderBy;
	}

	public Date getReceiveDateGreaterThanOrEqual() {
		return receiveDateGreaterThanOrEqual;
	}

	public Date getReceiveDateLessThanOrEqual() {
		return receiveDateLessThanOrEqual;
	}

	public Integer getReceiveStatus() {
		return receiveStatus;
	}

	public Integer getReceiveStatusGreaterThanOrEqual() {
		return receiveStatusGreaterThanOrEqual;
	}

	public Integer getReceiveStatusLessThanOrEqual() {
		return receiveStatusLessThanOrEqual;
	}

	public Integer getRetryTimesGreaterThanOrEqual() {
		return retryTimesGreaterThanOrEqual;
	}

	public Integer getRetryTimesLessThanOrEqual() {
		return retryTimesLessThanOrEqual;
	}

	public Date getSendDateGreaterThanOrEqual() {
		return sendDateGreaterThanOrEqual;
	}

	public Date getSendDateLessThanOrEqual() {
		return sendDateLessThanOrEqual;
	}

	public Integer getSendStatus() {
		return sendStatus;
	}

	public Integer getSendStatusGreaterThanOrEqual() {
		return sendStatusGreaterThanOrEqual;
	}

	public Integer getSendStatusLessThanOrEqual() {
		return sendStatusLessThanOrEqual;
	}

	public String getTableName() {
		return tableName;
	}

	public String getTaskId() {
		return taskId;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("taskId", "TASKID_");
		addColumn("mailTo", "MAILTO_");
		addColumn("sendDate", "SENDDATE_");
		addColumn("sendStatus", "SENDSTATUS_");
		addColumn("retryTimes", "RETRYTIMES_");
		addColumn("receiveIP", "RECEIVEIP_");
		addColumn("receiveDate", "RECEIVEDATE_");
		addColumn("receiveStatus", "RECEIVESTATUS_");
	}

	public MailItemQuery itemId(String itemId) {
		if (itemId == null) {
			throw new RuntimeException("itemId is null");
		}
		this.itemId = itemId;
		return this;
	}

	public MailItemQuery receiveDateGreaterThanOrEqual(
			Date receiveDateGreaterThanOrEqual) {
		if (receiveDateGreaterThanOrEqual == null) {
			throw new RuntimeException("receiveDate is null");
		}
		this.receiveDateGreaterThanOrEqual = receiveDateGreaterThanOrEqual;
		return this;
	}

	public MailItemQuery receiveDateLessThanOrEqual(
			Date receiveDateLessThanOrEqual) {
		if (receiveDateLessThanOrEqual == null) {
			throw new RuntimeException("receiveDate is null");
		}
		this.receiveDateLessThanOrEqual = receiveDateLessThanOrEqual;
		return this;
	}

	public MailItemQuery receiveStatus(Integer receiveStatus) {
		if (receiveStatus == null) {
			throw new RuntimeException("receiveStatus is null");
		}
		this.receiveStatus = receiveStatus;
		return this;
	}

	public MailItemQuery receiveStatusGreaterThanOrEqual(
			Integer receiveStatusGreaterThanOrEqual) {
		if (receiveStatusGreaterThanOrEqual == null) {
			throw new RuntimeException("receiveStatus is null");
		}
		this.receiveStatusGreaterThanOrEqual = receiveStatusGreaterThanOrEqual;
		return this;
	}

	public MailItemQuery receiveStatusLessThanOrEqual(
			Integer receiveStatusLessThanOrEqual) {
		if (receiveStatusLessThanOrEqual == null) {
			throw new RuntimeException("receiveStatus is null");
		}
		this.receiveStatusLessThanOrEqual = receiveStatusLessThanOrEqual;
		return this;
	}

	public MailItemQuery retryTimesGreaterThanOrEqual(
			Integer retryTimesGreaterThanOrEqual) {
		if (retryTimesGreaterThanOrEqual == null) {
			throw new RuntimeException("retryTimes is null");
		}
		this.retryTimesGreaterThanOrEqual = retryTimesGreaterThanOrEqual;
		return this;
	}

	public MailItemQuery retryTimesLessThanOrEqual(
			Integer retryTimesLessThanOrEqual) {
		if (retryTimesLessThanOrEqual == null) {
			throw new RuntimeException("retryTimes is null");
		}
		this.retryTimesLessThanOrEqual = retryTimesLessThanOrEqual;
		return this;
	}

	public MailItemQuery sendDateGreaterThanOrEqual(
			Date sendDateGreaterThanOrEqual) {
		if (sendDateGreaterThanOrEqual == null) {
			throw new RuntimeException("sendDate is null");
		}
		this.sendDateGreaterThanOrEqual = sendDateGreaterThanOrEqual;
		return this;
	}

	public MailItemQuery sendDateLessThanOrEqual(Date sendDateLessThanOrEqual) {
		if (sendDateLessThanOrEqual == null) {
			throw new RuntimeException("sendDate is null");
		}
		this.sendDateLessThanOrEqual = sendDateLessThanOrEqual;
		return this;
	}

	public MailItemQuery sendStatus(Integer sendStatus) {
		if (sendStatus == null) {
			throw new RuntimeException("sendStatus is null");
		}
		this.sendStatus = sendStatus;
		return this;
	}

	public MailItemQuery sendStatusGreaterThanOrEqual(
			Integer sendStatusGreaterThanOrEqual) {
		if (sendStatusGreaterThanOrEqual == null) {
			throw new RuntimeException("sendStatus is null");
		}
		this.sendStatusGreaterThanOrEqual = sendStatusGreaterThanOrEqual;
		return this;
	}

	public MailItemQuery sendStatusLessThanOrEqual(
			Integer sendStatusLessThanOrEqual) {
		if (sendStatusLessThanOrEqual == null) {
			throw new RuntimeException("sendStatus is null");
		}
		this.sendStatusLessThanOrEqual = sendStatusLessThanOrEqual;
		return this;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public void setLastRow(String lastRow) {
		this.lastRow = lastRow;
	}

	public void setReceiveDateGreaterThanOrEqual(
			Date receiveDateGreaterThanOrEqual) {
		this.receiveDateGreaterThanOrEqual = receiveDateGreaterThanOrEqual;
	}

	public void setReceiveDateLessThanOrEqual(Date receiveDateLessThanOrEqual) {
		this.receiveDateLessThanOrEqual = receiveDateLessThanOrEqual;
	}

	public void setReceiveStatus(Integer receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	public void setReceiveStatusGreaterThanOrEqual(
			Integer receiveStatusGreaterThanOrEqual) {
		this.receiveStatusGreaterThanOrEqual = receiveStatusGreaterThanOrEqual;
	}

	public void setReceiveStatusLessThanOrEqual(
			Integer receiveStatusLessThanOrEqual) {
		this.receiveStatusLessThanOrEqual = receiveStatusLessThanOrEqual;
	}

	public void setRetryTimesGreaterThanOrEqual(
			Integer retryTimesGreaterThanOrEqual) {
		this.retryTimesGreaterThanOrEqual = retryTimesGreaterThanOrEqual;
	}

	public void setRetryTimesLessThanOrEqual(Integer retryTimesLessThanOrEqual) {
		this.retryTimesLessThanOrEqual = retryTimesLessThanOrEqual;
	}

	public void setSendDateGreaterThanOrEqual(Date sendDateGreaterThanOrEqual) {
		this.sendDateGreaterThanOrEqual = sendDateGreaterThanOrEqual;
	}

	public void setSendDateLessThanOrEqual(Date sendDateLessThanOrEqual) {
		this.sendDateLessThanOrEqual = sendDateLessThanOrEqual;
	}

	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}

	public void setSendStatusGreaterThanOrEqual(
			Integer sendStatusGreaterThanOrEqual) {
		this.sendStatusGreaterThanOrEqual = sendStatusGreaterThanOrEqual;
	}

	public void setSendStatusLessThanOrEqual(Integer sendStatusLessThanOrEqual) {
		this.sendStatusLessThanOrEqual = sendStatusLessThanOrEqual;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public MailItemQuery taskId(String taskId) {
		if (taskId == null) {
			throw new RuntimeException("taskId is null");
		}
		this.taskId = taskId;
		return this;
	}

}