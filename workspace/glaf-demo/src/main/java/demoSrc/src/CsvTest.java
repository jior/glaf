package demoSrc.src;

import java.io.File;

import junit.framework.TestCase;
import baseSrc.common.CsvHelper;

public class CsvTest extends TestCase{
	// 测试入口
	public void test() throws Exception {
		// 001创建读测试文件
		System.out.println("001:--------------------------");
		test001();
		// 002fileName为空测试,或者为错误
		System.out.println("002:--------------------------");
		test002();
		// 003fileName路径对应文件为只读进行写操作测试
		System.out.println("003:--------------------------");
		test003();
		// 004获取属性测试
		System.out.println("004:--------------------------");
		test004();
		// 005有配置文件 属性设置读/写测试及其异常测试
		System.out.println("005:--------------------------");
		test005();
		// 006无配置文件 属性测试读/写测试及其异常测试
		System.out.println("006:--------------------------");
		test006();
		// 007获取数据测试
		System.out.println("007:--------------------------");
		test007();
		// 008检查数据测试
		System.out.println("008:--------------------------");
		test008();
		// 009写文件测试
		System.out.println("009:--------------------------");
		test009();
		// 010读写交换异常测试
		System.out.println("010:--------------------------");
		test010();
		// 011无配置文件配置无效异常测试
		System.out.println("011:--------------------------");
		test011();
	}

	private void test001() {
		try {
			CsvHelper csvHper0 = new CsvHelper("d:/danti001.csv");
			CsvHelper csvHper1 = new CsvHelper("d:/danti002.csv");

			String[] A1 = new String[] { "1", "你", "aa", "語り" };
			String[] A2 = new String[] { "2", "真", "aa", "くす" };
			String[] A3 = new String[] { "3", "的", "aa", "ちゅ" };
			String[] A4 = new String[] { "4", "好", "aa", "ん" };
			String[] A5 = new String[] { "5", "漂", "aa", "ちこ" };
			String[] A6 = new String[] { "6", "亮", "aa", "是很" };
			String[] A7 = new String[] { "7", "是", "aa", "日本語" };
			String[] A8 = new String[] { "8", "不", "aa", "界上de語" };
			String[] A9 = new String[] { "9", "哈", "aa", "->!!@!" };

			String[] B0 = new String[] { "列1", "列2", "列3", "列4" };
			String[] B1 = new String[] { "1", "萨法", "", "!#!@" };
			String[] B2 = new String[] { "2", "2341定身法", "ds", "aa" };
			String[] B3 = new String[] { "3", "1213", "", "" };
			String[] B4 = new String[] { "4", "风度aa12", "R", "a" };
			String[] B5 = new String[] { "5", "定身法", "1", "1231" };
			String[] B6 = new String[] { "6", "发达省份", "2", "31" };
			String[] B7 = new String[] { "7", "123", "aa", " aa" };
			String[] B8 = new String[] { "8", "3123", "aa", "fds " };
			String[] B9 = new String[] { "9", "发电公司", "aa", "heh12 " };

			csvHper0.writeRecord(A1);
			csvHper0.writeRecord(A2);
			csvHper0.writeRecord(A3);
			csvHper0.writeRecord(A4);
			csvHper0.writeRecord(A5);
			csvHper0.writeRecord(A6);
			csvHper0.writeRecord(A7);
			csvHper0.writeRecord(A8);
			csvHper0.writeRecord(A9);

			csvHper1.writeRecord(B0);
			csvHper1.writeRecord(B1);
			csvHper1.writeRecord(B2);
			csvHper1.writeRecord(B3);
			csvHper1.writeRecord(B4);
			csvHper1.writeRecord(B5);
			csvHper1.writeRecord(B6);
			csvHper1.writeRecord(B7);
			csvHper1.writeRecord(B8);
			csvHper1.writeRecord(B9);

			csvHper0.close();
			csvHper1.close();
		} catch (Exception ex) {
			System.out.println("001:" + ex.getMessage());
		}
	}

