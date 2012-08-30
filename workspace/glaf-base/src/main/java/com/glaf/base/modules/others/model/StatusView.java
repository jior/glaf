package com.glaf.base.modules.others.model;

import java.io.Serializable;

public class StatusView implements Serializable{

	private static final long serialVersionUID = 5656285863164782639L;
	
//	private long id;//��ص���Id/
	private int type;//�ڵ����ͣ�����ҳ������ʾΪ�ڼ����ڵ㣬��0��ʼ
	private String name;//�ڵ�����
	private int status;//�ڵ�״̬
	private String src;//ͼƬ·�������ݽڵ�״̬����ʾ
	private String path;//���ͼƬʱ������·������ص�����ϸ��Ϣ�� 
	private String tooltip;//��ʾ
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
		case 1://��ת���Ľڵ�
			this.src = "/module/others/status_see/images/green.jpg";
			break;
		case 2://��Ҫ��������δͨ���Ľڵ�
			this.src = "/module/others/status_see/images/yellow.jpg";
			break;
		case 3://����������δͨ���Ľڵ�
			this.src = "/module/others/status_see/images/red.jpg";
			break;
		default://δ��ת���Ľڵ�
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
