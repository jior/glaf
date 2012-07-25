<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.awt.*" %>
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
<%
         String path = this.getServletContext().getRealPath("")+"/temp/";

		 int width = 584;
         int height = 424;

		 DefaultPieDataset dataset = new DefaultPieDataset();
          
		 int max = 0;
		 int number = 0;
        
         dataset.setValue("苹果", 0.15);
		 dataset.setValue("梨", 0.15);
		 dataset.setValue("葡萄", 0.40);
		 dataset.setValue("桔子", 0.30);

		 PiePlot plot = new PiePlot(dataset);

        //设定链接 
		String link = "list.jsp?";
		plot.setURLGenerator(new StandardPieURLGenerator(link, "sendStatus", "pieIndex"));

		//可选，设置图片背景色 
		JFreeChart chart = new JFreeChart("",JFreeChart.DEFAULT_TITLE_FONT, plot, true); 
		chart.setBackgroundPaint(java.awt.Color.white);
		TextTitle texttitlex = chart.getTitle();
		texttitlex.setFont(new Font("宋体", 0, 15));

		//可选，设置图片标题
		
		java.text.DecimalFormat format = new java.text.DecimalFormat("##.00");
		chart.setTitle(""); 

		TextTitle texttitle = new TextTitle("水果产量百分比");
		texttitle.setFont(new Font("宋体", 0, 15));
	
		texttitle.setPosition(RectangleEdge.BOTTOM);
		texttitle.setHorizontalAlignment(HorizontalAlignment.CENTER);
		chart.addSubtitle(texttitle);

		java.util.List titleList = chart.getSubtitles();
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

		chart.removeLegend(); //去掉底部显示
		
		plot.setToolTipGenerator(new StandardPieToolTipGenerator());
		

        ByteArrayOutputStream baos = null;
		try {
			java.awt.image.BufferedImage bi = chart.createBufferedImage(
					width, height);
			org.jfree.chart.encoders.EncoderUtil
					.writeBufferedImage(bi, "png",
							new java.io.FileOutputStream(path + "123_xyz.png"));
			ChartRenderingInfo info = new ChartRenderingInfo(
					new org.jfree.chart.entity.StandardEntityCollection());
			org.jfree.chart.servlet.ServletUtilities.saveChartAsPNG(chart, width, height, info,null);
			baos = new ByteArrayOutputStream();
			PrintWriter pw = new PrintWriter(baos);
			pw
					.write("<script language=\"JavaScript\">"
							+ "function overlib(){"
							+ "}"
							+ "function nd(){"
							+ "}" + "</script></head><body>");
			ChartUtilities.writeImageMap(pw, "map025", info, new StandardToolTipTagFragmentGenerator(), new StandardURLTagFragmentGenerator());
			pw.write("<p align=\"center\"><img src=\""+request.getContextPath()+"/temp/"
					+ "123_xyz.png\" usemap=\"#map025\" border=\"0\" target=\"_blank\"></p>");
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