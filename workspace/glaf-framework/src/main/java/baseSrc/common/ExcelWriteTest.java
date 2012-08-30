package baseSrc.common;

import baseSrc.framework.BaseDTOMap;
import baseSrc.framework.BaseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;

import net.sf.jxls.transformer.XLSTransformer;
import sysSrc.orm.TUsers;

import junit.framework.TestCase;
import jxl.biff.formula.FormulaException;
import jxl.write.DateTime;

public class ExcelWriteTest extends TestCase {
	
	private static LogHelper logger = new LogHelper(ExcelOper.class);
	/**
	 * 测试根据模板生成导入文件（参数形式）
	 * 支持多sheet数据导入
	 */
	public void templeteXls1() {
		try {
			XLSTransformer transformer = new XLSTransformer();
			Map<String, Object> data = new HashMap<String, Object>();
			//定义对象
			List<TUsers> exList = new ArrayList<TUsers>();
			List<TUsers> exList1 = new ArrayList<TUsers>();
			
				//一个sheet数据
				for(int i=1;i<=10;i++){
					TUsers TUsers = new TUsers();
					//将数据赋给对象属性
					TUsers.setFUserid(String.valueOf(i));
					exList.add(TUsers);
				}
				//其他sheet的数据
				for(int i=1;i<=10;i++){
					TUsers TUsers = new TUsers();
					//将数据赋给对象属性
					TUsers.setFUserid(String.valueOf(i));
					exList1.add(TUsers);
				}
				data.put("exList", exList);
				data.put("exList1", exList1);
				//将模板转换为xls数据
				//transformer.transformXLS("d:\\user.xls", data, "D:\\user13.xls");
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new BaseException(e);
		}
	}
	/**
	 * 测试根据模板生成导入文件，并且修改数据
	 */
	public void templeteXls2(){
		ExcelOper xls =new ExcelOper(new File("d:\\11.xls"),new File("d:\\1111xx.xls"));
		//设定编辑sheet
		xls.setCurrentSheet(0);
		//取得第一列第一行的值
		String s=xls.getCellStr(0, 0);
		//取得sheet名
		s=xls.getSheetName();
		//写入第一列一行的值
		xls.setText(0, 0, "yyyy");
		s="";
		xls.writeXls();
		xls.closeXls();
	}
	/**
	 * 创建xls并在xls中写入数据
	 */
	public void CreateXLS(){
		ExcelOper xls =new ExcelOper();
		//创建xls
		xls.createXLS("d:\\user223.xls");
		//创建sheet
		xls.createSheet("xxxx",0);
		
		//写入数字
		xls.setNumber(0, 2, 111);
		xls.setNumber(0, 3, 222.1234);
		//写入日期
		xls.setDate(0, 5,new java.util.Date(),"yyy-MM-dd hh:mm:ss");
		xls.setDate(0, 6,new java.util.Date(),"yyy-MM-dd");
		//写入公式
		xls.setFormula(3,3, "A3+A4");
		//写入文本
		xls.setText(0, 1, "rrr");
		//解除锁定
		xls.setLock(0,1,false);
		//保护
		xls.setProtect(true, "123");
		
		
		//保存xls
		xls.writeXls();
		//关闭xls
		xls.closeXls();
	}
	/**
	 * 大数据量的处理
	 */
	public void testBigData(){
		ExcelOper xls =new ExcelOper();
		//创建xls
		xls.createXLS("d:\\user223.xls");
		//创建sheet
		xls.createSheet("xxxx",0);
		//数据大于256列的处理
//		int col;
//		if (col>256){
//			throw new ("列数超出xls范围");
//		}
		//数据大于65536行的处理
		int n;
		//数据量
		n=140000;
		int j1,j2;
		//取得需要增加的sheet个数
		j1=n/65536;
		//取得需要最后一个sheet的数据条数
		j2=n%65536;
		//数据小于等于65536的处理
		if (j1==0){
			for (int i=1;i<=n;i++){
				xls.setNumber(0, i-1, i);
			}
		//数据大于等于65536的处理
		}else{
			//遍历sheet个数，每个sheet都追加65536个数据
			for (int x=1;x<=j1;x++){
				for (int i=1;i<=65536;i++){
					xls.setNumber(0, i-1, 65536*(x-1)+i);
				}
				//大于65536行需要再创建一个sheet
				xls.createSheet("sheet" + String.valueOf(x),x);
			}
			//将剩余数据追加到xls中。
			for (int i=1;i<=j2;i++){
				xls.setNumber(0, i-1, n-65536*j1+i);
			}
		}
		//保存xls
		xls.writeXls();
		//关闭xls
		xls.closeXls();
	}
	public void setUp() throws Exception {
	}

	public void tearDown() throws Exception {
	}
}
