package baseSrc.common;

import baseSrc.framework.BaseException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jxl.BooleanFormulaCell;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.DateFormulaCell;
import jxl.FormulaCell;
import jxl.LabelCell;
import jxl.NumberCell;
import jxl.NumberFormulaCell;
import jxl.Sheet;
import jxl.StringFormulaCell;
import jxl.Workbook;
import jxl.biff.EmptyCell;
import jxl.biff.formula.FormulaException;
import jxl.read.biff.BiffException;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

 

public class ExcelOper {
	//日志
	private static LogHelper logger = new LogHelper(ExcelOper.class);
	//只读workbook
 	private Workbook workbook;
 	//当前工作sheet
 	private Sheet currentSheet;
 	//可读写的workbook
 	private WritableWorkbook wrWorkbook;
 	//可读写的sheet
 	private WritableSheet wrCurrentSheet;
 	//可读写与只读标示  true:表示只读取xls数据，false表示读写xls数据
 	private Boolean readOnlyWBook; 
 	/**
 	 * 用于创建一个新的xls
 	 */
 	public ExcelOper() {
 		readOnlyWBook=false;
 	}
 	/**
 	 * 用于根据模板生成新的xls
 	 * @param tplFile   模板文件路径
 	 * @param sFile     新的文件路径
 	 */
 	public ExcelOper(File tplFile,File sFile){
 		try {
 			//取得模板文件
			workbook = Workbook.getWorkbook(tplFile) ;
			//生成新文件
			wrWorkbook = Workbook.createWorkbook(sFile, workbook);
			readOnlyWBook=false;
		} catch (BiffException e) {
			//写日志
			logger.error(e.getMessage());
			//抛出异常
			throw new BaseException(e);
		} catch (IOException e) {
			//写日志
			logger.error(e.getMessage());
			//抛出异常
			throw new BaseException(e);
		}
 	}
 	/**
 	 * 只用于读取xls数据
 	 * xls文件名做成参数
 	 */
 	public ExcelOper(File file) {
 	    try {
 	    	//Workbook workbook = null;
 	 	    readOnlyWBook=true;
 	 	    //设置workbook
 	        workbook = Workbook.getWorkbook(file);
 	    } catch (BiffException e) {
 	    	logger.error(e.getMessage());
			throw new BaseException(e);
 	    } catch (IOException e) {
 	    	logger.error(e.getMessage());
			throw new BaseException(e);
 	
 	    }
 	    //设置当前工作workbook
 	    if (workbook != null) {
 	
 	        setWorkbook(workbook);
 	
 	    }
 	}
 	/**
 	 * 创建xls
 	 * @param fileName 文件名
 	 */
 	public void createXLS(String fileName){
 		try {
 			//创建workbook
 			wrWorkbook=Workbook.createWorkbook(new File(fileName));
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new BaseException(e);
		}      
 	}
 	/**
 	 * 创建sheet
 	 * @param sheetName
 	 * @param sheetNum
 	 */
 	public void createSheet(String sheetName,int sheetNum){
 		//创建可读写的sheet
 		wrCurrentSheet=wrWorkbook.createSheet(sheetName,sheetNum);
 	}
 	/**
 	 * 写入文本值
 	 * @param colNum 列号
 	 * @param rowNum 行号
 	 * @param sText  文本内容
 	 */
 	public void setText(int colNum, int rowNum,String sText){
 		Label labelc = new Label(colNum,rowNum,sText);
 		try {
 			//写入文本值
			wrCurrentSheet.addCell(labelc);
		} catch (RowsExceededException e) {
			logger.error(e.getMessage());
			throw new BaseException(e);
		} catch (WriteException e) {
			logger.error(e.getMessage());
			throw new BaseException(e);
		}
 	}
 	/**
 	 * 写入数值
 	 * @param colNum 列号
 	 * @param rowNum 行号
 	 * @param num    数值
 	 */
 	
