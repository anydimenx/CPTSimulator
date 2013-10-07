package pkfield;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.StringTokenizer;

import pkparameter.DefNum;
import pksimulator.CPTSimulator;

public class WindTunnel2 implements Field, DefNum {
	private double w = 0.7;
	private double pwidth = 100; //plume width mm
	private double pcx = 0; //plume center x
	public double rate;
	private static Field fld = null;

	private WindTunnel2() {
	}

	public static Field getInstance() {
		if (fld == null) {
			fld = new WindTunnel2();
		}
		return fld;
	}

	public void init() {
	}
	
	// x not zero and y not zero
	// 1hz plume
	public double getRate(double x_, double y_) {
		int tmpt = CPTSimulator.count % 10;
		if(tmpt == 0){
			tmpt = 0;
		}
		
		pcx = w * tmpt * CTRLCYCLEMS / 1000 * 1000;
		//System.out.println(5.0/60*(pcx - pwidth/2) + " " + 5.0/60*(pcx + pwidth/2));
		
		if(pcx - pwidth/2 <= x_ && pcx + pwidth/2 >= x_
				&& 5.0/60*x_ >= y_ && -5.0/60*x_ <= y_){
			return 1;
		}else{
			return 0;
		}		
	}

	public void getGridRate() {
	}

	public double getAngle() {
		return 0;
	}

	private double distance(double x_, double y_) {
		return Math.sqrt(x_ * x_ + y_ * y_);
	}
}
