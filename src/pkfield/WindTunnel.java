package pkfield;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.StringTokenizer;

import pkparameter.DefNum;
import pksimulator.CPTSimulator;

public class WindTunnel implements Field, DefNum {

	public double rate;

	public double gridRate[][] = new double[NUM_X][NUM_Y];
	private File file;
	private BufferedWriter bw;

	private static Field fld = null;
	private BufferedReader reader;

	private WindTunnel() {
		try {
			reader = new BufferedReader(new FileReader("mapin/mapforsima1.csv"));

			while (true) {
				String line = reader.readLine();
				if (line == null)
					break;
				StringTokenizer t = new StringTokenizer(line, ",");
				int x = Integer.parseInt(t.nextToken());
				int y = Integer.parseInt(t.nextToken());
				double xyrate = Double.parseDouble(t.nextToken());
				gridRate[x][y] = xyrate; // スクリーン座標系
			}
			// System.out.println(gridRate[14][77]);
			reader.close();

		} catch (IOException e) {
		}
		// getGridRate();
	}

	public static Field getInstance() {
		if (fld == null) {
			fld = new WindTunnel();
		}
		return fld;
	}

	public void init() {

	}

	// x not zero and y not zero
	public double getRate(double x_, double y_) {
		if (isTiming()) {
			int px = (int) (x_ * 55 / 100.0);
			int py = 65 - (int) (y_ * 65 / 100.0);

			// System.out.println(px + " " + py);
			if (px < 0 || py < 0 || px > NUM_X - 1 || py > NUM_Y - 1) {
				return 0;
			} else {
				// return gridRate[px][py] * 0.5;
				return gridRate[px][py];
			}
		} else {
			return 0;
		}
	}

	public boolean isTiming() {
		if (CPTSimulator.count % 5 >= 0 && CPTSimulator.count % 5 <= 2) {
			return true;
		} else {
			return false;
		}
	}

	public void getGridRate() {
		for (int x = 0; x < NUM_X; x++) {
			for (int y = 0; y < NUM_Y; y++) {
				System.out.println(gridRate[x][y]);
			}
		}
	}

	public double getAngle() {
		return 0;
	}

	private double distance(double x_, double y_) {
		return Math.sqrt(x_ * x_ + y_ * y_);
	}
}
