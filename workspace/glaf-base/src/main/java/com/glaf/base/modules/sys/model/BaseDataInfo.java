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

package com.glaf.base.modules.sys.model;

import java.io.Serializable;
import java.util.Date;

public class BaseDataInfo implements Serializable {
	private static final long serialVersionUID = 4103533989257821676L;
	private String code; // ����
	private int deep; // ��״�ṹ������Ϣ���
	protected String ext1;

	protected Date ext10;

	protected Long ext11;

	protected Long ext12;

	protected Long ext13;

	protected Long ext14;

	protected Long ext15;

	protected Double ext16;

	protected Double ext17;

	protected Double ext18;

	protected Double ext19;

	protected String ext2;

	protected Double ext20;

	protected String ext3;

	protected String ext4;

	protected Date ext5;

	protected Date ext6;

	protected Date ext7;

	protected Date ext8;

	protected Date ext9;

	private long id; // ������Ϣ�ڲ���ʶ��

	private String name; // ������Ϣ����

	private String no; // ������Ϣ��������

	private int parentId; // ������Ϣ�����ʶ��

	private String value;// ֵ

	private double value1; // �����ֵ����Ҫ�л��ʣ���λ�����

	private double value2;

	public BaseDataInfo() {

	}

	public String getCode() {
		return code;
	}

	public int getDeep() {
		return deep;
	}

	public String getExt1() {
		return ext1;
	}

	public Date getExt10() {
		return ext10;
	}

	public Long getExt11() {
		return ext11;
	}

	public Long getExt12() {
		return ext12;
	}

	public Long getExt13() {
		return ext13;
	}

	public Long getExt14() {
		return ext14;
	}

	public Long getExt15() {
		return ext15;
	}

	public Double getExt16() {
		return ext16;
	}

	public Double getExt17() {
		return ext17;
	}

	public Double getExt18() {
		return ext18;
	}

	public Double getExt19() {
		return ext19;
	}

	public String getExt2() {
		return ext2;
	}

	public Double getExt20() {
		return ext20;
	}

	public String getExt3() {
		return ext3;
	}

	public String getExt4() {
		return ext4;
	}

	public Date getExt5() {
		return ext5;
	}

	public Date getExt6() {
		return ext6;
	}

	public Date getExt7() {
		return ext7;
	}

	public Date getExt8() {
		return ext8;
	}

	public Date getExt9() {
		return ext9;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getNo() {
		return no;
	}

	public int getParentId() {
		return parentId;
	}

	public String getValue() {
		return value;
	}

	public double getValue1() {
		return value1;
	}

	public double getValue2() {
		return value2;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDeep(int deep) {
		this.deep = deep;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public void setExt10(Date ext10) {
		this.ext10 = ext10;
	}

	public void setExt11(Long ext11) {
		this.ext11 = ext11;
	}

	public void setExt12(Long ext12) {
		this.ext12 = ext12;
	}

	public void setExt13(Long ext13) {
		this.ext13 = ext13;
	}

	public void setExt14(Long ext14) {
		this.ext14 = ext14;
	}

	public void setExt15(Long ext15) {
		this.ext15 = ext15;
	}

	public void setExt16(Double ext16) {
		this.ext16 = ext16;
	}

	public void setExt17(Double ext17) {
		this.ext17 = ext17;
	}

	public void setExt18(Double ext18) {
		this.ext18 = ext18;
	}

	public void setExt19(Double ext19) {
		this.ext19 = ext19;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public void setExt20(Double ext20) {
		this.ext20 = ext20;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}

	public void setExt5(Date ext5) {
		this.ext5 = ext5;
	}

	public void setExt6(Date ext6) {
		this.ext6 = ext6;
	}

	public void setExt7(Date ext7) {
		this.ext7 = ext7;
	}

	public void setExt8(Date ext8) {
		this.ext8 = ext8;
	}

	public void setExt9(Date ext9) {
		this.ext9 = ext9;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setValue1(double value1) {
		this.value1 = value1;
	}

	public void setValue2(double value2) {
		this.value2 = value2;
	}
}