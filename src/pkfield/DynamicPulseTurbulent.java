package pkfield;


import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import pkparameter.DefNum;
import pksimulator.CPTSimulator;


//dynamic ticl4 turbulent pulse class

public class DynamicPulseTurbulent implements Field, DefNum {
	private int recnt = 1;
	private int old_count = 1;

	private Point2D.Double pp = new Point2D.Double(OX, OY); // position
	public double angle = 0;

	public double rate;

	public double gridData[][] = new double[200][500];
	private File file;
	private BufferedWriter bw;

	private static Field fld = null;

	
	public DynamicPulseTurbulent() {
	}
	
	public static Field getInstance() {
		if (fld == null) {
			fld = new DynamicPulseTurbulent();
		}
		return fld;
	}

	public int loadPic(int index, double x, double y) {
		String name0;
		if(CPTSimulator.count != old_count){
			if(old_count % 100 == 0){
				recnt = 1;
			}	
		}		
		
		if (recnt < 10) {
			name0 = "images/dyanmicPulseTurbulent/a1_" + "000" + recnt + ".bmp";
		} else if (10 <= recnt && recnt < 100) {
			name0 = "images/dyanmicPulseTurbulent/a1_" + "00" + recnt + ".bmp";
		} else if (100 <= recnt && recnt < 1000) {
			name0 = "images/dyanmicPulseTurbulent/a1_" + "0" + recnt + ".bmp";
		} else {
			name0 = "images/dyanmicPulseTurbulent/a1_" + "error" + ".bmp";
		}
		
		if(CPTSimulator.count != old_count){
			recnt++;
		}
		old_count = CPTSimulator.count;
		
		File f = new File(name0);
		BufferedImage read;

		try {
			read = ImageIO.read(f);

			int w = read.getWidth();
			int h = read.getHeight();
			//System.out.println(recnt);
			
			int c = read.getRGB((int)(0.55*x), (int)(65-0.65*y));
			//int c = read.getRGB((int)(x), (int)((65-y)));
			//int c = read.getRGB(100, 65);
			int r = r(c);
			int g = g(c);
			int b = b(c);
			//System.out.println((int)(0.55*x) + " " + (int)(65-0.65*y));
			int rgb = rgb(r, g, b);
			
			return r;
		} catch (IOException e) {
			//e.printStackTrace();
			return -1;
		}
	}


	// 検出確率計算メソッド
	// @override
	public double getRate(double x_, double y_) {
		int lv = loadPic(CPTSimulator.count, x_, y_);
		
		if (lv == 255) {
			rate = 1.0;
		} else {
			rate = 0.0;
		}
		
		return rate;
	}

	//RGB色の取得
	
	public static int a(int c) {
		return c >>> 24;
	}

	public static int r(int c) {
		return c >> 16 & 0xff;
	}

	public static int g(int c) {
		return c >> 8 & 0xff;
	}

	public static int b(int c) {
		return c & 0xff;
	}

	public static int rgb(int r, int g, int b) {
		return 0xff000000 | r << 16 | g << 8 | b;
	}

	public static int argb(int a, int r, int g, int b) {
		return a << 24 | r << 16 | g << 8 | b;
	}
	
	public void getGridRate(){
	}

	public double getAngle() {
		return angle;
	}
	
	public void init(){
		recnt = 1;
		old_count = 1;
	}
}
