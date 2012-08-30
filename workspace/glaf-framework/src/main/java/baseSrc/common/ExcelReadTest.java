package baseSrc.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.io.*;
import junit.framework.TestCase;
import jxl.biff.formula.FormulaException;

public class ExcelReadTest extends TestCase {
	/**
	 * 按照列取出sheet数据
	 * @Test
	 */
	public void getxlsMapCOl(){
		String[] sheetName;
		ExcelOper xls =new ExcelOper(new File("d:\\test.xls"));
		
		sheetName=xls.getAllSheetName();
		//遍历sheet名数组，取出sheet名
		for (int i=0;i<sheetName.length;i++){
			//存放sheet中数据
			HashMap<String, List> map = new HashMap<String, List>();
			 //sheet名变量
			 String sName=sheetName[i];
			 //从第2列，第3行开始读取数据。
			 map=xls.getExcelMapByCol(1,2,sName);
		}
	}
	/**
	 * 按照行取出sheet数据
	 * @Test
	 */
	public void getxlsMapRow(){
		String[] sheetName;
		ExcelOper xls =new ExcelOper(new File("d:\\test.xls"));
		
		sheetName=xls.getAllSheetName();
		//遍历sheet名数组，取出sheet名
		for (int i=0;i<sheetName.length;i++){
			//存放sheet中数据
			HashMap<String, List> map = new HashMap<String, List>();
			 //sheet名变量
			 String sName=sheetName[i];
			 //将sheet中cell数据存放区map中
			 map=xls.getExcelMapByRow(2, 2,sName);
		}
		xls.closeXls();
	}
	/*
	 * 取得文本框中的值
	 */
	public void getTextCell(){
		String s;
		ExcelOper xls =new ExcelOper(new File("d:\\test.xls"));
		//指定sheet
		xls.setCurrentSheet("first");
		s=xls.getCellStr(0,0);
		xls.closeXls();
	}
	
	/*
	 * 取得单元格类型为常规的值
	 */
	public void getCell(){
		String s;
		ExcelOper xls =new ExcelOper(new File("d:\\test.xls"));
		//指定sheet
		xls.setCurrentSheet("first");
		s=xls.getCell(1, 1).getContents();
		xls.closeXls();
	}
	/*
	 * 取得数值cell中的值
	 */
	public void getNumCell(){
		Number s;
		ExcelOper xls =new ExcelOper(new File("d:\\test.xls"));
		//指定sheet
		xls.setCurrentSheet("first");
		//取得数值cell中的值的方法
		s=xls.getCellNum(0, 1);
		xls.closeXls();
	}
	/*
	 * 取得日期cell中的值
	 */
	public void getCellYYYYMMDDDate(){
		String s;
		ExcelOper xls =new ExcelOper(new File("d:\\test.xls"));
		//指定sheet
		xls.setCurrentSheet("first");
		//取得数值cell中的值的方法
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd");
		//取得yyyy-mm-ss日期
		s=xls.getCellYYYYMMDDDate(0, 2);
		//取得yyyy-mm-ss hh:mm:ss 日期时间
		s=xls.getCellYYYYMMDDHHMMSSDate(0, 2);
		xls.closeXls();
	}
	
	public void getDateCell(){
		Date testDate = null;
		ExcelOper xls = new ExcelOper(new File("d:\\test.xls"));
		//指定sheet
		xls.setCurrentSheet("first");
		//取得日期类型的值
		try{
			testDate = xls.getDateCell(0, 2).getDate();
		}catch(Exception e){
			e.printStackTrace();
		}
		xls.closeXls();
	}
	
	/**
	 * 取得公式以及公式对应的值
	 * @throws FormuslaException
	 */
	public void getFormulaCell() throws FormulaException {
		String s;
		ExcelOper xls =new ExcelOper(new File("d:\\test.xls"));
		//指定sheet
		xls.setCurrentSheet("first");
		
		//取得值
		s=xls.getStringFormulaCell(0,2).getContents();
		xls.closeXls();
		//取得公式
		try{
			s=xls.getStringFormulaCell(0,2).getFormula();
		}catch(FormulaException ex){
			throw ex;
		}
	}
	public void setUp() throws Exception {
	}

	public void tearDown() throws Exception {
	}
}
