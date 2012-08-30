package com.glaf.base.modules.workspace.actionform;

import org.apache.struts.action.ActionForm;

import com.glaf.base.modules.sys.model.SysUser;

public class MessageForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6157447503772238063L;

	private long id;

	private int type;
	
	private int sysType;
	
	private SysUser sender;

	private SysUser recver;

	private String title;

	private String content;

	private String createDate;

	private int readed;

	private int category;
	
	private String toEmail;

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getReaded() {
		return readed;
	}

	public void setReaded(int readed) {
		this.readed = readed;
	}

	public SysUser getRecver() {
		return recver;
	}

	public void setRecver(SysUser recver) {
		this.recver = recver;
	}

	public SysUser getSender() {
		return sender;
	}

	public void setSender(SysUser sender) {
		this.sender = sender;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSysType() {
		return sysType;
	}

	public void setSysType(int sysType) {
		this.sysType = sysType;
	}

}
