package demoSrc.src;


import java.util.Map;

import sysSrc.common.SysBaseCom;
import sysSrc.common.SystemConfig;
import sysSrc.framework.SysBaseDTOMap;

import baseSrc.common.BaseCom;
import baseSrc.framework.BaseDAO;
import baseSrc.common.CsvHelper;

//csv示例
public class CsvTestDAO extends BaseDAO{
	public SysBaseDTOMap csvDo(CsvTestFrom form, SysBaseCom baseCom)
			throws Exception {

		
		SysBaseDTOMap ret = new SysBaseDTOMap();
		
		ret.setForwardId("goCsvTest");
		
		return ret;
	}

	private void csvTestReader() throws Exception {
		try{
		CsvHelper cl = new CsvHelper("D:/testA.csv", "测试001");

		//获取数据
		if (!cl.getData()) {
			// 获取失败
			for(int i = 0 ; i < cl.getMessageList().size();i++){
				System.out.println(cl.getMessageList().get(i));
			}
			return;
		}
		
		//检查数据
		if (!cl.checkData()) {
			// 检查不通过
			for(int i = 0 ; i < cl.getMessageList().size();i++){
				System.out.println(cl.getMessageList().get(i));
			}
			return;
		}

		// 遍历CSV读取的结果
		String dataStrs = "";
		//表头。
		if(cl.getCsvHeaderList() != null){
			System.out.println("---表头---：");
			for(int i = 0;i<cl.getCsvHeaderList().size();i++){
				for(int j = 0;j<cl.getCsvHeaderList().get(i).length;j++){
					dataStrs = "行/列【" + i + "/" + j +"】" + cl.getCsvHeaderList().get(i)[j];
				}
			}
		}
		
		if(cl.getCsvBodyList() != null){
			System.out.println("---内容---：");
			for(int i = 0;i<cl.getCsvBodyList().size();i++){
				dataStrs = "";
				for(int j = 0;j<cl.getCsvBodyList().get(i).length;j++){
					dataStrs = dataStrs + "行/列【" + i + "/" + j +"】" + cl.getCsvBodyList().get(i)[j] + "; ";
				}
				System.out.println(dataStrs);
			}
		}}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	private void csvTestWriter() throws Exception{
		CsvHelper wr1 = new CsvHelper("D:/testWr1.csv");
		CsvHelper wr2 = new CsvHelper("D:/testWr2.csv","测试001");
		CsvHelper wr3 = new CsvHelper("D:/testWr3.csv");
		try{
			String[] contents1 = { "\"a1,ax", "\"b1", "\'c1", "\'d1" };
			String[] contents2 = { "我是中文", "123123", "！@#！@#..,..,", "fsdfdsaf" };
			String[] contents3 = { "日本语测试：語り尽くす ちゅうけんはちこう", "\"b1", "\'c1", "\'d1" };
			String str1 = "aaaaaaa,adadada123";
			String str2 = "bbbbbbbb是中文";
			String str3 = "ccccccccccccc語り尽くす";
			String str11 = "dddd";
			String str22 = "eee日本语测试eee";
			String str33 = "fff1213";
			
			wr1.setDelimiter(',');
			wr3.setIsFileAddModel(true);			
			
			//一次写一列
			wr1.write(str1); //写入一列
			wr1.write(str2); //写入一列
			wr1.write(str3); //写入一列			
			wr1.endRecord(); //换行
			wr1.write(str11); //写入一列
			wr1.write(str22); //写入一列
			wr1.write(str33); //写入一列
			wr1.endRecord();//换行
			
			wr2.writeRecord(contents1); //写入一行
			wr2.writeRecord(contents2); //写入一行
			wr2.writeRecord(contents3); //写入一行
			
			wr3.endRecord();//换行
			wr3.write(str11);//写入一列
			wr3.write(str22);//写入一列
			wr3.write(str33);//写入一列
			wr3.endRecord();//换行
			wr3.writeRecord(contents1);//写入一行
			wr3.writeRecord(contents2);//写入一行
			wr3.writeRecord(contents3);//写入一行
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(wr1 != null){
				wr1.close();//关闭
			}
			if(wr2 != null){
				wr2.close();//关闭
			}
			if(wr3 != null){
				wr3.close();//关闭
			}
		}
		
	}

	private void creatFile() throws Exception{
		CsvHelper wr1 = new CsvHelper("D:/testA.csv");
		wr1.writeRecord(new String[]{"1","灵","a","START"});
		wr1.writeRecord(new String[]{"2","地","b","日本语测试：語り尽く"});
		wr1.writeRecord(new String[]{"3","佑","e","还是很多123efg"});
		wr1.writeRecord(new String[]{"4","我","k","1980"});
		wr1.writeRecord(new String[]{"5","中","G","!!@!！@！＝、、+、－～～我的符号"});
		wr1.writeRecord(new String[]{"6","","H","END"});
		wr1.close();
	}
}
