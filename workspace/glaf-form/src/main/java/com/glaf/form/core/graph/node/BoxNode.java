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

import java.util.HashSet;
import java.util.Set;

import com.glaf.form.core.graph.def.FormNode;

public class BoxNode extends FormNode {

	private static final long serialVersionUID = 1L;

	public static final String NODE_TYPE = "box";

	protected String boxId;

	protected Set<Pen> pens = new HashSet<Pen>();

	public BoxNode() {
		setNodeType(NODE_TYPE);
	}

	public void addPen(Pen pen) {
		if (pens == null) {
			pens = new HashSet<Pen>();
		}
		if (pen != null) {
			pen.setBoxNode(this);
			pens.add(pen);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof BoxNode)) {
			return false;
		}
		BoxNode other = (BoxNode) obj;
		if (boxId == null) {
			if (other.boxId != null) {
				return false;
			}
		} else if (!boxId.equals(other.boxId)) {
			return false;
		}
		return true;
	}

	public String getBoxId() {
		if (boxId == null) {
			boxId = getName();
		}
		return boxId;
	}

	public String getNodeType() {
		return NODE_TYPE;
	}

	public Set<Pen> getPens() {
		return pens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((boxId == null) ? 0 : boxId.hashCode());
		return result;
	}

	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}

	public void setPens(Set<Pen> pens) {
		this.pens = pens;
	}

	@Override
	public int getDataType() {
		return 0;
	}

}