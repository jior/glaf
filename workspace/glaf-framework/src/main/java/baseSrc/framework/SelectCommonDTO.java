package baseSrc.framework;

public class SelectCommonDTO {
	
	private static final long serialVersionUID = 1L;
	
	private String[] values;
	private String[] labels;
	public void setValues(String[] values) {
		this.values = values;
	}
	public String[] getValues() {
		return values;
	}
	public void setLabels(String[] labels) {
		this.labels = labels;
	}
	public String[] getLabels() {
		return labels;
	}


}
