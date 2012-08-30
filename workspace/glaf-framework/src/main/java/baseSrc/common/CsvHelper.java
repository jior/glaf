//================================================================================================
//项目名称 ：    基盘
//功    能 ：          CsvHelper
//文件名称 ：    CsvHelper.java                                   
//描    述 ：          Csv文件操作

//================================================================================================
//修改履历                                                                
//年 月 日		区分		所 属/担 当           		内 容									标识        
//----------   	----   	------------------- ---------------                          ------        
//2012/03/02   	编写   	Intasect/徐妙   	 新規作成                                                                            
//================================================================================================

package baseSrc.common;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CSV文件操作类：实现对CSV文件的的读及检查操作和写操作。 注意一个该列的对象只能单独执行读或者写操作！

 */
public class CsvHelper {
	private static LogHelper logger = new LogHelper(CsvHelper.class);
	/* 变量 */
	
	/**
	 * 默认分隔符【逗号】:','
	 */
	public final char DEFALUT_DELIMITER = ',';// 默认分隔符

	/**
	 * 检查模式－break
	 */
	public final String CHECK_Model_BREAK = "break";// 检查模式－break
	/**
	 * 检查模式－continue
	 */
	public final String CHECK_Model_CONTINUE = "continue";// 检查模式－continue
	
	private int DEFALUT_INT = -1;// 默认列计数

	private String DEFALUT_CsvRdOrWr_Flag = "NK";// 默认读写区分标识值

	private String CsvRd_Flag = "RD";// 读区分标识值

	private String CsvWr_Flag = "WR";// 写区分标识值

	private String rdOrWrFlag = this.DEFALUT_CsvRdOrWr_Flag;// 读写区分标识
	private String configFile = "";// 配置文件路径
	private String configFileXml = "cvsInOutCheckConfig.xml";// 配置文件名称
	private String csvTypeName = "";// 配置文件中table名称
	private String fileName;// CSV操作文件路径及名称

	private CsvReader reader;// CSV读操作类
	private CsvWriter writer;// CSV写操作类
	private boolean fileCheckFlag = false;// 文件是否已检查标识

	
	/* 公共值 配置信息 */
	private String charset = "UTF-8";// CSV操作编码 默认UTF-8/GB2312
	private char delimiter = this.DEFALUT_DELIMITER;// CSV操作分隔符

	private String checkModel = this.CHECK_Model_BREAK;// CSV操作检查模式

	private boolean isExistHeader = false;// CSV操作表头存在标识
	private int columnsCount = this.DEFALUT_INT;// CSV文件列计数

	private ArrayList<String> messageList = new ArrayList<String>();// CSV操作错误消息
	private ArrayList<CsvCheckColumnInfo> columsList;// 配置信息中列信息
	private ArrayList<String[]> csvHeaderList; // CSV返回数据表头
	private ArrayList<String[]> csvBodyList; // CSV返回数据内容
	private boolean isFileAddModel = false;// 写操作时添加模式标识
	private boolean preserveSpaces = false;// 写操作时空格有效标识

	/**
	 * 创建一个CsvHelper实例
	 * 
	 * @param fileName
	 *            操作文件对象对应的路径和名称
	 */
	public CsvHelper(String fileName) throws Exception {
		logger.info("fileName:" + fileName);
		this.fileName = fileName;
	}

	/**
	 * 创建一个CsvHelper实例
	 * 
	 * @param fileName
	 *            操作文件对象对应的路径和名称
	 * @param csvTypeName
	 *            CSV配置文件中对应table的name
	 */
	public CsvHelper(String fileName, String csvTypeName) throws Exception {
		logger.info("fileName:" + fileName);
		logger.info("csvTypeName:" + csvTypeName);
		this.fileName = fileName;
		this.csvTypeName = csvTypeName;
		this.getConfig(this.configFileXml);
	}
	
