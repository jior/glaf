/**
 * ArrayGridDetail对应array-grid.jsp中列表的各个字段，
 * 大小写严格区分
 */
package demoSrc.src;


public class ArrayGridDetail {
	private static final long serialVersionUID = 1L;
	
	private String company;
	private float price;
	private float change;
	private float pctChange;
	private String lastChange;
	
	public String getLastChange() {
		return lastChange;
	}
	public void setLastChange(String lastChange) {
		this.lastChange = lastChange;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getChange() {
		return change;
	}
	public void setChange(float change) {
		this.change = change;
	}
	public float getPctChange() {
		return pctChange;
	}
	public void setPctChange(float pctChange) {
		this.pctChange = pctChange;
	}
	
	
}
