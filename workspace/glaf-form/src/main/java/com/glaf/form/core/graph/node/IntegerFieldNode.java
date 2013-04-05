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
package com.glaf.form.core.graph.node;

import com.glaf.core.util.FieldType;

public class IntegerFieldNode extends TextFieldNode {

	private static final long serialVersionUID = 1L;

	public static final String NODE_TYPE = "intfield";

	protected String intFieldId;

	protected int maxValue = -1;

	protected int minValue = -1;

	public IntegerFieldNode() {
		setNodeType(NODE_TYPE);
	}

	@Override
	public int getDataType() {
		return FieldType.INTEGER_TYPE;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof IntegerFieldNode)) {
			return false;
		}
		IntegerFieldNode other = (IntegerFieldNode) obj;
		if (intFieldId == null) {
			if (other.intFieldId != null) {
				return false;
			}
		} else if (!intFieldId.equals(other.intFieldId)) {
			return false;
		}
		return true;
	}

	public String getNodeType() {
		return NODE_TYPE;
	}

	public String getIntFieldId() {
		if (intFieldId == null) {
			intFieldId = getName();
		}
		return intFieldId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((intFieldId == null) ? 0 : intFieldId.hashCode());
		return result;
	}

	public boolean isInteger(int value) {
		if (maxValue != -1 && minValue != -1 && maxValue > minValue) {

		}
		return true;
	}

	public void setIntFieldId(String intFieldId) {
		this.intFieldId = intFieldId;
	}

	public boolean validate(Object value) {
		if (value != null) {
			if (value instanceof Short) {
				Short d = (Short) value;
				return this.isInteger(d.intValue());
			} else if (value instanceof Integer) {
				Integer d = (Integer) value;
				return this.isInteger(d.intValue());
			} else if (value instanceof Long) {
				Long d = (Long) value;
				return this.isInteger(d.intValue());
			} else if (value instanceof Float) {
				Float d = (Float) value;
				return this.isInteger(d.intValue());
			} else if (value instanceof Double) {
				Double d = (Double) value;
				return this.isInteger(d.intValue());
			} else if (value instanceof String) {
				String tmp = value.toString();
				Integer d = Integer.valueOf(tmp);
				return this.isInteger(d.intValue());
			}
		}
		if (isRequired()) {
			if (value == null) {
				return false;
			}
		} else {
			if (value == null) {
				return true;
			}
		}
		return false;
	}
}