	/**
	 * 创建一个CsvHelper实例
	 * 
	 * @param fileName
	 *            操作文件对象对应的路径和名称
	 * @param csvTypeName
	 *            CSV配置文件中对应table的name
	 */
	public CsvHelper(String fileName, String csvTypeName,String configFileName) throws Exception {
		logger.info("fileName:" + fileName);
		logger.info("csvTypeName:" + csvTypeName);
		this.fileName = fileName;
		this.csvTypeName = csvTypeName;
		this.getConfig(configFileName);
	}

	/**
	 * 【读】属性（是否有表头）。 注意：重设该属性会重置CSV读操作处理器，部分属性会被清空。

	 * 
	 * @param isExistHeader
	 *            读CSV文件时，是否存在表头
	 */
	public void setIsExistHeader(Boolean isExistHeader) {
		this.isExistHeader = isExistHeader;
		// 重置实例化reader和返回对象。需要重新执行GetData函数。
		this.reader = null;
		this.csvBodyList = null;
		this.csvHeaderList = null;
	}

	/**
	 * 【读】属性（是否有表头）
	 * 
	 * @return 读CSV文件时，是否存在表头
	 */
	public Boolean getIsExistHeader() {
		return this.isExistHeader;
	}

	/**
	 * 【读/写】属性（分隔符）。 注意：重设该属性会重置CSV读和写操作处理器，部分属性会被清空。

	 * 
	 * @param delimiter
	 *            读CSV文件时，是否存在表头
	 */
	public void setDelimiter(char delimiter) {
		this.delimiter = delimiter;
		// 重置实例化reader writer和返回对象。需要重新执行GetData函数。

		if (writer != null) {
			writer.close();
		}
		this.writer = null;
		this.reader = null;
		this.csvBodyList = null;
		this.csvHeaderList = null;
	}

	/**
	 * 【读/写】属性（分隔符）
	 * 
	 * @return 分隔符

	 */
	public char getDelimiter() {
		return this.delimiter;
	}

	/**
	 * 【读/写】属性（编码方式）。 注意：重设该属性会重置CSV读和写操作处理器，部分属性会被清空。

	 * 
	 * @param delimiter
	 *            读写CSV文件时，编码方式
	 */
	public void setCharset(String charset) {
		this.charset = charset;
		// 重置实例化reader writer和返回对象。需要重新执行GetData函数。

		if (writer != null) {
			writer.close();
		}
		this.writer = null;
		this.reader = null;
		this.csvBodyList = null;
		this.csvHeaderList = null;
	}

	/**
	 * 【读/写】属性（编码方式）

	 * 
	 * @return 编码方式
	 */
	public String getCharset() {
		return this.charset;
	}

	/**
	 * 【写】属性（文件写入添加模式）。 注意：重设该属性会重置CSV写操作处理器。

	 * 
	 * @param isFileAddModel
	 *            文件写入添加模式。 true：文件末尾以添加的形式写入数据； false：重置文件，从开始写入数据。

	 */
	public void setIsFileAddModel(Boolean isFileAddModel) {
		this.isFileAddModel = isFileAddModel;
		// 重置实例化 writer和返回对象。需要重新执行GetData函数。

		if (writer != null) {
			writer.close();
		}
		this.writer = null;
	}

	/**
	 * 【读/写】属性（文件写入添加模式）

	 * 
	 * @return 文件写入添加模式
	 */
	public Boolean getIsFileAddModel() {
		return this.isFileAddModel;
	}

	/**
	 * 【读】属性（检查模式）。 注意：重设该属性部分属性会被清空。

	 * 
	 * @param checkModel
	 *            检查模式 continue：出错继续检查； break：出错跳出检查。

	 */
	public void setCheckModel(String checkModel) {
		if (checkModel != null
				&& CHECK_Model_CONTINUE.equals(checkModel.trim())) {
			this.checkModel = this.CHECK_Model_CONTINUE;
		} else {
			this.checkModel = this.CHECK_Model_BREAK;
		}
		this.messageList = new ArrayList<String>();
	}

	/**
	 * 【读】属性（检查模式）
	 * 
	 * @return 检查模式 continue：出错继续检查； break：出错跳出检查。

	 */
	public String getCheckModel() {
		return this.checkModel;
	}