	private void test002() {
		// 为空读
		try {
			CsvHelper csvHper0 = new CsvHelper("");
			// 获取数据
			if (!csvHper0.getData()) {
				// 获取失败
				for (int i = 0; i < csvHper0.getMessageList().size(); i++) {
					System.out.println(csvHper0.getMessageList().get(i));
				}
			}
		} catch (Exception ex) {
			System.out.print("002,为空读：" + ex.getMessage());
		}

		// 为空写
		try {
			CsvHelper csvHper1 = new CsvHelper("");
			csvHper1.write("");
			csvHper1.endRecord();
			csvHper1.close();
		} catch (Exception ex) {
			System.out.println("002,为空写" + ex.getMessage());
		}

		// 为错误读
		try {
			CsvHelper csvHper0 = new CsvHelper("p:/danti001.csv");
			// 获取数据
			if (!csvHper0.getData()) {
				// 获取失败
				for (int i = 0; i < csvHper0.getMessageList().size(); i++) {
					System.out.println(csvHper0.getMessageList().get(i));
				}
			}

		} catch (Exception ex) {
			System.out.println("002,为错误读：" + ex.getMessage());
		}

		// 为错误写
		try {
			CsvHelper csvHper1 = new CsvHelper("p:/danti001.csv");
			csvHper1.write("");
			csvHper1.endRecord();
			csvHper1.close();
		} catch (Exception ex) {
			System.out.println("002,为错误写" + ex.getMessage());
		}
	}

	private void test003() {
		try {
			File file = new File("d:/danti003.csv");
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			// 设置只读
			file.setReadOnly();
			CsvHelper csvHper0 = new CsvHelper("d:/danti003.csv");
			csvHper0.write("测试");
			csvHper0.close();

		} catch (Exception ex) {
			System.out.println("003" + ex.getMessage());
		}
	}

	private void test004() {
		try {
			CsvHelper csvHper0 = new CsvHelper("d:/danti001.csv", "测试001");
			System.out.println("getCharset:" + csvHper0.getCharset());
			System.out.println("getCheckModel:" + csvHper0.getCheckModel());
			System.out.println("getColumnsCount:" + csvHper0.getColumnsCount());
			System.out.print("getCharset:");
			System.out.println(csvHper0.getDelimiter());
			System.out.println("getIsExistHeader:"
					+ csvHper0.getIsExistHeader());
			System.out.println("getIsFileAddModel:"
					+ csvHper0.getIsFileAddModel());
			System.out.println("getPreserveSpaces:"
					+ csvHper0.getPreserveSpaces());
		} catch (Exception ex) {
			System.out.println("004" + ex.getMessage());
		}

	}

	private void test005() {
		try {
			CsvHelper csvHper0 = new CsvHelper("d:/danti001.csv", "测试001");
			// 获取数据
			if (!csvHper0.getData()) {
				// 获取失败
				for (int i = 0; i < csvHper0.getMessageList().size(); i++) {
					System.out.println(csvHper0.getMessageList().get(i));
				}
			}

			// 遍历CSV读取的结果
			String dataStrs = "";
			// 表头。
			if (csvHper0.getCsvHeaderList() != null) {
				System.out.println("---表头---：");
				for (int i = 0; i < csvHper0.getCsvHeaderList().size(); i++) {
					for (int j = 0; j < csvHper0.getCsvHeaderList().get(i).length; j++) {
						dataStrs = dataStrs + "行/列【" + i + "/" + j + "】"
								+ csvHper0.getCsvHeaderList().get(i)[j];
					}
					System.out.println(dataStrs);
				}
			}

			if (csvHper0.getCsvBodyList() != null) {
				System.out.println("---内容---：");
				for (int i = 0; i < csvHper0.getCsvBodyList().size(); i++) {
					dataStrs = "";
					for (int j = 0; j < csvHper0.getCsvBodyList().get(i).length; j++) {
						dataStrs = dataStrs + "行/列【" + i + "/" + j + "】"
								+ csvHper0.getCsvBodyList().get(i)[j] + "; ";
					}
					System.out.println(dataStrs);
				}
			}

			// 设置为有列头
			csvHper0.setIsExistHeader(true);
			// 获取数据
			if (!csvHper0.getData()) {
				// 获取失败
				for (int i = 0; i < csvHper0.getMessageList().size(); i++) {
					System.out.println(csvHper0.getMessageList().get(i));
				}
				return;
			}

			// 遍历CSV读取的结果
			dataStrs = "";
			// 表头。
			if (csvHper0.getCsvHeaderList() != null) {
				System.out.println("---表头---：");
				for (int i = 0; i < csvHper0.getCsvHeaderList().size(); i++) {
					for (int j = 0; j < csvHper0.getCsvHeaderList().get(i).length; j++) {
						dataStrs = dataStrs + "行/列【" + i + "/" + j + "】"
								+ csvHper0.getCsvHeaderList().get(i)[j];
					}
					System.out.println(dataStrs);
				}
			}

			if (csvHper0.getCsvBodyList() != null) {
				System.out.println("---内容---：");
				for (int i = 0; i < csvHper0.getCsvBodyList().size(); i++) {
					dataStrs = "";
					for (int j = 0; j < csvHper0.getCsvBodyList().get(i).length; j++) {
						dataStrs = dataStrs + "行/列【" + i + "/" + j + "】"
								+ csvHper0.getCsvBodyList().get(i)[j] + "; ";
					}
					System.out.println(dataStrs);
				}
			}

			CsvHelper csvHper1 = new CsvHelper("d:/danti005.csv", "测试001");
			csvHper1.writeRecord(new String[] { "aa", "bb" });
			csvHper1.writeRecord(new String[] { "aa", "bb" });
			csvHper1.close();

			CsvHelper csvHper2 = new CsvHelper("d:/danti006.csv", "测试001");
			csvHper2.setDelimiter('\t');
			csvHper2.writeRecord(new String[] { "aa", "bb" });
			csvHper2.writeRecord(new String[] { "aa", "bb" });
			csvHper2.close();

			// .........

		} catch (Exception ex) {
			System.out.println("005" + ex.getMessage());
		}
	}

