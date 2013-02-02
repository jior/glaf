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

package com.glaf.base.modules.others.model;

import java.io.Serializable;

public class StatusView implements Serializable{

	private static final long serialVersionUID = 5656285863164782639L;
	
//	private long id;//相关单的Id/
	private int type;//节点类型，即在页面上显示为第几个节点，从0开始
	private String name;//节点名称
	private int status;//节点状态
	private String src;//图片路径，根据节点状态来显示
	private String path;//点击图片时的链接路径（相关单的详细信息） 
	private String tooltip;//提示
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSrc() {
		return src;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
		
		switch(status){
		case 1://流转过的节点
			this.src = "/module/others/status_see/images/green.jpg";
			break;
		case 2://快要过期限仍未通过的节点
			this.src = "/module/others/status_see/images/yellow.jpg";
			break;
		case 3://超过期限仍未通过的节点
			this.src = "/module/others/status_see/images/red.jpg";
			break;
		default://未流转到的节点
			this.src = "/module/others/status_see/images/black.jpg";
			break;
		}
	}
	public String getTooltip() {
		return tooltip;
	}
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
//	public long getId(){
//		return id;
//	}
//	public void setId(long id){
//		this.id = id;
//	}
	public String getPath(){
		return path;
	}
	public void setPath(String path){
		this.path = path;
	}
}