	/**
	 * 【读】属性（ArrayList<String[]> csvHeaderList）CSV文件内容表头值

	 * 
	 * @return CSV文件内容表头值

	 */
	public ArrayList<String[]> getCsvHeaderList() {
		return this.csvHeaderList;
	}

	/**
	 * 【读】属性（ArrayList<String[]> csvBodyList）CSV文件内容除表头外的值

	 * 
	 * @return CSV文件内容除表头外的值

	 */
	public ArrayList<String[]> getCsvBodyList() {
		return this.csvBodyList;
	}

	/**
	 * 【读】属性（ArrayList<String> messageList）CSV检查返回消息

	 * 
	 * @return CSV检查返回消息

	 */
	public ArrayList<String> getMessageList() {
		return this.messageList;
	}

	/**
	 * 【读】属性（列数值）。

	 * 
	 * @param columnsCount
	 *            列数值

	 */
	public void setColumnsCount(int columnsCount) {
		this.columnsCount = columnsCount;
	}

	/**
	 * 【读】属性 列数值

	 * 
	 * @return 列数值

	 */
	public int getColumnsCount() {
		return columnsCount;
	}

	/**
	 * 【写】空格忽略模式。

	 * 
	 * @param preserveSpaces
	 *            空格忽略模式 ture--可以包含前后空格;false(默认)--自动TRIM前后空格。

	 */
	public void setPreserveSpaces(Boolean preserveSpaces) {
		this.preserveSpaces = preserveSpaces;
	}

	/**
	 * 【读】属性 空格忽略模式。

	 * 
	 * @return 空格忽略模式
	 */
	public Boolean getPreserveSpaces() {
		return preserveSpaces;
	}

	/**
	 * 【写】获取数据

	 * 
	 * @return 获取数据成功状态

	 */
	public boolean getData() throws Exception {
		this.setRdWrFalg(this.CsvRd_Flag);
		this.checkInitRdorWr();
		return this.getCsvData();
	}

	/**
	 * 【读】检查数据

	 * 
	 * @return 检查数据成功状态

	 */
	public boolean checkData() throws Exception {
		this.setRdWrFalg(this.CsvRd_Flag);
		this.checkInitRdorWr();
		return this.checkCsvData();
	}

	/**
	 * 【写】数据，适用于单列写入

	 */
	public void write(String content) throws Exception {
		this.setRdWrFalg(this.CsvWr_Flag);
		this.checkInitRdorWr();
		this.writer.write(content, this.preserveSpaces);
	}

	/**
	 * 【写】数据，写入换行
	 */
	public void endRecord() throws Exception {
		this.setRdWrFalg(this.CsvWr_Flag);
		this.checkInitRdorWr();
		this.writer.endRecord();
	}

	/**
	 * 【写】数据，写数据并自动换行
	 */
	public void writeRecord(String[] values) throws Exception {
		this.setRdWrFalg(this.CsvWr_Flag);
		this.checkInitRdorWr();
		this.writer.writeRecord(values, this.preserveSpaces);
	}

	/**
	 * 【写】数据，关闭并且保存。

	 */
	public void close() {
		if (this.writer != null) {
			this.writer.close();
		}
		this.writer = null;
	}

