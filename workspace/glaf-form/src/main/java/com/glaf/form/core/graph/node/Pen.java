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

public class Pen {

	public static final int DEFAULT_PEN_TYPE = 0;

	public static final int TOP_PEN_TYPE = 1;

	public static final int BOTTOM_PEN_TYPE = 2;

	public static final int LEFT_PEN_TYPE = 3;

	public static final int RIGHT_PEN_TYPE = 4;

	protected long id = 0;

	protected double lineWidth = 0;

	protected String lineColor = null;

	protected String lineStyle = null;

	protected int penType = 0;

	protected BoxNode boxNode = null;

	public Pen() {

	}

	public BoxNode getBoxNode() {
		return boxNode;
	}

	public long getId() {
		return id;
	}

	public String getLineColor() {
		return lineColor;
	}

	public String getLineStyle() {
		return lineStyle;
	}

	public double getLineWidth() {
		return lineWidth;
	}

	public int getPenType() {
		return penType;
	}

	public void setBoxNode(BoxNode boxNode) {
		this.boxNode = boxNode;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLineColor(String lineColor) {
		this.lineColor = lineColor;
	}

	public void setLineStyle(String lineStyle) {
		this.lineStyle = lineStyle;
	}

	public void setLineWidth(double lineWidth) {
		this.lineWidth = lineWidth;
	}

	public void setPenType(int penType) {
		this.penType = penType;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boxNode == null) ? 0 : boxNode.hashCode());
		result = prime * result
				+ ((lineColor == null) ? 0 : lineColor.hashCode());
		result = prime * result
				+ ((lineStyle == null) ? 0 : lineStyle.hashCode());
		long temp;
		temp = Double.doubleToLongBits(lineWidth);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + penType;
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Pen other = (Pen) obj;
		if (boxNode == null) {
			if (other.boxNode != null)
				return false;
		} else if (!boxNode.equals(other.boxNode))
			return false;
		if (lineColor == null) {
			if (other.lineColor != null)
				return false;
		} else if (!lineColor.equals(other.lineColor))
			return false;
		if (lineStyle == null) {
			if (other.lineStyle != null)
				return false;
		} else if (!lineStyle.equals(other.lineStyle))
			return false;
		if (Double.doubleToLongBits(lineWidth) != Double
				.doubleToLongBits(other.lineWidth))
			return false;
		if (penType != other.penType)
			return false;
		return true;
	}

}