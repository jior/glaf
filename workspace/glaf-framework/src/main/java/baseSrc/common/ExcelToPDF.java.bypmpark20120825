package baseSrc.common;
import jp.ne.so_net.ga2.no_ji.jcom.IDispatch;
import jp.ne.so_net.ga2.no_ji.jcom.ReleaseManager;
import java.io.*;


public class ExcelToPDF {
/*
 * PDF打印
 * officePath：xls文件路径
 * pdfPath：pdf文件路径
 */
	public void createPDF(String officePath,String pdfPath) throws Exception {
		ReleaseManager rm = null;  
        IDispatch app = null;  
        try {
        	rm=new ReleaseManager();  
            app = new IDispatch(rm, "PDFMakerAPI.PDFMakerApp");
            File f=new File(pdfPath);
            //如果PDF文件存在，则删除
            if (f.exists()==true){
            	f.delete();
            }
            //PDF做成
            app.method("CreatePDF",new Object[]{officePath,pdfPath});
        	
        } catch (Exception e) {
                throw e;
        } finally {
                try {
                		//资源释放
                        app=null;
                        rm.release();
                        rm = null;
                } catch (Exception e) {
                        throw e;
                }
        }
}
}
