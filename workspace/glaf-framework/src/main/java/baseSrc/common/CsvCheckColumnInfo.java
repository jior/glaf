package baseSrc.common;

/*列信息类*/
public class CsvCheckColumnInfo {
	private String value = "";
	private String colName = "";
	private int columnIndex = 0;
	private String colNullAble = "true";
	private String colChectRegex = "*";
	private String colChectMessage = "";
	private int colIndex = 0;
	public boolean flag = true;

	public String getColName() {
		return this.colName;
	}

	public void setColName(String colName) {
		if (colName != null) {
			this.colName = colName.trim();
		} else {
			this.colName = "";
		}
	}

	public int getColumnIndex() {
		return this.columnIndex;
	}

	public void setColumnIndex(String columnIndex) {
		try {
			value = columnIndex;
			if (value != null) {
				value = value.trim();
				if (value.equals("")) {
					value = "-1";// 默认为-1
				}
			} else {
				this.value = "-1";// 默认为-1
			}
			this.columnIndex = Integer.parseInt(value);
		} catch (Exception e) {
			flag = false;
		}
	}

	public String getColNullAble() {
		return this.colNullAble;
	}

	public void setColNullAble(String colNullAble) {
		if (colNullAble != null) {
			this.colNullAble = colNullAble.trim().toLowerCase();
		} else {
			// 默认为true
			this.colNullAble = "true";
		}

		// 配置文件若值不为TRUE或者FALSE则默认为TRUE
		if (!this.colNullAble.equals("true")
				&& !this.colNullAble.equals("false")) {
			this.colNullAble = "true";
		}
	}

	public String getColChectRegex() {
		return this.colChectRegex;
	}

	public void setColChectRegex(String colChectRegex) {
		if (colChectRegex != null) {
			this.colChectRegex = colChectRegex;
		} else {
			this.colChectRegex = "";
		}
	}

	public String getColChectMessag() {
		return this.colChectMessage;
	}

	public void setColChectMessage(String colChectMessage) {
		if (colChectMessage != null) {
			this.colChectMessage = colChectMessage;
		} else {
			this.colChectMessage = "";
		}
	}

	public int getColIndex() {
		return this.colIndex;
	}

	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
	}
}