	private void test006() {
		try {
			CsvHelper csvHper0 = new CsvHelper("d:/danti001.csv");
			// 获取数据
			if (!csvHper0.getData()) {
				// 获取失败
				for (int i = 0; i < csvHper0.getMessageList().size(); i++) {
					System.out.println(csvHper0.getMessageList().get(i));
				}
				return;
			}

			// 遍历CSV读取的结果
			String dataStrs = "";
			// 表头。
			if (csvHper0.getCsvHeaderList() != null) {
				System.out.println("---表头---：");
				for (int i = 0; i < csvHper0.getCsvHeaderList().size(); i++) {
					for (int j = 0; j < csvHper0.getCsvHeaderList().get(i).length; j++) {
						dataStrs = dataStrs + "行/列【" + i + "/" + j + "】"
								+ csvHper0.getCsvHeaderList().get(i)[j];
					}
					System.out.println(dataStrs);
				}
			}

			if (csvHper0.getCsvBodyList() != null) {
				dataStrs = "";
				System.out.println("---内容---：");
				for (int i = 0; i < csvHper0.getCsvBodyList().size(); i++) {
					dataStrs = "";
					for (int j = 0; j < csvHper0.getCsvBodyList().get(i).length; j++) {
						dataStrs = dataStrs + "行/列【" + i + "/" + j + "】"
								+ csvHper0.getCsvBodyList().get(i)[j] + "; ";
					}
					System.out.println(dataStrs);
				}
			}

			// 设置为有列头
			csvHper0.setIsExistHeader(true);
			// 获取数据
			if (!csvHper0.getData()) {
				// 获取失败
				for (int i = 0; i < csvHper0.getMessageList().size(); i++) {
					System.out.println(csvHper0.getMessageList().get(i));
				}
				return;
			}

			// 遍历CSV读取的结果
			dataStrs = "";
			// 表头。
			if (csvHper0.getCsvHeaderList() != null) {
				System.out.println("---表头---：");
				for (int i = 0; i < csvHper0.getCsvHeaderList().size(); i++) {
					for (int j = 0; j < csvHper0.getCsvHeaderList().get(i).length; j++) {
						dataStrs = dataStrs + "行/列【" + i + "/" + j + "】"
								+ csvHper0.getCsvHeaderList().get(i)[j];
					}
					System.out.println(dataStrs);
				}
			}

			if (csvHper0.getCsvBodyList() != null) {
				System.out.println("---内容---：");
				for (int i = 0; i < csvHper0.getCsvBodyList().size(); i++) {
					dataStrs = "";
					for (int j = 0; j < csvHper0.getCsvBodyList().get(i).length; j++) {
						dataStrs = dataStrs + "行/列【" + i + "/" + j + "】"
								+ csvHper0.getCsvBodyList().get(i)[j] + "; ";
					}
					System.out.println(dataStrs);
				}
			}

			CsvHelper csvHper1 = new CsvHelper("d:/danti007.csv");
			csvHper1.writeRecord(new String[] { "aa", "bb" });
			csvHper1.writeRecord(new String[] { "aa", "bb" });
			csvHper1.close();

			CsvHelper csvHper2 = new CsvHelper("d:/danti008.csv");
			csvHper2.setDelimiter('\t');
			csvHper2.setPreserveSpaces(true);
			csvHper2.setIsFileAddModel(true);
			csvHper2.writeRecord(new String[] { "cc", "dd" });
			csvHper2.writeRecord(new String[] { "ee", "ff" });
			csvHper2.write("gg");
			csvHper2.write(" hh ");
			csvHper2.endRecord();
			csvHper2.close();

			// .........

		} catch (Exception ex) {
			System.out.println("006" + ex.getMessage());
		}
	}

