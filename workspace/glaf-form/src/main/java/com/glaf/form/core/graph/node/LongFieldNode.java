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

public class LongFieldNode extends TextFieldNode {

	private static final long serialVersionUID = 1L;

	public static final String NODE_TYPE = "longfield";

	protected String longFieldId;

	protected long maxValue = -1;

	protected long minValue = -1;

	public LongFieldNode() {
		setNodeType(NODE_TYPE);
	}

	public String getJavaType() {
		return FieldType.getJavaType(FieldType.LONG_TYPE);
	}

	@Override
	public int getDataType() {
		return FieldType.LONG_TYPE;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof LongFieldNode)) {
			return false;
		}
		LongFieldNode other = (LongFieldNode) obj;
		if (longFieldId == null) {
			if (other.longFieldId != null) {
				return false;
			}
		} else if (!longFieldId.equals(other.longFieldId)) {
			return false;
		}
		return true;
	}

	public String getNodeType() {
		return NODE_TYPE;
	}

	public String getLongFieldId() {
		if (longFieldId == null) {
			longFieldId = getName();
		}
		return longFieldId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((longFieldId == null) ? 0 : longFieldId.hashCode());
		return result;
	}

	public boolean isLong(long value) {
		if (maxValue != -1 && minValue != -1 && maxValue > minValue) {

		}
		return true;
	}

	public void setLongFieldId(String longFieldId) {
		this.longFieldId = longFieldId;
	}

	public boolean validate(Object value) {
		if (value != null) {
			if (value instanceof Short) {
				Short d = (Short) value;
				return this.isLong(d.longValue());
			} else if (value instanceof Integer) {
				Integer d = (Integer) value;
				return this.isLong(d.longValue());
			} else if (value instanceof Long) {
				Long d = (Long) value;
				return this.isLong(d.longValue());
			} else if (value instanceof Float) {
				Float d = (Float) value;
				return this.isLong(d.longValue());
			} else if (value instanceof Double) {
				Double d = (Double) value;
				return this.isLong(d.longValue());
			} else if (value instanceof String) {
				String tmp = value.toString();
				Integer d = Integer.valueOf(tmp);
				return this.isLong(d.longValue());
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