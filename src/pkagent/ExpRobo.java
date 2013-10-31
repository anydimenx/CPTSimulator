package pkagent;


import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import pkfield.Field;
import pkparameter.DefNum;
import pksimulator.CPTSimulator;


public class ExpRobo implements Agent, DefNum {
	private int myState;	//state
	
	private BrainMap bm;
	private Tactics tactics;
	private Field fd;
	
	private Point2D.Double gp;	
	
	private double vel = 26; // velocity
	private double angvel = 0.5; // angular velocity

	private BufferedReader reader;

	ArrayList<Integer> x = new ArrayList<Integer>();
	ArrayList<Integer> y = new ArrayList<Integer>();
	ArrayList<Double>  angle = new ArrayList<Double>();
	ArrayList<Integer> stimuL = new ArrayList<Integer>();
	ArrayList<Integer> stimuR = new ArrayList<Integer>();

	public ExpRobo(Field fd_) {
		fd = fd_;
		bm = new BrainMap(this);
		gp = new Point2D.Double(INIT_AX, INIT_AY);

		try {
			reader = new BufferedReader(new FileReader("exp/a2.csv"));
			
			while(true) {
				try{
					String line = reader.readLine();
					if(line == null) break;
					StringTokenizer st = new StringTokenizer(line, ","); //,ÇÃëOÇ…ãÛîíÇ†ÇÈÇ∆É_ÉÅ
					String _x = st.nextToken();
					String _y = st.nextToken();
					String _angle = st.nextToken();
					String _stimuL = st.nextToken();
					String _stimuR = st.nextToken();
					
					//System.out.println(_x);
					x.add(Integer.parseInt(_x));
					y.add(Integer.parseInt(_y));
					angle.add(Double.parseDouble(_angle));
					stimuL.add(Integer.parseInt(_stimuL));
					stimuR.add(Integer.parseInt(_stimuR));
					
				}catch(IOException e){}
			}
			
			reader.close();			
		} catch(FileNotFoundException e) {
			System.out.println(e);
		} catch(IOException e) {
			System.out.println(e);
		}
	}

	public void move() {
		while(CPTSimulator.count < x.size()){
			boolean is;
			if(stimuL.get(CPTSimulator.count) == 1 | stimuR.get(CPTSimulator.count) == 1){
				is = true;
			}else{
				is = false;
			}
			gp.setLocation(x.get(CPTSimulator.count), y.get(CPTSimulator.count));
			bm.calcS(is);
			CPTSimulator.count++;
		}
	}

	//flapping effect
	public boolean inArea() {
		return false;
	}

	public boolean isSense() {
		return false;
	}

	public int getOdorDir() {
		return -1;
	}
	
	public boolean isStimuL() {
		return false;
	}

	public boolean isStimuR() {
		return false;
	}

	public void setState(int state_) {
		myState = state_;
	}

	public int getState() {
		return myState;
	}

	public double getGx() {
		return gp.x;
	}

	public double getGy() {
		return gp.y;
	}

	public double getspLx() {
		return -1;
	}

	public double getspLy() {
		return -1;
	}

	public double getspRx() {
		return -1;
	}

	public double getspRy() {
		return -1;
	}

	public double getAng() {
		return angle.get(CPTSimulator.count);
	}

	public boolean localization() {

		return false;
	}

	private double distance(double x_, double y_) {
		return Math.sqrt(x_ * x_ + y_ * y_);
	}
	

	public List<Point2D.Double> getGridXY(){
		return bm.getGridXY();
	}
	
	public List<Point2D.Double> getXY(){
		return bm.getXY();
	}
	
	public List<Point2D.Double> getHitXY(){
		return bm.getHitXY();
	}
	
	public double getHz(){
		return bm.getHz();
	}
	
	
	public void setStimu(boolean l, boolean r){
	}
	
	public void setTactics(Tactics tactics_) {
		tactics = tactics_;
	}

	public Tactics getTactics() {
		return tactics;
	}

	public void setPara(double vel_, double angvel_) {
		vel = vel_;
		angvel = angvel_;
	}
}
