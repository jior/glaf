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

package com.glaf.report.runtime;

import java.util.Date;
import java.util.List;

public class ReportDataModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	protected String id;

	protected String parentId;

	protected String field1;

	protected String field2;

	protected String field3;

	protected String field4;

	protected String field5;

	protected String field6;

	protected String field7;

	protected String field8;

	protected String field9;

	protected String field10;

	protected String field11;

	protected String field12;

	protected String field13;

	protected String field14;

	protected String field15;

	protected String field16;

	protected String field17;

	protected String field18;

	protected String field19;

	protected String field20;

	protected int integer1;

	protected int integer2;

	protected int integer3;

	protected int integer4;

	protected int integer5;

	protected int integer6;

	protected int integer7;

	protected int integer8;

	protected int integer9;

	protected int integer10;

	protected double number1;

	protected double number2;

	protected double number3;

	protected double number4;

	protected double number5;

	protected double number6;

	protected double number7;

	protected double number8;

	protected double number9;

	protected double number10;

	protected Date date1;

	protected Date date2;

	protected Date date3;

	protected Date date4;

	protected Date date5;

	protected ReportDataModel parent;

	protected List<ReportDataModel> children = new java.util.ArrayList<ReportDataModel>();

	public ReportDataModel() {

	}

	public void addChild(ReportDataModel child) {
		if (children == null) {
			children = new java.util.ArrayList<ReportDataModel>();
		}
		child.setParent(this);
		child.setParentId(this.getId());
		children.add(child);
	}

	public List<ReportDataModel> getChildren() {
		return children;
	}

	public Date getDate1() {
		return date1;
	}

	public Date getDate2() {
		return date2;
	}

	public Date getDate3() {
		return date3;
	}

	public Date getDate4() {
		return date4;
	}

	public Date getDate5() {
		return date5;
	}

	public String getField1() {
		return field1;
	}

	public String getField10() {
		return field10;
	}

	public String getField11() {
		return field11;
	}

	public String getField12() {
		return field12;
	}

	public String getField13() {
		return field13;
	}

	public String getField14() {
		return field14;
	}

	public String getField15() {
		return field15;
	}

	public String getField16() {
		return field16;
	}

	public String getField17() {
		return field17;
	}

	public String getField18() {
		return field18;
	}

	public String getField19() {
		return field19;
	}

	public String getField2() {
		return field2;
	}

	public String getField20() {
		return field20;
	}

	public String getField3() {
		return field3;
	}

	public String getField4() {
		return field4;
	}

	public String getField5() {
		return field5;
	}

	public String getField6() {
		return field6;
	}

	public String getField7() {
		return field7;
	}

	public String getField8() {
		return field8;
	}

	public String getField9() {
		return field9;
	}

	public String getId() {
		return id;
	}

	public int getInteger1() {
		return integer1;
	}

	public int getInteger10() {
		return integer10;
	}

	public int getInteger2() {
		return integer2;
	}

	public int getInteger3() {
		return integer3;
	}

	public int getInteger4() {
		return integer4;
	}

	public int getInteger5() {
		return integer5;
	}

	public int getInteger6() {
		return integer6;
	}

	public int getInteger7() {
		return integer7;
	}

	public int getInteger8() {
		return integer8;
	}

	public int getInteger9() {
		return integer9;
	}

	public double getNumber1() {
		return number1;
	}

	public double getNumber10() {
		return number10;
	}

	public double getNumber2() {
		return number2;
	}

	public double getNumber3() {
		return number3;
	}

	public double getNumber4() {
		return number4;
	}

	public double getNumber5() {
		return number5;
	}

	public double getNumber6() {
		return number6;
	}

	public double getNumber7() {
		return number7;
	}

	public double getNumber8() {
		return number8;
	}

	public double getNumber9() {
		return number9;
	}

	public ReportDataModel getParent() {
		return parent;
	}

	public String getParentId() {
		return parentId;
	}

	public void setChildren(List<ReportDataModel> children) {
		this.children = children;
	}

	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	public void setDate2(Date date2) {
		this.date2 = date2;
	}

	public void setDate3(Date date3) {
		this.date3 = date3;
	}

	public void setDate4(Date date4) {
		this.date4 = date4;
	}

	public void setDate5(Date date5) {
		this.date5 = date5;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public void setField10(String field10) {
		this.field10 = field10;
	}

	public void setField11(String field11) {
		this.field11 = field11;
	}

	public void setField12(String field12) {
		this.field12 = field12;
	}

	public void setField13(String field13) {
		this.field13 = field13;
	}

	public void setField14(String field14) {
		this.field14 = field14;
	}

	public void setField15(String field15) {
		this.field15 = field15;
	}

	public void setField16(String field16) {
		this.field16 = field16;
	}

	public void setField17(String field17) {
		this.field17 = field17;
	}

	public void setField18(String field18) {
		this.field18 = field18;
	}

	public void setField19(String field19) {
		this.field19 = field19;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public void setField20(String field20) {
		this.field20 = field20;
	}

	public void setField3(String field3) {
		this.field3 = field3;
	}

	public void setField4(String field4) {
		this.field4 = field4;
	}

	public void setField5(String field5) {
		this.field5 = field5;
	}

	public void setField6(String field6) {
		this.field6 = field6;
	}

	public void setField7(String field7) {
		this.field7 = field7;
	}

	public void setField8(String field8) {
		this.field8 = field8;
	}

	public void setField9(String field9) {
		this.field9 = field9;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setInteger1(int integer1) {
		this.integer1 = integer1;
	}

	public void setInteger10(int integer10) {
		this.integer10 = integer10;
	}

	public void setInteger2(int integer2) {
		this.integer2 = integer2;
	}

	public void setInteger3(int integer3) {
		this.integer3 = integer3;
	}

	public void setInteger4(int integer4) {
		this.integer4 = integer4;
	}

	public void setInteger5(int integer5) {
		this.integer5 = integer5;
	}

	public void setInteger6(int integer6) {
		this.integer6 = integer6;
	}

	public void setInteger7(int integer7) {
		this.integer7 = integer7;
	}

	public void setInteger8(int integer8) {
		this.integer8 = integer8;
	}

	public void setInteger9(int integer9) {
		this.integer9 = integer9;
	}

	public void setNumber1(double number1) {
		this.number1 = number1;
	}

	public void setNumber10(double number10) {
		this.number10 = number10;
	}

	public void setNumber2(double number2) {
		this.number2 = number2;
	}

	public void setNumber3(double number3) {
		this.number3 = number3;
	}

	public void setNumber4(double number4) {
		this.number4 = number4;
	}

	public void setNumber5(double number5) {
		this.number5 = number5;
	}

	public void setNumber6(double number6) {
		this.number6 = number6;
	}

	public void setNumber7(double number7) {
		this.number7 = number7;
	}

	public void setNumber8(double number8) {
		this.number8 = number8;
	}

	public void setNumber9(double number9) {
		this.number9 = number9;
	}

	public void setParent(ReportDataModel parent) {
		this.parent = parent;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}