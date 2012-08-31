<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.awt.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.jpage.util.*" %>
<%@ page import="org.jpage.context.*" %>
<%@ page import="org.jpage.core.task.*" %>
<%@ page import="org.jpage.core.task.model.*" %>
<%@ page import="org.jpage.core.mail.*" %>
<%@ page import="org.jpage.core.mail.model.*" %>
<%@ page import="org.jpage.services.*" %>
<%@ page import="org.jpage.persistence.*" %>
<%@ page import="org.jpage.component.chart.*" %>
<%@ page import="org.jpage.core.query.paging.Page" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.jfree.ui.*" %>
<%@ page import="org.jfree.chart.*" %>
<%@ page import="org.jfree.chart.urls.*"%> 
<%@ page import="org.jfree.chart.labels.*" %> 
<%@ page import="org.jfree.chart.entity.*"%> 
<%@ page import="org.jfree.chart.imagemap.*" %> 
<%@ page import="org.jfree.chart.axis.*" %>
<%@ page import="org.jfree.chart.plot.*" %>
<%@ page import="org.jfree.chart.title.*" %>
<%@ page import="org.jfree.data.*" %>
<%@ page import="org.jfree.data.general.*"%> 
<%@ page import="org.jfree.data.category.*" %>
<%@ page import="org.jfree.chart.renderer.category.*" %>
<%@ page import="org.jfree.chart.servlet.ServletUtilities"%> 
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.todo.*"%>
<%@ page import="com.glaf.base.modules.todo.model.*"%>
<%@ page import="com.glaf.base.modules.todo.service.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.sys.service.*"%>
<%@ page import="org.jpage.util.*" %>
<%@ page import="org.jpage.core.query.paging.*" %>
<%@ page import="com.glaf.base.modules.*" %>
<%@ page import="com.glaf.base.modules.todo.service.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%!
    class CustomBarRenderer extends BarRenderer
    {

        public Paint getItemPaint(int i, int j)
        {
            return colors[j % colors.length];
        }

        private Paint colors[];

        public CustomBarRenderer(Paint apaint[])
        {
            colors = apaint;
        }
    }


