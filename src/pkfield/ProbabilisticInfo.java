package pkfield;

import java.awt.geom.Point2D;
import java.io.*;

import pkparameter.DefNum;

import cern.jet.math.Bessel;

//infotaxis field

public class ProbabilisticInfo implements Field, DefNum {
	public static final double E = 10.0;
	public static final double a = 10 / 1000.0;
	public static final double W = 0.7;
	public static final double D = 1.0;
	public static final double tau = 150;
	private static final double lambda = Math.sqrt(D * tau
			/ (1 + W * W * tau / 4 / D));

	private Point2D.Double pp = new Point2D.Double(OX, OY); // position
	public double angle = 0;

	public double rate;

	public double gridData[][] = new double[200][500];
	private File file;
	private BufferedWriter bw;

	private static Field fld = null;

	private ProbabilisticInfo() {

	}

	public static Field getInstance() {
		if (fld == null) {
			fld = new ProbabilisticInfo();
		}
		return fld;
	}

	int x, y;
	double z;

	public void init(){
		
	}
	
	
	public void getGridRate() {
		try {
			file = new File("mapout/prmn.txt");
			bw = new BufferedWriter(new FileWriter(file, false)); // true�ŒǋL
		} catch (IOException e) {
			System.out.println("FILE_ERROR");
		}

		for (int i = 0; i <= TRAJP_W / 100; i++) {
			x = -100 + 100 * i;
			for (int j = (int) TRAJP_H / 2 / 100; j >= 1; j--) {
				y = -10 * j;
				z = getRate(x, y);
				writeData();
			}
			if (x == 0) {
				y = 0;
				z = D;
				writeData();
			} else {
				y = 0;
				z = getRate(x, y);
				writeData();
			}
			for (int j = 1; j <= (int) TRAJP_H / 2 / 100; j++) {
				y = 100 * j;
				z = getRate(x, y);
				writeData();
			}
			try {
				bw.newLine();
			} catch (IOException e) {
				System.out.println("ERROR");
			}

		}

		try {
			bw.close();
		} catch (IOException e) {
		}
	}

	private void writeData() {
		try {
			bw.write(x + "	" + y + "	" + z);
			bw.newLine();
		} catch (IOException e) {
			System.out.println("ERROR");
		}
	}

	// x not zero and y not zero
	public double getRate(double x_, double y_) {
		rate = E / Math.log(lambda / a) * Math.exp(x_ / 1000 * W / 2 / D)
				* Bessel.k0(distance(x_, y_) / 1000 / lambda) * 0.1;
		// System.out.println(rate);
		return rate;
	}

	public double getAngle() {
		return angle;
	}

	private double distance(double x_, double y_) {
		return Math.sqrt(x_ * x_ + y_ * y_);
	}
}
