package pkagent;


import java.awt.geom.Point2D;
import java.util.List;

import pkfield.Field;
import pkparameter.DefNum;
import pksimulator.CPTSimulator;

//test tactics class

public class TestMoth implements Agent, DefNum {
	// state
	public static final int STAY = 0;
	public static final int SURGE = 1;
	public static final int ZIGZAG = 2;
	public static final int TURN = 3;
	public int myState = STAY;
	public BrainMap bm = new BrainMap(this);
	
	private double k = 5; // mm
	private double f = 10; //mm
	
	public double angle = Math.PI;
	public Point2D.Double gp = new Point2D.Double(INIT_AX, INIT_AY);
	public Point2D.Double spL = new Point2D.Double(gp.x + f * Math.cos(angle) + k
			* Math.cos(angle + Math.PI / 2), gp.y + f * Math.sin(angle) + k
			* Math.sin(angle + Math.PI / 2));
	public Point2D.Double spR = new Point2D.Double(gp.x + f * Math.cos(angle) + k
			* Math.cos(angle - Math.PI / 2), gp.y  + f * Math.sin(angle)+ k
			* Math.sin(angle - Math.PI / 2));

	private double vel; // velocity
	private double angvel; // angular velocity

	private Tactics tactics;

	public boolean stimuL = false;
	public boolean stimuR = false;
	public boolean sensor = false;

	public Field fd;
	
	public TestMoth(Field fd_) {
		fd = fd_;
	}
	
	public TestMoth() {
	}

	public void setTactics(Tactics tactics_) {
		tactics = tactics_;
	}

	public Tactics getTactics() {
		return tactics;
	}

	public void move() {
		//System.out.println(CPTSimulator.count/10.0 + "	" + gp.x + "	" + gp.y);
		
		boolean is = isSense();
		
		if(is){
			bm.setHitXY(gp);
			//System.out.println(bm.getHitXY());
		}
		
		//update position & angle
		
		//System.out.println(myState);
		
		angle += angvel * CTRLCYCLEMS / 1000;
		//gp.setLocation(-1 + 2 * Math.random() + gp.x + vel * Math.cos(angle) * INTERVAL,
		//		-1 + 2 * Math.random() + gp.y + vel * Math.sin(angle) * INTERVAL);
		gp.setLocation(gp.x + vel * Math.cos(angle) * CTRLCYCLEMS / 1000, gp.y + vel * Math.sin(angle) * CTRLCYCLEMS / 1000);
		spL.setLocation(gp.x + f * Math.cos(angle) + k * Math.cos(angle + Math.PI / 2),
				gp.y  + f * Math.sin(angle)+ k * Math.sin(angle + Math.PI / 2));
		spR.setLocation(gp.x + f * Math.cos(angle) + k * Math.cos(angle - Math.PI / 2),
				gp.y  + f * Math.sin(angle)+ k * Math.sin(angle - Math.PI / 2));
		
		bm.setGridXY(gp);
		bm.setXY(gp);
		
		//System.out.println(is);
		bm.hitsCnt(is);
		
		//System.out.println((int)gp.x/10*10+5 + " " + (int)gp.y/10*10);
		
		//System.out.println(myState);
		//System.out.println(gp);

	}

	public void setPara(double vel_, double angvel_) {
		vel = vel_;
		angvel = angvel_;
	}

	public boolean inArea() {
		return true;
	}

	public boolean isSense() {
		return (stimuL | stimuR) & inArea();
	}

	public boolean isStimuL() {
		return stimuL & inArea();
	}

	public boolean isStimuR() {
		return stimuR & inArea();
	}
	
	public void setStimu(boolean l, boolean r){
		stimuL = l;
		stimuR = r;
	}

	public int getOdorDir() {
		if (stimuL && !stimuR) {
			return LEFT;
		} else if (!stimuL && stimuR) {
			return RIGHT;
		} else {
			int ran = (int) (Math.random() * 10) % 2;
			//System.out.println(ran);
			return ran;
		}
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
		return spL.x;
	}

	public double getspLy() {
		return spL.y;
	}

	public double getspRx() {
		return spR.x;
	}

	public double getspRy() {
		return spR.y;
	}

	public double getAng() {
		return angle;
	}

	public boolean localization() {
		if (distance(gp.x, gp.y) <= 50) {
			return true;
		}
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
}