%>
<%

            int total = 0;
	    	int width = 584;
            int height = 424;

			SysUser user = com.glaf.base.utils.RequestUtil.getLoginUser(request);
	        Map params = new HashMap();
	        params.put("actorIdx", user.getAccount());
			TodoJobBean bean = (TodoJobBean)BaseDataManager.getInstance().getBean("todoJobBean");
			Collection rows = bean.getToDoInstanceList(params);
			Map dataMap = new LinkedHashMap();
			Map todoMap = bean.getToDoMap();
			Map deptMap = bean.getDepartmentMap();

			if(rows != null && rows.size()> 0){
					  Iterator iterator008 = rows.iterator();
					  while(iterator008.hasNext()){
						   ToDoInstance tdi = (ToDoInstance)iterator008.next();
						   int status = TodoConstants.getTodoStatus(tdi);
						   tdi.setStatus(status);
						   ToDoInstance xx = (ToDoInstance)dataMap.get(new Long(tdi.getDeptId()));
						   if(xx == null){
							   xx = new ToDoInstance();
							   xx.setDeptId(tdi.getDeptId());
							   SysDepartment r = (SysDepartment)deptMap.get(new Long(tdi.getDeptId()));
							   if(r != null){
								   xx.setDeptName(r.getName());
							   }
						   }

						   switch(status){
								case TodoConstants.OK_STATUS:
									xx.setQty01(xx.getQty01()+1);
									break;
								case TodoConstants.CAUTION_STATUS:
									xx.setQty02(xx.getQty02()+1);
									break;
								case TodoConstants.PAST_DUE_STATUS:
									xx.setQty03(xx.getQty03()+1);
									break;
								default:
									break;
						   }

						   dataMap.put(new Long(tdi.getDeptId()), xx);

					  }
			}

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        int pastDue = 0;
		rows = dataMap.values();
		Iterator iter003 = rows.iterator();
        while(iter003.hasNext()){
			ToDoInstance tdi = (ToDoInstance)iter003.next();
			if(tdi.getDeptName() != null){
			    String title = "["+tdi.getDeptId()+"]" + tdi.getDeptName() ;
				dataset.addValue(tdi.getQty03()*1.000000D , "Past Due", title);
				dataset.addValue(tdi.getQty02()*1.000000D , "Caution", title);
				dataset.addValue(tdi.getQty01()*1.000000D , "OK", title);
				pastDue += tdi.getQty03();
				total += tdi.getQty01() + tdi.getQty02() + tdi.getQty03();
			}
		}

		if(rows.size() > 5){
           width = 120 * rows.size();
		}	

        String seriesTitle = "Quantity";
		double percent = pastDue*1.00D / total*1.00D;

		java.text.DecimalFormat format = new java.text.DecimalFormat("##.00");
	    JFreeChart jfreechart = ChartFactory.createStackedBarChart("TODO状态分布图 总数（" + Math.round(total)+"）超期（"+pastDue+"）超期占比（" +  format.format(percent*100) + "%）", "", seriesTitle, dataset, PlotOrientation.VERTICAL, true, true, false);
        jfreechart.setBackgroundPaint(Color.white);  
		TextTitle texttitle = jfreechart.getTitle();
		texttitle.setFont(new Font("宋体", 0, 15));

		java.util.List titleList = jfreechart.getSubtitles();
		Iterator iterator002 = titleList.iterator();
		while(iterator002.hasNext()){
			Title tt02 = (Title)iterator002.next();
			if(tt02 instanceof TextTitle){
				TextTitle tttt = (TextTitle)tt02;
                tttt.setFont(new Font("宋体", 0, 12));
			}else if(tt02 instanceof LegendTitle){
				LegendTitle tttt = (LegendTitle)tt02;
                tttt.setItemFont(new Font("宋体", 0, 12));
			}
		}


	    CategoryPlot categoryplot = jfreechart.getCategoryPlot();

		ExtendedStackedBarRenderer render = new ExtendedStackedBarRenderer();
		render.setItemLabelsVisible(true);
		render.setItemLabelGenerator(new org.jfree.chart.labels.StandardCategoryItemLabelGenerator());
		render.setToolTipGenerator(new org.jfree.chart.labels.StandardCategoryToolTipGenerator());

        Paint red = new GradientPaint(0.0F, 0.0F, Color.red, 0.0F, 0.0F, Color.red);
		Paint yellow = new GradientPaint(0.0F, 0.0F, Color.yellow, 0.0F, 0.0F, Color.yellow);
        Paint green = new GradientPaint(0.0F, 0.0F, Color.green, 0.0F, 0.0F, Color.green);

		render.setSeriesPaint(0, red);
		render.setSeriesPaint(1, yellow);
		render.setSeriesPaint(2, green);
		render.setItemMargin(2);

        String link = "todo_list.jsp";
		render.setItemURLGenerator(new ChartCategoryURLGenerator(link,
				"x", "y_dept"));
		render.setToolTipGenerator(new org.jfree.chart.labels.StandardCategoryToolTipGenerator());

		categoryplot.setRenderer(render);
      
		ValueAxis valueaxis = categoryplot.getRangeAxis();
		valueaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		valueaxis.setLowerMargin(0.14999999999999999D);
		valueaxis.setUpperMargin(0.14999999999999999D);
		String dataFile ="dept_stackedbar";

		ByteArrayOutputStream baos = null;
        String path = ApplicationContext.getAppPath()+"/temp/";
		try {
			java.awt.image.BufferedImage bi = jfreechart.createBufferedImage(
					width, height);
			org.jfree.chart.encoders.EncoderUtil
					.writeBufferedImage(bi, "png",
							new java.io.FileOutputStream(path + dataFile
									+ ".png"));
			ChartRenderingInfo info = new ChartRenderingInfo(
					new org.jfree.chart.entity.StandardEntityCollection());
			org.jfree.chart.servlet.ServletUtilities.saveChartAsPNG(jfreechart, width, height, info,null);
			baos = new ByteArrayOutputStream();
			PrintWriter pw = new PrintWriter(baos);
			pw
					.write("<script language=\"JavaScript\">"
							+ "function overlib(){"
							+ "}"
							+ "function nd(){"
							+ "}" + "</script></head><body>");
			ChartUtilities.writeImageMap(pw, "map015", info, new ChartToolTipTagFragmentGenerator(), new ChartURLTagFragmentGenerator());
			pw.write("<p align=\"center\"><img src=\""+request.getContextPath()+"/temp/"
					+ dataFile + ".png\" usemap=\"#map015"+"\" border=\"0\"></p>");
			pw.write("");
			pw.close();
			baos.close();
			byte[] bytes = baos.toByteArray();
            String content = new String(bytes);
            if(content.length()>0){
			   out.println(content);
			}else{
			   out.println("<div align=\"center\">没有数据。</div>");
		    }
		} catch (IOException ex) {
			ex.printStackTrace();
		} 
%>