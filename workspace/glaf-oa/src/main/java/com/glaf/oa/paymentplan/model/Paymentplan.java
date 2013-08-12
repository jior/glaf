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
package com.glaf.oa.paymentplan.model;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.oa.paymentplan.util.*;

@Entity
@Table(name = "oa_paymentplan")
public class Paymentplan implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "planid", nullable = false)
	protected Long planid;

	@Column(name = "budgetid")
	protected Long budgetid;

	@Column(name = "paymemtsum")
	protected Double paymemtsum;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "paymentdate")
	protected Date paymentdate;

	@Column(name = "sequence")
	protected Integer sequence;

	@Column(name = "createby")
	protected String createBy;

	public Paymentplan() {

	}

	public Long getPlanid() {
		return this.planid;
	}

	public void setPlanid(Long planid) {
		this.planid = planid;
	}

	public Long getBudgetid() {
		return this.budgetid;
	}

	public Double getPaymemtsum() {
		return this.paymemtsum;
	}

	public Date getPaymentdate() {
		return this.paymentdate;
	}

	public Integer getSequence() {
		return this.sequence;
	}

	public void setBudgetid(Long budgetid) {
		this.budgetid = budgetid;
	}

	public void setPaymemtsum(Double paymemtsum) {
		this.paymemtsum = paymemtsum;
	}

	public void setPaymentdate(Date paymentdate) {
		this.paymentdate = paymentdate;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paymentplan other = (Paymentplan) obj;
		if (planid == null) {
			if (other.planid != null)
				return false;
		} else if (!planid.equals(other.planid))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((planid == null) ? 0 : planid.hashCode());
		return result;
	}

	public Paymentplan jsonToObject(JSONObject jsonObject) {
		return PaymentplanJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return PaymentplanJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return PaymentplanJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

}