	private boolean getCsvData() {
		boolean rtTf = true;
		String rtMessage = "";
		messageList = new ArrayList<String>();
		ArrayList<String[]> csvList = new ArrayList<String[]>();
		csvHeaderList = new ArrayList<String[]>();
		csvBodyList = new ArrayList<String[]>();
		int count = 1;
		try {
			if (this.fileName == null || !checkFileOpean()) {
				rtMessage = "获取数据失败：文件路径不正确；";
				messageList.add(rtMessage);
				rtTf = false;
				return rtTf;
			}

			while (reader.readRecord()) { // 逐行读入数据
				String[] lineValue = reader.getValues();
				/**
				if(count == 1){
					if(null != lineValue && lineValue.length > 0){
						int skipSize = skipBomSize(lineValue[0].getBytes());
						lineValue[0] = lineValue[0].substring(skipSize, lineValue[0].length());
					}
				}
				**/
				if (this.isExistHeader == true && count == 1) {
					csvHeaderList.add(lineValue);
				} else {
					csvBodyList.add(lineValue);
				}
				count++;
			}
			reader.close();

			if (count == 1) {
				csvHeaderList = null;
				csvBodyList = null;

				rtMessage = "数据源为空；";
				messageList.add(rtMessage);

				rtTf = false;
				return rtTf;
			}
			return rtTf;
		} catch (Exception e) {
			rtTf = false;
			rtMessage = e.getMessage();
			messageList.add(rtMessage);
			logger.error("CsvHelper-getCsvData:" + e.getMessage());
			return rtTf;
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	private boolean checkCsvData() {
		String rtMessage = "";
		boolean rtTf = true;
		messageList = new ArrayList<String>();
		try {
			if (this.csvBodyList == null || this.csvBodyList.size() == 0) {
				rtMessage = "数据为空，检查失败；";
				messageList.add(rtMessage);
				rtTf = false;
				return rtTf;
			}

			Pattern pn = null;
			Matcher mr = null;

			// 逐行逐列数据检查

			for (int i = 0; i < csvBodyList.size(); i++) {
				String name = "";
				int columnIndex = -1;
				String regex = "";
				String message = "";
				String nullAble = "";
				String[] dataStrs = csvBodyList.get(i);
				// 在正确配置了列数的情况下，列数与配置的列数不对。

				if (dataStrs.length != this.columnsCount
						&& this.columnsCount != DEFALUT_INT) {
					rtMessage = "第" + (i + 1) + "行数据列数不对；";
					messageList.add(rtMessage);
					rtTf = false;

					// 跳出
					if ("break".equals(this.checkModel.trim().toLowerCase())) {
						return rtTf;
					}
					// 本行数据个数不对，不再继续进行本行正则表达式匹配检查

					continue;
				}

				// 未找到列配置信息退出检查

				if (columsList == null || columsList.size() == 0) {
					return rtTf;
				}
				for (int iCountL = 0; iCountL < columsList.size(); iCountL++) {
					name = columsList.get(iCountL).getColName();
					columnIndex = columsList.get(iCountL).getColumnIndex();
					regex = columsList.get(iCountL).getColChectRegex();
					message = columsList.get(iCountL).getColChectMessag();
					nullAble = columsList.get(iCountL).getColNullAble();
					if (columnIndex == -1) {
						rtMessage = "CSV配置文件出错，请检查配置文件！";
						messageList.add(rtMessage);
						rtTf = false;
						return rtTf;
					}

					// 数据集合不够
					if (dataStrs.length < columnIndex) {
						rtMessage = "第" + (i + 1) + "行数据列数不对；";
						messageList.add(rtMessage);
						rtTf = false;

						// 跳出
						if ("break"
								.equals(this.checkModel.trim().toLowerCase())) {
							return rtTf;
						}
						continue;
					}

					// 为空判读
					if (dataStrs[columnIndex - 1].equals("")
							&& ("false").equals(nullAble.toLowerCase())) {

						rtMessage = "第" + (i + 1) + "行" + (columnIndex)
								+ "列不能为空；";
						messageList.add(rtMessage);
						rtTf = false;

						// 跳出
						if ("break"
								.equals(this.checkModel.trim().toLowerCase())) {
							return rtTf;
						}
						// 本行数据个数不对，不再继续进行本行正则表达式匹配检查

						continue;
					}

					// 正则表达式判读

					if (!dataStrs[columnIndex - 1].equals("")
							&& !regex.equals("")) {
						pn = Pattern.compile(regex);
						mr = pn.matcher(dataStrs[columnIndex - 1]);
						//System.out.println("等匹配字符串---------->："+dataStrs[columnIndex - 1]);
						//System.out.println("正则表达式为---------->"+regex);
						if (!mr.matches()) {
							// 不匹配提示
							//System.out.println("匹配结果---------->false");
							rtMessage = "第" + (i + 1) + "行" + (columnIndex)
									+ "列" + message + "；";
							messageList.add(rtMessage);
							rtTf = false;

							// 跳出
							if ("break".equals(this.checkModel.trim()
									.toLowerCase())) {
								return rtTf;
							}
						}
						//System.out.println("匹配结果---------->true");
					}
				}
			}
			return rtTf;
		} catch (Exception e) {
			rtTf = false;
			rtMessage = e.getMessage();
			messageList.add(rtMessage);
			logger.error("CsvHelper-checkCsvData:" + e.getMessage());
			return rtTf;
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	// 设置writer reader Flag
	private void setRdWrFalg(String flag) throws IOException {
		// 为初始值则设置
		if (this.rdOrWrFlag.equals(this.DEFALUT_CsvRdOrWr_Flag)) {
			this.rdOrWrFlag = flag;
		}
		// 检查文件/为写模式
		if (this.rdOrWrFlag == this.CsvWr_Flag && !checkFileOpean()) {
			throw new java.io.IOException();
		}
	}

	// 实例化writer reader 对象
	private void checkInitRdorWr() throws Exception {
		this.messageList = new ArrayList<String>();
		if (this.rdOrWrFlag == this.CsvWr_Flag && this.writer == null) {
			if (fileName != null && !fileName.equals("")) {
				this.writer = new CsvWriter(this.fileName, this.delimiter,
						Charset.forName(this.charset));
				this.writer.isFileAddModel = this.isFileAddModel;
				return;
			}
		}

		if (this.rdOrWrFlag == this.CsvRd_Flag && reader == null) {
			if (fileName != null && !fileName.equals("")) {
				reader = new CsvReader(this.fileName, this.delimiter, Charset
						.forName(this.charset));
				return;
			}
		}
	}

	// 获取配置信息
	private void getConfig(String configFileName) throws Exception {
		// 获取部署后路径

		String ProjectPath = new File(getClass().getClassLoader().getResource(
				"").toURI()).getPath();
		// 设置配置文件路径
		this.configFile = ProjectPath + "/" + configFileName;
		File f = new File(this.configFile);
		SAXReader reader = new SAXReader();
		Document doc = reader.read(f);
		Element root = doc.getRootElement();
		Element fooA;
		Element fooB;
		String colName;
		String columnIndex;
		int iColIndex = 0;
		CsvCheckColumnInfo colInfo;
		for (Iterator i = root.elementIterator("table"); i.hasNext();) {
			fooA = (Element) i.next();
			if (this.csvTypeName.equals(fooA.attributeValue("name"))) {
				// 设置分隔符：默认','
				setSplit(fooA.elementText("split"));

				// 检查模式：默认break
				this.checkModel = fooA.elementText("checkModel");
				if (this.checkModel != null
						&& this.CHECK_Model_CONTINUE.equals(checkModel.trim()
								.toLowerCase())) {
					this.checkModel = this.CHECK_Model_CONTINUE;
				} else {
					this.checkModel = this.CHECK_Model_BREAK;
				}

				// 是否有表头:默认无表头

				String isExistHeaderStr = fooA.element("headers").elementText(
						"isExistHeader");
				if (isExistHeaderStr != null
						&& "true".equals(isExistHeaderStr.trim().toLowerCase())) {
					this.isExistHeader = true;
				} else {
					this.isExistHeader = false;
				}

				// 列总数
				this.columnsCount = this.intPars(fooA.element("columns")
						.attributeValue("count"));

				columsList = new ArrayList<CsvCheckColumnInfo>();
				for (Iterator ic = fooA.element("columns").elementIterator(
						"column"); ic.hasNext();) {
					fooB = (Element) ic.next();
					colInfo = new CsvCheckColumnInfo();
					iColIndex = iColIndex++;
					columnIndex = fooB.attributeValue("columnIndex");
					colName = fooB.attributeValue("name");
					colInfo.setColName(colName);
					colInfo.setColumnIndex(columnIndex);
					colInfo.setColNullAble(fooB.attributeValue("nullAble"));
					colInfo.setColChectRegex(fooB.elementText("chectRegex"));
					colInfo
							.setColChectMessage(fooB
									.elementText("chectMessage"));
					colInfo.setColIndex(iColIndex);
					columsList.add(colInfo);
				}
				break;
			}
		}
	}

	// 设置分隔符号
	private void setSplit(String splitStr) {
		try {
			char[] chs;
			if (splitStr != null) {
				chs = splitStr.trim().toCharArray();
				if (chs.length > 0) {
					switch (chs[0]) {
					// s表示空格
					case 's':
						this.delimiter = ' ';
						break;
					case 'S':
						this.delimiter = ' ';
						break;
					// t表示TAB
					case 't':
						this.delimiter = '\t';
						break;
					case 'T':
						this.delimiter = '\t';
						break;
					default:
						this.delimiter = chs[0];
						break;
					}
				} else {
					// 默认逗号
					this.delimiter = this.DEFALUT_DELIMITER;
				}
			}
		} catch (Exception e) {
			// 默认逗号
			this.delimiter = this.DEFALUT_DELIMITER;
		}
	}

	// 列行数转化

	private int intPars(String s) {
		int rtInt = DEFALUT_INT;
		try {
			if (s != null && !s.trim().equals("")) {
				rtInt = Integer.parseInt(s.trim());
			} else {
				rtInt = DEFALUT_INT;
			}
		} catch (Exception e) {
		} finally {
			return rtInt;
		}
	}

	private boolean checkFileOpean() {
		File f = new File(this.fileName);
		// 读模式检查文件存在性

		if (this.rdOrWrFlag == this.CsvRd_Flag) {
			if (!f.exists()) {
				fileCheckFlag = true;
				return false;
			}
		}
		// 写模式检查文件是否打开
		if (fileCheckFlag == false && this.rdOrWrFlag == this.CsvWr_Flag) {
			if (f.exists() && !f.renameTo(f)) {
				fileCheckFlag = true;
				return false;
			}
		}
		fileCheckFlag = true;
		return true;
	}
	
	/**
	 * 
	 * http://www.unicode.org/unicode/faq/utf_bom.html <br>
	 * 
	 * BOMs in byte length ordering: <br>
	 * 
	 * 
	 * 
	 * 00 00 FE FF = UTF-32, big-endian <br>
	 * 
	 * FF FE 00 00 = UTF-32, little-endian <br>
	 * 
	 * EF BB BF = UTF-8, <br>
	 * 
	 * FE FF = UTF-16, big-endian <br>
	 * 
	 * FF FE = UTF-16, little-endian <br>
	 * 
	 * 
	 * 
	 * @return
	 */
	private int skipBomSize(byte[] header) {

		if (header == null || header.length == 0) {
			return 0;
		}

		if ((header[0] == (byte) 0x00) && (header[1] == (byte) 0x00)
		&& (header[2] == (byte) 0xFE) && (header[3] == (byte) 0xFF)) {
			// encoding = "UTF-32BE";
			return 4;
		} else if ((header[0] == (byte) 0xFF) && (header[1] == (byte) 0xFE)
		&& (header[2] == (byte) 0x00) && (header[3] == (byte) 0x00)) {
			// encoding = "UTF-32LE";
			return 4;
		} else if ((header[0] == (byte) 0xEF) && (header[1] == (byte) 0xBB)
		&& (header[2] == (byte) 0xBF)) {
			// encoding = "UTF-8";
			return 3;
		} else if ((header[0] == (byte) 0xFE) && (header[1] == (byte) 0xFF)) {
			// encoding = "UTF-16BE";
			return 2;
		} else if ((header[0] == (byte) 0xFF) && (header[1] == (byte) 0xFE)) {
			// encoding = "UTF-16LE";
			return 2;
		}
		return 0;
	}
}
