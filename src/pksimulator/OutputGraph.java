package pksimulator;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.StandardChartTheme;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.plot.PlotOrientation;

import pkparameter.DefNum;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

public class OutputGraph implements DefNum {
	private String path;
	private static final int width = 300;	//png
	private static final int height = 300;	//png
	
	public OutputGraph(String currentDir){
		path = currentDir;
		//•¶Žš‰»‚¯–hŽ~
		ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
	}
	
	public void plotAll(){
		for(int i=0; i<SIM_NUM; i++){
			try {
				plotXYPng(i);
				plotTXPng(i);
				plotTYPng(i);
				plotTAngPng(i);
				plotTStimuPng(i);
			} catch (Exception e) {
				// TODO Ž©“®¶¬‚³‚ê‚½ catch ƒuƒƒbƒN
				e.printStackTrace();
			}
		}
		System.out.println("output png finish");
		java.awt.Toolkit.getDefaultToolkit().beep();	//beep sound
	}
	
	public void plotXYPng(int i) throws Exception{
			XYSeriesCollection data = new XYSeriesCollection();
		    XYSeries series = new XYSeries("");
			series.add(0, -250);
			series.add(0, 250);
			series.add(500, -250);
			series.add(500, 250);

			FileInputStream fis = new FileInputStream(path + "/data" + i + ".csv");
			InputStreamReader in = new InputStreamReader(fis,"SJIS");

	        BufferedReader br = new BufferedReader(in);

	        String str;
	        while((str = br.readLine()) != null){
	          String[] strAry = str.split(",", 0);
	          series.add(Double.parseDouble(strAry[1]), Double.parseDouble(strAry[2]));
	          //System.out.println(str);
	        }
	        
	        br.close();
	        data.addSeries(series);

	        File pfile = new File(path + "/dataXY" + i +".png");
		    JFreeChart chart = 
		    	      ChartFactory.createScatterPlot("Trajectory",
		    	                                     "x",
		    	                                     "y",
		    	                                     data,
		    	                                     PlotOrientation.VERTICAL, //xy or yx
		    	                                     false, //legend
		    	                                     false, 
		    	                                     false);
		    
	        ChartUtilities.saveChartAsPNG(pfile, chart, width, height);
	}
	
	public void plotTXPng(int i) throws Exception{
		XYSeriesCollection data = new XYSeriesCollection();
	    XYSeries series = new XYSeries("");
		series.add(0, 0);
		series.add(0, 500);
		series.add(300, 0);
		series.add(300, 500);

		FileInputStream fis = new FileInputStream(path + "/data" + i + ".csv");
		InputStreamReader in = new InputStreamReader(fis,"SJIS");

        BufferedReader br = new BufferedReader(in);

        String str;
        while((str = br.readLine()) != null){
          String[] strAry = str.split(",", 0);
          series.add(Double.parseDouble(strAry[0]), Double.parseDouble(strAry[1]));
          //System.out.println(str);
        }
        
        br.close();
        data.addSeries(series);

        File pfile = new File(path + "/dataTX" + i +".png");
	    JFreeChart chart = 
	    	      ChartFactory.createScatterPlot("t - x",
	    	                                     "t",
	    	                                     "x",
	    	                                     data,
	    	                                     PlotOrientation.VERTICAL, //xy or yx
	    	                                     false, //legend
	    	                                     false, 
	    	                                     false);
	    
        ChartUtilities.saveChartAsPNG(pfile, chart, width, height);
	}
	
	public void plotTYPng(int i) throws Exception{
		XYSeriesCollection data = new XYSeriesCollection();
	    XYSeries series = new XYSeries("");
		series.add(0, -200);
		series.add(0, 200);
		series.add(300, -200);
		series.add(300, 200);

		FileInputStream fis = new FileInputStream(path + "/data" + i + ".csv");
		InputStreamReader in = new InputStreamReader(fis,"SJIS");

        BufferedReader br = new BufferedReader(in);

        String str;
        while((str = br.readLine()) != null){
          String[] strAry = str.split(",", 0);
          series.add(Double.parseDouble(strAry[0]), Double.parseDouble(strAry[2]));
          //System.out.println(str);
        }
        
        br.close();
        data.addSeries(series);

        File pfile = new File(path + "/dataTY" + i +".png");
	    JFreeChart chart = 
	    	      ChartFactory.createScatterPlot("t - y",
	    	                                     "t",
	    	                                     "y",
	    	                                     data,
	    	                                     PlotOrientation.VERTICAL, //xy or yx
	    	                                     false, //legend
	    	                                     false, 
	    	                                     false);
	    
        ChartUtilities.saveChartAsPNG(pfile, chart, width, height);
	}
	
	public void plotTAngPng(int i) throws Exception{
		XYSeriesCollection data = new XYSeriesCollection();
	    XYSeries series = new XYSeries("");
		series.add(0, -6*Math.PI);
		series.add(0, 6*Math.PI);
		series.add(300, -6*Math.PI);
		series.add(300, 6*Math.PI);

		FileInputStream fis = new FileInputStream(path + "/data" + i + ".csv");
		InputStreamReader in = new InputStreamReader(fis,"SJIS");

        BufferedReader br = new BufferedReader(in);

        String str;
        while((str = br.readLine()) != null){
          String[] strAry = str.split(",", 0);
          series.add(Double.parseDouble(strAry[0]), Double.parseDouble(strAry[3]));
          //System.out.println(str);
        }
        
        br.close();
        data.addSeries(series);

        File pfile = new File(path + "/dataTAng" + i +".png");
	    JFreeChart chart = 
	    	      ChartFactory.createScatterPlot("t - ang",
	    	                                     "t",
	    	                                     "ang",
	    	                                     data,
	    	                                     PlotOrientation.VERTICAL, //xy or yx
	    	                                     false, //legend
	    	                                     false, 
	    	                                     false);
	    
        ChartUtilities.saveChartAsPNG(pfile, chart, width, height);
	}
	
	public void plotTStimuPng(int i) throws Exception{
		XYSeriesCollection data = new XYSeriesCollection();
	    XYSeries series = new XYSeries("");
		series.add(300, 0);

		FileInputStream fis = new FileInputStream(path + "/data" + i + ".csv");
		InputStreamReader in = new InputStreamReader(fis,"SJIS");

        BufferedReader br = new BufferedReader(in);

        String str;
        while((str = br.readLine()) != null){
          String[] strAry = str.split(",", 0);
          series.add(Double.parseDouble(strAry[0]), Double.parseDouble(strAry[4]));
          series.add(Double.parseDouble(strAry[0]), 2*Double.parseDouble(strAry[5]));
          //System.out.println(str);
        }
        
        br.close();
        data.addSeries(series);

        File pfile = new File(path + "/dataTStimu" + i +".png");
	    JFreeChart chart = 
	    	      ChartFactory.createScatterPlot("t - stimu",
	    	                                     "t",
	    	                                     "stimu",
	    	                                     data,
	    	                                     PlotOrientation.VERTICAL, //xy or yx
	    	                                     false, //legend
	    	                                     false, 
	    	                                     false);
	    
        ChartUtilities.saveChartAsPNG(pfile, chart, width, height);
	}
}
