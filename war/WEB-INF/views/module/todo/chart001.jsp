<%@ page contentType="text/html;charset=UTF-8" %>
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
<%!
   private static class CustomBarRenderer extends BarRenderer{
        private Paint[] colors;

        public CustomBarRenderer(Paint[] paint) {
            colors = paint;
        }

	    public Paint getItemPaint(int i, int j) {
            return colors[j % colors.length];
        }
    }


    private static CategoryDataset createDataset()
    {
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
        defaultcategorydataset.addValue(410D, "Network Traffic", "Monday");
        defaultcategorydataset.addValue(680D, "Network Traffic", "Tuesday");
        defaultcategorydataset.addValue(530D, "Network Traffic", "Wednesday");
        defaultcategorydataset.addValue(570D, "Network Traffic", "Thursday");
        defaultcategorydataset.addValue(330D, "Network Traffic", "Friday");
        return defaultcategorydataset;
    }

    private static JFreeChart createChart(CategoryDataset categorydataset)
    {
        JFreeChart jfreechart = ChartFactory.createBarChart("Bar Chart Demo 9", null, "Value", categorydataset, PlotOrientation.VERTICAL, false, true, false);
        TextTitle texttitle = jfreechart.getTitle();
        texttitle.setBorder(0.0D, 0.0D, 1.0D, 0.0D);
        texttitle.setBackgroundPaint(new GradientPaint(0.0F, 0.0F, Color.red, 350F, 0.0F, Color.white, true));
        texttitle.setExpandToFitSpace(true);
        jfreechart.setBackgroundPaint(new GradientPaint(0.0F, 0.0F, Color.yellow, 350F, 0.0F, Color.white, true));
        CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();
        categoryplot.setNoDataMessage("NO DATA!");
        categoryplot.setBackgroundPaint(null);
        categoryplot.setInsets(new RectangleInsets(10D, 5D, 5D, 5D));
        categoryplot.setOutlinePaint(Color.black);
        categoryplot.setRangeGridlinePaint(Color.gray);
        categoryplot.setRangeGridlineStroke(new BasicStroke(1.0F));
        Paint[] paint = createPaint();
        CustomBarRenderer custombarrenderer = new CustomBarRenderer(paint);
        custombarrenderer.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.CENTER_HORIZONTAL));
        categoryplot.setRenderer(custombarrenderer);
        NumberAxis numberaxis = (NumberAxis)categoryplot.getRangeAxis();
        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        numberaxis.setRange(0.0D, 800D);
        numberaxis.setTickMarkPaint(Color.black);
        return jfreechart;
    }

    private static Paint[] createPaint()
    {
        Paint[] paint = new Paint[5];
        paint[0] = new GradientPaint(0.0F, 0.0F, Color.white, 0.0F, 0.0F, Color.red);
        paint[1] = new GradientPaint(0.0F, 0.0F, Color.white, 0.0F, 0.0F, Color.green);
        paint[2] = new GradientPaint(0.0F, 0.0F, Color.white, 0.0F, 0.0F, Color.blue);
        paint[3] = new GradientPaint(0.0F, 0.0F, Color.white, 0.0F, 0.0F, Color.orange);
        paint[4] = new GradientPaint(0.0F, 0.0F, Color.white, 0.0F, 0.0F, Color.magenta);
        return paint;
    }

%>
<%
     String path = org.jpage.context.ApplicationContext.getAppPath()+"/temp/";
     String taskId = "001";
	 java.util.List rows = null;
     try{
         
		 int width = 640;
         int height = 430;

		 CategoryDataset categorydataset = createDataset();
         JFreeChart jfreechart = createChart(categorydataset);

         java.awt.image.BufferedImage bi = jfreechart.createBufferedImage(width, height);
		 org.jfree.chart.encoders.EncoderUtil.writeBufferedImage(bi, "png",
								new java.io.FileOutputStream(path + taskId+ ".png"));
		 ChartRenderingInfo info = new ChartRenderingInfo(new org.jfree.chart.entity.StandardEntityCollection());
		 org.jfree.chart.servlet.ServletUtilities.saveChartAsPNG(jfreechart, width, height, info,null);
		 java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
		 java.io.PrintWriter pw = new java.io.PrintWriter(baos);
		 pw.write("<script language=\"JavaScript\">"
								+ "function overlib(){"
								+ "}"
								+ "function nd(){"
								+ "}" + "</script></head><body>");
		 ChartUtilities.writeImageMap(pw, "map020", info, new ChartToolTipTagFragmentGenerator(), new ChartURLTagFragmentGenerator());
		 pw.write("<p align=\"center\"><img src=\""+request.getContextPath()+"/temp/"
						+ taskId + ".png\" usemap=\"#map020\" border=\"0\"></p>");
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
	 }catch(Exception ex){
		 ex.printStackTrace();
	 }
%>