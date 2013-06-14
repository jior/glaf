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

package com.glaf.core.todo;

import java.util.Collection;
import java.util.HashSet;

public class TodoTotal implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private StringBuffer allBuffer = new StringBuffer();

	private StringBuffer allProcessBuffer = new StringBuffer();

	private Collection<Object> caution = new HashSet<Object>();

	private StringBuffer cautionBuffer = new StringBuffer();

	private StringBuffer cautionProcessBuffer = new StringBuffer();

	private int cautionQty;

	private Collection<Object> ok = new HashSet<Object>();

	private StringBuffer okBuffer = new StringBuffer();

	private StringBuffer okProcessBuffer = new StringBuffer();

	private int okQty;

	private Collection<Object> pastDue = new HashSet<Object>();

	private StringBuffer pastDueBuffer = new StringBuffer();

	private StringBuffer pastDueProcessBuffer = new StringBuffer();

	private int pastDueQty;

	private Collection<Object> processInstanceIds = new HashSet<Object>();

	private Collection<Object> rowIds = new HashSet<Object>();

	private Todo todo;

	private int totalQty;

	public TodoTotal() {

	}

	public StringBuffer getAllBuffer() {
		return allBuffer;
	}

	public StringBuffer getAllProcessBuffer() {
		return allProcessBuffer;
	}

	public Collection<Object> getCaution() {
		return caution;
	}

	public StringBuffer getCautionBuffer() {
		return cautionBuffer;
	}

	public StringBuffer getCautionProcessBuffer() {
		return cautionProcessBuffer;
	}

	public int getCautionQty() {
		return cautionQty;
	}

	public Collection<Object> getOk() {
		return ok;
	}

	public StringBuffer getOkBuffer() {
		return okBuffer;
	}

	public StringBuffer getOkProcessBuffer() {
		return okProcessBuffer;
	}

	public int getOkQty() {
		return okQty;
	}

	public Collection<Object> getPastDue() {
		return pastDue;
	}

	public StringBuffer getPastDueBuffer() {
		return pastDueBuffer;
	}

	public StringBuffer getPastDueProcessBuffer() {
		return pastDueProcessBuffer;
	}

	public int getPastDueQty() {
		return pastDueQty;
	}

	public Collection<Object> getProcessInstanceIds() {
		return processInstanceIds;
	}

	public Collection<Object> getRowIds() {
		return rowIds;
	}

	public Todo getTodo() {
		return todo;
	}

	public int getTotalQty() {
		return totalQty;
	}

	public void setAllBuffer(StringBuffer allBuffer) {
		this.allBuffer = allBuffer;
	}

	public void setAllProcessBuffer(StringBuffer allProcessBuffer) {
		this.allProcessBuffer = allProcessBuffer;
	}

	public void setCaution(Collection<Object> caution) {
		this.caution = caution;
	}

	public void setCautionBuffer(StringBuffer cautionBuffer) {
		this.cautionBuffer = cautionBuffer;
	}

	public void setCautionProcessBuffer(StringBuffer cautionProcessBuffer) {
		this.cautionProcessBuffer = cautionProcessBuffer;
	}

	public void setCautionQty(int cautionQty) {
		this.cautionQty = cautionQty;
	}

	public void setOk(Collection<Object> ok) {
		this.ok = ok;
	}

	public void setOkBuffer(StringBuffer okBuffer) {
		this.okBuffer = okBuffer;
	}

	public void setOkProcessBuffer(StringBuffer okProcessBuffer) {
		this.okProcessBuffer = okProcessBuffer;
	}

	public void setOkQty(int okQty) {
		this.okQty = okQty;
	}

	public void setPastDue(Collection<Object> pastDue) {
		this.pastDue = pastDue;
	}

	public void setPastDueBuffer(StringBuffer pastDueBuffer) {
		this.pastDueBuffer = pastDueBuffer;
	}

	public void setPastDueProcessBuffer(StringBuffer pastDueProcessBuffer) {
		this.pastDueProcessBuffer = pastDueProcessBuffer;
	}

	public void setPastDueQty(int pastDueQty) {
		this.pastDueQty = pastDueQty;
	}

	public void setProcessInstanceIds(Collection<Object> processInstanceIds) {
		this.processInstanceIds = processInstanceIds;
	}

	public void setRowIds(Collection<Object> rowIds) {
		this.rowIds = rowIds;
	}

	public void setTodo(Todo todo) {
		this.todo = todo;
	}

	public void setTotalQty(int totalQty) {
		this.totalQty = totalQty;
	}

}