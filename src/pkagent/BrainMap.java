package pkagent;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.BooleanUtils;

import pkfield.ProbabilisticWindTunnel;
import pkparameter.DefNum;
import pksimulator.CPTSimulator;

// entropy calculation class

public class BrainMap implements DefNum {
	public static final int MAPX = 700;
	public static final int MAPY = 200;

	List<Point2D.Double> gridXY;
	List<Point2D.Double> hitXY;
	List<Point2D.Double> xy;

	List<Integer> hits;

	double[][] pXY, ppXY; // distribution
	double[][] mapXY, n_mapXY;
	double[][] funcL;
	double[][] mapRate;

	int hitCnt = 0;

	private BufferedReader reader;
	
	private Agent agt;

	public BrainMap(Agent _agt) {
		agt = _agt;
		funcL = new double[MAPX][MAPY];

		try {
			reader = new BufferedReader(new FileReader("mapin/mapmm.csv"));
			while (true) {
				String line = reader.readLine();
				if (line == null)
					break;
				StringTokenizer t = new StringTokenizer(line, ",");
				int x = Integer.parseInt(t.nextToken());
				int y = Integer.parseInt(t.nextToken());
				double xyrate = Double.parseDouble(t.nextToken());
				funcL[x][y+99] = xyrate; // スクリーン座標系
			}
			// System.out.println(gridRate[14][77]);
			reader.close();

		} catch (IOException e) {
		}

		gridXY = new ArrayList<Point2D.Double>();
		gridXY.add(new Point2D.Double((int) (Math.round(INIT_AX / GW - 0.5)),
				(int) (Math.round(INIT_AY / GH))));

		xy = new ArrayList<Point2D.Double>();
		xy.add(new Point2D.Double(INIT_AX, INIT_AY));

		hitXY = new ArrayList<Point2D.Double>();
		hits = new ArrayList<Integer>();

		pXY = new double[NUM_GW][NUM_GH];
		ppXY = new double[NUM_GW][NUM_GH];

		for (int j = 0; j < NUM_GH; j++) {
			for (int i = 0; i < NUM_GW; i++) {
				pXY[i][j] = 1; // prior distribution
				ppXY[i][j] = 1; // Posterior probability distribution
			}
		}

		mapXY = new double[MAPX][MAPY];
		n_mapXY = new double[MAPX][MAPY];

		for (int j = 0; j < MAPY; j++) {
			for (int i = 0; i < MAPX; i++) {
				mapXY[i][j] = 1; // prior distribution
				n_mapXY[i][j] = 1; // Posterior probability distribution
			}
		}

		// calcS();
	}

	public void setGridXY(Point2D.Double xy_) {
		gridXY.add(new Point2D.Double((int) (Math.round(xy_.x / GW - 0.5)),
				(int) (Math.round(xy_.y / GH)))); // addには引数ではなくオブジェクトを渡さないとダメ
		// System.out.println(gridXY.get(gridXY.size()-1));
	}

	public void setXY(Point2D.Double xy_) {
		xy.add(new Point2D.Double(xy_.x, xy_.y));
		// System.out.println(p);
	}

	public void setHitXY(Point2D.Double xy_) {
		hitXY.add(new Point2D.Double(xy_.x, xy_.y));
		// setHitCnt();
		// System.out.println(xy);
		// System.out.println((double)hitCnt/CPTSimulator.count);
	}

	public void hitsOnTraj(boolean is) {
		if (is) {
			hits.add(1);
		} else {
			hits.add(0);
		}
	}

	public void hitsCnt(boolean is) {
		if (hits.size() == 5 * SEC2CNT) {
			hits.remove(0);
		}
		if (is) {
			hits.add(1);
		} else {
			hits.add(0);
		}
		// System.out.println(hits);
		// System.out.println(getHz());

	}

	public double getHz() {
		int sumHits = 0;
		for (int i = 0; i < hits.size(); i++) {
			sumHits += hits.get(i);
		}
		return (double) sumHits / hits.size();
	}

	public List<Point2D.Double> getGridXY() {
		return gridXY;
	}

	public List<Point2D.Double> getXY() {
		return xy;
	}

	public List<Point2D.Double> getHitXY() {
		return hitXY;
	}

	public void calcS(boolean is) {
		
		int ax = (int)agt.getGx();
		int ay = (int)agt.getGy() + 99;
		
		//System.out.println(CPTSimulator.count);		
		
		if (is) {
			for (int j = 0; j < MAPY; j++) {
				for (int i = 0; i < MAPX; i++) {
					//System.out.print(ax-i + " ");
					//System.out.println(ay-j);
					if(ax-i>=0 && Math.abs(ay-j) < 100){
						n_mapXY[i][j] = funcL[ax-i][(ay-j)+99] * mapXY[i][j];
					}else{
						n_mapXY[i][j] = 0;
					}

				}
			}
		} else {
			for (int j = 0; j < MAPY; j++) {
				for (int i = 0; i < MAPX; i++) {
					//刺激受けないときマップ維持
					n_mapXY[i][j] = mapXY[i][j];
					//刺激受けないときもマップ更新
					/*if(ax-i>=0 && Math.abs(ay-j) < 100){
						n_mapXY[i][j] = (1.0 - funcL[ax-i][(ay-j)+99]) * mapXY[i][j];
					}else{
						n_mapXY[i][j] = mapXY[i][j];
					}*/
				}
			}
		}
		
		double sum = 0;		
		for (int j = 0; j < MAPY; j++) {
			for (int i = 0; i < MAPX; i++) {
				mapXY[i][j] = n_mapXY[i][j];
				sum += mapXY[i][j];
				//System.out.print(mapXY[i][j] + ",");
			}
			//System.out.println();
		}
		
		double S = 0;
		for (int j = 0; j < MAPY; j++) {
			for (int i = 0; i < MAPX; i++) {
				mapXY[i][j] = n_mapXY[i][j] / sum;
				if(mapXY[i][j]>0){
					S += -mapXY[i][j] * Math.log(mapXY[i][j]) / Math.log(2);
				}
				//System.out.print(mapXY[i][j] + ",");
			}
			//System.out.println();
		}
		System.out.println(CPTSimulator.count * 0.1 + "," + S + "," + BooleanUtils.toInteger(is) + "," + agt.getAng());
		
		//if(CPTSimulator.count==191){
		try {
			File file = new File("data/map_" + CPTSimulator.count +".csv");
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			for (int j = MAPY-1; j >= 0; j--) {
				for (int i = 0; i < MAPX; i++) {
					pw.print(mapXY[i][j] + ",");
				}
				pw.println();
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//}


	}

}