	private void test007() {
		try {
			CsvHelper csvHper0 = new CsvHelper("d:/danti001.csv");
			// 获取数据
			if (!csvHper0.getData()) {
				// 获取失败
				for (int i = 0; i < csvHper0.getMessageList().size(); i++) {
					System.out.println(csvHper0.getMessageList().get(i));
				}
				return;
			}

			// 遍历CSV读取的结果
			String dataStrs = "";
			// 表头。
			if (csvHper0.getCsvHeaderList() != null) {
				System.out.println("---表头---：");
				for (int i = 0; i < csvHper0.getCsvHeaderList().size(); i++) {
					for (int j = 0; j < csvHper0.getCsvHeaderList().get(i).length; j++) {
						dataStrs = dataStrs + "行/列【" + i + "/" + j + "】"
								+ csvHper0.getCsvHeaderList().get(i)[j];
					}
					System.out.println(dataStrs);
				}
			}

			if (csvHper0.getCsvBodyList() != null) {
				System.out.println("---内容---：");
				for (int i = 0; i < csvHper0.getCsvBodyList().size(); i++) {
					dataStrs = "";
					for (int j = 0; j < csvHper0.getCsvBodyList().get(i).length; j++) {
						dataStrs = dataStrs + "行/列【" + i + "/" + j + "】"
								+ csvHper0.getCsvBodyList().get(i)[j] + "; ";
					}
					System.out.println(dataStrs);
				}
			}

			CsvHelper csvHper1 = new CsvHelper("d:/danti002.csv", "测试001");
			// 获取数据
			if (!csvHper1.getData()) {
				// 获取失败
				for (int i = 0; i < csvHper1.getMessageList().size(); i++) {
					System.out.println(csvHper1.getMessageList().get(i));
				}
				return;
			}

			// 遍历CSV读取的结果
			dataStrs = "";
			// 表头。
			if (csvHper1.getCsvHeaderList() != null) {

				System.out.println("---表头---：");
				for (int i = 0; i < csvHper1.getCsvHeaderList().size(); i++) {
					for (int j = 0; j < csvHper1.getCsvHeaderList().get(i).length; j++) {
						dataStrs = dataStrs + "行/列【" + i + "/" + j + "】"
								+ csvHper1.getCsvHeaderList().get(i)[j];
					}
				}
			}

			if (csvHper1.getCsvBodyList() != null) {
				System.out.println("---内容---：");
				dataStrs = "";
				for (int i = 0; i < csvHper1.getCsvBodyList().size(); i++) {
					dataStrs = "";
					for (int j = 0; j < csvHper1.getCsvBodyList().get(i).length; j++) {
						dataStrs = dataStrs + "行/列【" + i + "/" + j + "】"
								+ csvHper1.getCsvBodyList().get(i)[j] + "; ";
					}
					System.out.println(dataStrs);
				}
			}
		} catch (Exception ex) {
			System.out.println("007" + ex.getMessage());
		}
	}

	private void test008() {
		try {
			CsvHelper csvHper0 = new CsvHelper("d:/danti002.csv", "测试001");
			// 获取数据
			if (!csvHper0.getData()) {
				// 获取失败
				for (int i = 0; i < csvHper0.getMessageList().size(); i++) {
					System.out.println(csvHper0.getMessageList().get(i));
				}
				return;
			}

			// 检查数据
			if (!csvHper0.checkData()) {
				// 检查不通过
				for (int i = 0; i < csvHper0.getMessageList().size(); i++) {
					System.out.println(csvHper0.getMessageList().get(i));
				}
				// return;
			}

			CsvHelper csvHper1 = new CsvHelper("d:/danti002.csv", "测试002");
			// 获取数据
			if (!csvHper1.getData()) {
				// 获取失败
				for (int i = 0; i < csvHper1.getMessageList().size(); i++) {
					System.out.println(csvHper1.getMessageList().get(i));
				}
				return;
			}

			// 检查数据
			if (!csvHper1.checkData()) {
				// 检查不通过
				for (int i = 0; i < csvHper1.getMessageList().size(); i++) {
					System.out.println(csvHper1.getMessageList().get(i));
				}
				return;
			}
		} catch (Exception ex) {
			System.out.println("008" + ex.getMessage());
		}
	}

