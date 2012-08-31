<%@ page contentType="text/html;charset=UTF-8" %>
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

%>
<%
         String path = this.getServletContext().getRealPath("")+"/temp/";

		 int width = 584;
         int height = 424;

		 DefaultCategoryDataset dataset = new DefaultCategoryDataset();
          
		 int max = 0;
		 int number = 0;
        
         dataset.addValue(15, "水果", "苹果");
		 dataset.addValue(15, "水果", "梨");
		 dataset.addValue(20, "水果", "葡萄");
		 dataset.addValue(50, "水果", "桔子");

		java.text.DecimalFormat format = new java.text.DecimalFormat("##.00");
		JFreeChart jfreechart = ChartFactory.createBarChart("水果产量柱状图", null, "数量",dataset, PlotOrientation.VERTICAL, false, true, false);
        TextTitle texttitle = jfreechart.getTitle();
		texttitle.setFont(new Font("宋体", 0, 15));
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
        
		Paint[] paint = new Paint[12];
        paint[0] = new GradientPaint(0.0F, 0.0F, Color.white, 0.0F, 0.0F, Color.green);
		paint[1] = new GradientPaint(0.0F, 0.0F, Color.white, 0.0F, 0.0F, Color.blue);
        paint[2] = new GradientPaint(0.0F, 0.0F, Color.white, 0.0F, 0.0F, Color.red);
        paint[3] = new GradientPaint(0.0F, 0.0F, Color.white, 0.0F, 0.0F, Color.magenta);
        paint[4] = new GradientPaint(0.0F, 0.0F, Color.white, 0.0F, 0.0F, Color.magenta);
		paint[5] = new GradientPaint(0.0F, 0.0F, Color.white, 0.0F, 0.0F, Color.magenta);
		paint[6] = new GradientPaint(0.0F, 0.0F, Color.white, 0.0F, 0.0F, Color.magenta);
		paint[7] = new GradientPaint(0.0F, 0.0F, Color.white, 0.0F, 0.0F, Color.magenta);
		paint[8] = new GradientPaint(0.0F, 0.0F, Color.white, 0.0F, 0.0F, Color.magenta);
		paint[9] = new GradientPaint(0.0F, 0.0F, Color.white, 0.0F, 0.0F, Color.magenta);
		paint[10] = new GradientPaint(0.0F, 0.0F, Color.white, 0.0F, 0.0F, Color.magenta);
		paint[11] = new GradientPaint(0.0F, 0.0F, Color.white, 0.0F, 0.0F, Color.magenta);


        CustomBarRenderer custombarrenderer = new CustomBarRenderer(paint);
        custombarrenderer.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.CENTER_HORIZONTAL));
		custombarrenderer.setItemLabelsVisible(true);
		custombarrenderer.setItemLabelGenerator(new org.jfree.chart.labels.StandardCategoryItemLabelGenerator());
		custombarrenderer.setToolTipGenerator(new org.jfree.chart.labels.StandardCategoryToolTipGenerator());
        categoryplot.setRenderer(custombarrenderer);
        NumberAxis numberaxis = (NumberAxis)categoryplot.getRangeAxis();
        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        numberaxis.setRange(0.0D, 100);
        numberaxis.setTickMarkPaint(Color.black);
		numberaxis.setLowerMargin(0.14999999999999999D);
		numberaxis.setUpperMargin(0.14999999999999999D);

         java.awt.image.BufferedImage bi = jfreechart.createBufferedImage(width, height);
		 org.jfree.chart.encoders.EncoderUtil.writeBufferedImage(bi, "png",
								new java.io.FileOutputStream(path +  "123.png"));
		 ChartRenderingInfo info = new ChartRenderingInfo(new org.jfree.chart.entity.StandardEntityCollection());
		 org.jfree.chart.servlet.ServletUtilities.saveChartAsPNG(jfreechart, width, height, info,null);
		 try{
		 java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
		 java.io.PrintWriter pw = new java.io.PrintWriter(baos);
		 pw.write("<script language=\"JavaScript\">"
								+ "function overlib(){"
								+ "}"
								+ "function nd(){"
								+ "}" + "</script></head><body>");
		 ChartUtilities.writeImageMap(pw, "map020", info, new StandardToolTipTagFragmentGenerator(), new StandardURLTagFragmentGenerator());
		 pw.write("<p align=\"center\"><img src=\""+request.getContextPath()+"/temp/"
						+  "123.png\" usemap=\"#map020\" border=\"0\"></p>");
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