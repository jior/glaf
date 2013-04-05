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

public class NumberFieldNode extends TextFieldNode {

	private static final long serialVersionUID = 1L;

	public static final String NODE_TYPE = "numberfield";

	protected String numberFieldId;

	protected double maxValue = -1;

	protected double minValue = -1;

	public NumberFieldNode() {
		setNodeType(NODE_TYPE);
	}

	@Override
	public int getDataType() {
		return FieldType.DOUBLE_TYPE;
	}

	public String getJavaType() {
		return FieldType.getJavaType(FieldType.DOUBLE_TYPE);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof NumberFieldNode)) {
			return false;
		}
		NumberFieldNode other = (NumberFieldNode) obj;
		if (numberFieldId == null) {
			if (other.numberFieldId != null) {
				return false;
			}
		} else if (!numberFieldId.equals(other.numberFieldId)) {
			return false;
		}
		return true;
	}

	public String getNodeType() {
		return NODE_TYPE;
	}

	public String getNumberFieldId() {
		if (numberFieldId == null) {
			numberFieldId = getName();
		}
		return numberFieldId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((numberFieldId == null) ? 0 : numberFieldId.hashCode());
		return result;
	}

	public boolean isDouble(double value) {
		if (maxValue != -1 && minValue != -1 && maxValue > minValue) {

		}
		return true;
	}

	public void setNumberFieldId(String numberFieldId) {
		this.numberFieldId = numberFieldId;
	}

	public boolean validate(Object value) {
		if (value != null) {
			if (value instanceof Short) {
				Short d = (Short) value;
				return this.isDouble(d.doubleValue());
			} else if (value instanceof Integer) {
				Integer d = (Integer) value;
				return this.isDouble(d.doubleValue());
			} else if (value instanceof Long) {
				Long d = (Long) value;
				return this.isDouble(d.doubleValue());
			} else if (value instanceof Float) {
				Float d = (Float) value;
				return this.isDouble(d.doubleValue());
			} else if (value instanceof Double) {
				Double d = (Double) value;
				return this.isDouble(d.doubleValue());
			} else if (value instanceof String) {
				String tmp = value.toString();
				Double d = Double.valueOf(tmp);
				return this.isDouble(d.doubleValue());
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