 	public void setNumber(int colNum, int rowNum,double num){
 		jxl.write.Number number = new jxl.write.Number(colNum,rowNum,num);
 		try {
 			//写入数值
			wrCurrentSheet.addCell(number);
		} catch (RowsExceededException e) {
			logger.error(e.getMessage());
			throw new BaseException(e);
		} catch (WriteException e) {
			logger.error(e.getMessage());
			throw new BaseException(e);
		}
 	}
 	/**
 	 * 写入日期型
 	 * @param colNum 列号
 	 * @param rowNum 行号
 	 * @param dt     日期或时间
 	 * @param dateformat “YYY-MM-dd” "YYYY-MM-dd hh:mm：ss" 日期或时间格式
 	 */
	public void setDate(int colNum, int rowNum,Date dt,String dateformat){
 		try {
 			//定义日期格式
 			DateFormat df=new DateFormat(dateformat); 
 			//定义cell的日期格式
 			WritableCellFormat wcfDF=new WritableCellFormat(df);  
 			
 			DateTime labelDTF=new DateTime(colNum,rowNum,dt,wcfDF); 
 			//写入日期
 			wrCurrentSheet.addCell(labelDTF); 
 			
		} catch (RowsExceededException e) {
			logger.error(e.getMessage());
			throw new BaseException(e);
		} catch (WriteException e) {
			logger.error(e.getMessage());
			throw new BaseException(e);
		} 
 	}
 	/**
 	 *  写入公式
 	 * @param colNum 列号
 	 * @param rowNum 行号
 	 * @param formula 写入公式内容
 	 */
	public void setFormula(int colNum, int rowNum,String formula){
		try {
			wrCurrentSheet.addCell(new Formula(6,3,formula));
			
		} catch (RowsExceededException e) {
			logger.error(e.getMessage());
			throw new BaseException(e);
		} catch (WriteException e) {
			logger.error(e.getMessage());
			throw new BaseException(e);
		}
	}
 	/**
 	 * 锁定单元格
 	 * @param colNum 列号
 	 * @param rowNum 行号
 	 * @param bln  true：锁定 ,false :取消
 	 */
	public void setLock(int colNum, int rowNum,Boolean bln){
		WritableCellFormat format_unlock = new WritableCellFormat();
	    try {
	    	//锁定单元格
	    	if (bln==true){
	    		format_unlock.setLocked(true);
	    	//解锁单元格
	    	}else{
	    		format_unlock.setLocked(false);
	    	}
			wrCurrentSheet.getWritableCell(colNum,rowNum).setCellFormat(format_unlock);
		} catch (WriteException e) {
			logger.error(e.getMessage());
			throw new BaseException(e);
		}
	}
	/**
	 * 设置保护
	 * @param bln true：保护 false 取消保护
	 * @param pwd 密码
	 */
	public void setProtect(Boolean bln,String pwd){
		//根据bln值确定是否保护或取消保护
		wrCurrentSheet.getSettings().setProtected(bln);
		//设置密码
		wrCurrentSheet.getSettings().setPassword(pwd);
	}
 	/**
 	 * 关闭xls
 	 */
 	public void closeXls(){
 		try {
 			if (readOnlyWBook==true){
	 			if (wrWorkbook !=null){
	 				wrWorkbook.close();
	 			}
 			}else{
 				if (workbook != null) {
 		            workbook.close();
 		        }
 			}
		} catch (WriteException e) {
			logger.error(e.getMessage());
			throw new BaseException(e);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new BaseException(e);
		}
 	}
 	/**
 	 * 写入xls
 	 */
 	public void writeXls(){
 		try {
 			if (wrWorkbook !=null){
 				wrWorkbook.write();
 			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new BaseException(e);
		}
 	}
 	/**ss
	 * 设置当前workBook
	 * 
	 * @param workbook 工作book
	 * 
	 * 
	 */
 	public void setWorkbook(Workbook workbook) {
 		//设置当前work
 	    this.workbook = workbook;
 	    //设置当前工作sheet
 	    //this.currentSheet = workbook.getSheet(0);
 	   
 	
 	}
 	/**
 	 * 根据sheet序号设置当前操作sheet
 	 * @param sheetNum sheet序号
 	 */
 	public void setCurrentSheet(int sheetNum) {
 		//只读取workbook
 		if (readOnlyWBook==true){
 			currentSheet = workbook.getSheet(sheetNum);
 		//可读写workbook
 		}else{
 			wrCurrentSheet=wrWorkbook.getSheet(sheetNum);
 		}
 	   
 	}
 	/**
 	 * 根据sheet名设置当前操作sheet
 	 * @param sheetName shee名
 	 */
 	public void setCurrentSheet(String sheetName) {
 		if (readOnlyWBook==true){
 	 		currentSheet = workbook.getSheet(sheetName);
 	 	} else{
 	 		wrCurrentSheet=wrWorkbook.getSheet(sheetName);
 	 	}
 	}
 	
 	/**
 	 * 取得xls中所有的shee名
 	 * @return sheet名数组
 	 */
 	public  String[] getAllSheetName() {
 		if (readOnlyWBook==true){
 			return workbook.getSheetNames();
 		}else{
 			return wrWorkbook.getSheetNames();
 		}
 	   
 	}
 	/**
 	 * 取得当前sheet的sheet名
 	 * @return 
 	 */
 	public  String getSheetName() {
 		if (readOnlyWBook==true){
 			return currentSheet.getName();
 		}else{
 			return wrCurrentSheet.getName();
 		}
 			
 	   
 	}
 	/**
 	 * 取得cell
 	 * @param colNum 列号
 	 * @param rowNum 行号
 	 * @return
 	 */
 	public Cell getCell(int colNum, int rowNum) {
 		if (readOnlyWBook==true){
 			return currentSheet.getCell(colNum, rowNum);       
 		}else{
 			return wrCurrentSheet.getCell(colNum, rowNum);       
 		}
 			
 	}
 	/**
 	 * 取得一行的cell
 	 * @param rowNum 行号
 	 * @return 
 	 */
 	public Cell[] getRowCells(int rowNum) {
 		if (readOnlyWBook==true){
 			return currentSheet.getRow(rowNum);
 		}else{
 			return wrCurrentSheet.getRow(rowNum);
 		}
 			
 	
 	}
 	/**
 	 * 取得一列的cell
 	 * @param rowNum 行号
 	 * @return 一列的cell
 	 */
 	public Cell[] getColCells(int colNum) {
 		if (readOnlyWBook==true){
 			return currentSheet.getColumn(colNum);
 		}else{
 			return wrCurrentSheet.getColumn(colNum);
 		}
 	
 	}
 	/**
 	 * 取得行数
 	 * @return 行数
 	 */
 	public int getRows() {
 		if (readOnlyWBook==true){
 			return currentSheet.getRows();
 		}else{
 			return wrCurrentSheet.getRows();
 		}
 	
 	}
 	/**
 	 * 取得列数
 	 * @return 列数
 	 */
 	public int getCols() {
 		if (readOnlyWBook==true){
 			return currentSheet.getColumns();
 		}else{
 			return wrCurrentSheet.getColumns();
 		}
 			
 	}
 	/**
 	 * 取得空的cell
 	 * @param colNum
 	 * @param rowNum
 	 * @return
 	 */
 	public EmptyCell getEmptyCell(int colNum, int rowNum) {
 	
 	    Cell cell = getCell(colNum, rowNum);
 	    //如果cell为空cell
 	    if (cell instanceof EmptyCell) {
 	
 	        return (EmptyCell)cell;
 	
 	    }else{
 	    	logger.error("cell type is not EmptyCell");
 	    	throw new BaseException("cell type is not EmptyCell");
 	    }
 	}
 	/**
 	 * 取得时间型的cell
 	 * @param colNum 列号
 	 * @param rowNum 行号
 	 * @return
 	 */
 	public DateCell getDateCell(int colNum, int rowNum) {
 	
 	    Cell cell = getCell(colNum, rowNum);
 	    //如果cell为日期型cell
 	    if (cell instanceof DateCell) {
 	        return (DateCell)cell;
 	    }else{
 	    	logger.error("cell type is not DateCell");
 	    	throw new BaseException("cell type is not DateCell");
 	    }
 	}
 	/**
 	 * 取得字符型的cell
 	 * @param colNum 列号
 	 * @param rowNum 行号
 	 * @return
 	 */
 	public LabelCell getLabelCell(int colNum, int rowNum) {
 	    Cell cell = getCell(colNum, rowNum);
 	    //如果cell为日期型cell
 	    if (cell instanceof LabelCell) {
 	        return (LabelCell)cell;
 	    }else{
 	    	logger.error("cell type is not LabelCell");
 	    	throw new BaseException("cell type is not LabelCell");
 	    }
 	}
 	/**
 	 * 取得数字型的cell
 	 * @param colNum 列号
 	 * @param rowNum 行号
 	 * @return
 	 */
	public NumberCell getNumberCell(int colNum, int rowNum) {
	    Cell cell = getCell(colNum, rowNum);
	    //如果cell为数值型cell
	    if (cell instanceof NumberCell) {
	        return (NumberCell)cell;
	    }else{
	    	logger.error("cell type is not NumberCell");
 	    	throw new BaseException("cell type is not NumberCell");
 	    }
	}
	/**
 	 * 取得数字公式的cell
 	 * @param colNum 列号
 	 * @param rowNum 行号
 	 * @return
 	 */
	public NumberFormulaCell getNumberFormulaCell(int colNum, int rowNum) {
	    Cell cell = getCell(colNum, rowNum);
	    //如果cell为数值公式型cell
	    if (cell instanceof NumberFormulaCell) {
	        return (NumberFormulaCell)cell;
	    }else{
	    	logger.error("cell type is not NumberFormulaCell");
 	    	throw new BaseException("cell type is not NumberFormulaCell");
 	    }
	}
	/**
 	 * 取得日期公式的cell
 	 * @param colNum 列号
 	 * @param rowNum 行号
 	 * @return
 	 */
	public DateFormulaCell getDateFormulaCell(int colNum, int rowNum) {
	    Cell cell = getCell(colNum, rowNum);
	    //如果cell为日期公式型
	    if (cell instanceof DateFormulaCell) {
	        return (DateFormulaCell)cell;
	    }else{
	    	logger.error("cell type is not DateFormulaCell");
 	    	throw new BaseException("cell type is not DateFormulaCell");
 	    }
	}
	/**
 	 * 取得文本公式的cell
 	 * @param colNum 列号
 	 * @param rowNum 行号
 	 * @return
 	 */
	public StringFormulaCell getStringFormulaCell(int colNum, int rowNum) {
	    Cell cell = getCell(colNum, rowNum);
	    //如果cell为字符公式型cell
	    if (cell instanceof StringFormulaCell) {
	        return (StringFormulaCell)cell;
	    }else{
	    	logger.error("cell type is not StringFormulaCell");
 	    	throw new BaseException("cell type is not StringFormulaCell");
 	    }
	}
	/**
 	 * 取得布尔公式的cell
 	 * @param colNum 列号
 	 * @param rowNum 行号
 	 * @return
 	 */
	public BooleanFormulaCell getBooleanFormula(int colNum, int rowNum) {
	    Cell cell = getCell(colNum, rowNum);
	    //如果cell为布尔公式型
	    if (cell instanceof BooleanFormulaCell) {
	        return (BooleanFormulaCell)cell;
	    }else{
	    	logger.error("cell type is not BooleanFormulaCell");
 	    	throw new BaseException("cell type is not BooleanFormulaCell");
 	    }
	}
	/**
	 * 取得公式内容
	 * @param colNum
	 * @param rowNum
	 * @return
	 * @throws FormulaException
	 */
	public String getFormula(int colNum, int rowNum) throws FormulaException{
	    Cell cell = getCell(colNum, rowNum);
	    FormulaCell nfc =  (FormulaCell) cell;
	    //如果为公式类型
	    if (cell.getType() == CellType.NUMBER_FORMULA
	    	|| cell.getType() == CellType.STRING_FORMULA
	    	|| cell.getType() == CellType.BOOLEAN_FORMULA
	    	|| cell.getType() == CellType.DATE_FORMULA
	    	||cell.getType() == CellType.FORMULA_ERROR){
	         return nfc.getFormula();
	    }else{
	    	logger.error("cell is not Formula");
 	    	throw new BaseException("cell is not Formula");
 	    }
	}
	/**
	 * 取得cell中日期数据
	 * @param colNum 列号
	 * @param rowNum 行号
	 * @return
	 */
    public Date getCellDate(int colNum, int rowNum) {
  
        DateCell dateCell = getDateCell(colNum, rowNum);
        return dateCell.getDate();
    }
    /**
     * 取得cell中日期（yyyy-MM-dd）数据
     * @param colNum
     * @param rowNum
     * @return
     */
    public String getCellYYYYMMDDDate(int colNum, int rowNum) {
        SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd");
        return simp.format(getCellDate(colNum, rowNum));
    }
    /**
     * 取得时间
     * @param colNum
     * @param rowNum
     * @return 时间值
     */
    public String getCellYYYYMMDDHHMMSSDate(int colNum, int rowNum) {
        SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        return simp.format(getCellDate(colNum, rowNum));
        
    }
    /**
	 * 取得cell中字符型数据
	 * @param colNum 列号
	 * @param rowNum 行号
	 * @return
	 */
    public String getCellStr(int colNum, int rowNum) {
        LabelCell labelCell = getLabelCell(colNum, rowNum);
        return labelCell.getContents();
       
    }
    /**
     * 取得数值列的值
     * @param colNum 列号
     * @param rowNum 行号
     * @return
     */
    public Number getCellNum(int colNum, int rowNum) {
        return getNumberCell(colNum,rowNum).getValue();
       
    }
    
    /**
     * 从（开始行号，开始列号）起，到xls有效数据行结束，
     * 按列取得一个sheet中所有数据,列号作为map中key值。
     * 一列中的所有数据放置为list中作为map的value值
     * @param iBeginCol 开始列号
     * @param iBeginRow 开始行号
     * @return
     */
    public HashMap<String, List> getExcelMapByCol(int iBeginCol,int iBeginRow,String sheetName){
		try{
			//设置当前操作sheet
			setCurrentSheet(sheetName);
			//数据存放map
			HashMap<String, List> map = new HashMap<String, List>();
			int i,j,iRowSum,iColSum;
			//取得有效数据行数
			iRowSum=getRows();
			//取得有效数据列数
			iColSum=getCols();
			for(i=iBeginCol;i<iColSum;i++){
				//用于存放一列数据的list
				List<String> lst = new ArrayList<String>();
				for(j=iBeginRow;j<iRowSum;j++){
					lst.add(getCell(i,j).getContents().toString().trim());
				}
				//列号作为key,将数据存放，map中
				map.put(String.valueOf(i), lst);
			}
			return map;
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new BaseException(e);
		}
	}
    /**
     从（开始行号，开始列号）起，到xls有效数据行结束，
     * 按行取得一个sheet中所有数据,行号作为map中key值
     * 一行中的所有数据放置为list中作为map的value值
     * @return 
     */
    public HashMap<String, List> getExcelMapByRow(int iBeginCol,int iBeginRow,String sheetName){
    	try{
    		//设置当前操作sheet
			setCurrentSheet(sheetName);
			//数据存放map
			HashMap<String, List> map = new HashMap<String, List>();
			int i,j,iRowSum,iColSum;
			//取得有效数据行数
			iRowSum=getRows();
			//取得有效数据列数
			iColSum=getCols();
			for(i=iBeginRow;i<iRowSum;i++){
				//用于存放一行数据的list
				List<String> lst = new ArrayList<String>();
				for(j=iBeginCol;j<iColSum;j++){
					lst.add(getCell(j,i).getContents().toString().trim());
				}
				//行号作为key,将数据存放，map中
				map.put(String.valueOf(i),lst);
			}
			return map;
    	}catch(Exception e){
			logger.error(e.getMessage());
			throw new BaseException(e);
		}
	}
}