	private void test009() {
		try {
			CsvHelper csvHper1 = new CsvHelper("d:/danti009.csv");
			csvHper1.setPreserveSpaces(false);
			csvHper1.writeRecord(new String[] { "aa", "bb" });
			csvHper1.endRecord();
			csvHper1.endRecord();
			csvHper1.writeRecord(new String[] { "kk", "  hh  " });
			csvHper1.close();

			CsvHelper csvHper2 = new CsvHelper("d:/danti010.csv");
			csvHper2.setDelimiter(',');
			csvHper2.setPreserveSpaces(true);
			csvHper2.setIsFileAddModel(true);
			csvHper2.writeRecord(new String[] { "cc", "dd" });
			csvHper2.writeRecord(new String[] { "ee", "ff" });
			csvHper2.write("gg");
			csvHper2.write(" hh ");
			csvHper2.endRecord();
			csvHper2.close();

			CsvHelper csvHper3 = new CsvHelper("d:/danti011.csv");
			csvHper3.setDelimiter('\t');
			csvHper3.setPreserveSpaces(true);
			csvHper3.setIsFileAddModel(true);
			csvHper3.writeRecord(new String[] { "cc", "dd" });
			csvHper3.writeRecord(new String[] { "ee", "ff" });
			csvHper3.write("gg");
			csvHper3.write(" hh ");
			csvHper3.endRecord();
			csvHper3.close();

			CsvHelper csvHper4 = new CsvHelper("d:/danti014.csv");
			csvHper4.setDelimiter(' ');
			csvHper4.setPreserveSpaces(true);
			csvHper4.setIsFileAddModel(false);
			csvHper4.writeRecord(new String[] { "cc", "dd" });
			csvHper4.writeRecord(new String[] { "ee", "ff" });
			csvHper4.setIsFileAddModel(true);
			csvHper4.writeRecord(new String[] { "cc", "dd" });
			csvHper4.writeRecord(new String[] { "ee", "ff" });
			csvHper4.write("gg");
			csvHper4.write(" hh ");
			csvHper4.endRecord();
			csvHper4.close();
		} catch (Exception ex) {
			System.out.println("009" + ex.getMessage());
		}
	}

	private void test010() throws Exception {
		CsvHelper csvHper1 = new CsvHelper("d:/danti001.csv");
		try {
			csvHper1.getData();
			csvHper1.write("aaa");
		} catch (Exception ex) {
			System.out.println("010" + ex.getMessage());
		} finally {
			if (csvHper1 != null) {
				csvHper1.close();
			}
		}
		CsvHelper csvHper2 = new CsvHelper("d:/danti012.csv");
		try {
			csvHper2.write("aaa");
			// 获取数据
			if (!csvHper2.getData()) {
				// 获取失败
				for (int i = 0; i < csvHper2.getMessageList().size(); i++) {
					System.out.println(csvHper2.getMessageList().get(i));
				}
			}
		} catch (Exception ex) {
			System.out.println("010" + ex.getMessage());
		} finally {
			if (csvHper1 != null) {
				csvHper1.close();
			}
		}
	}

	private void test011() {
		try {
			CsvHelper csvHper0 = new CsvHelper("d:/danti001.csv", "测试003");
			// 获取数据
			if (!csvHper0.getData()) {
				// 获取失败
				for (int i = 0; i < csvHper0.getMessageList().size(); i++) {
					System.out.println(csvHper0.getMessageList().get(i));
				}
				return;
			}

			// 检查数据
			if (!csvHper0.checkData()) {
				// 检查不通过
				for (int i = 0; i < csvHper0.getMessageList().size(); i++) {
					System.out.println(csvHper0.getMessageList().get(i));
				}
				// return;
			}

			CsvHelper csvHper1 = new CsvHelper("d:/danti002.csv", "测试004");
			// 获取数据
			if (!csvHper1.getData()) {
				// 获取失败
				for (int i = 0; i < csvHper1.getMessageList().size(); i++) {
					System.out.println(csvHper1.getMessageList().get(i));
				}
				return;
			}

			// 检查数据
			if (!csvHper1.checkData()) {
				// 检查不通过
				for (int i = 0; i < csvHper1.getMessageList().size(); i++) {
					System.out.println(csvHper1.getMessageList().get(i));
				}
				// return;
			}
		} catch (Exception ex) {
			System.out.println("011" + ex.getMessage());
		}
	}
}
