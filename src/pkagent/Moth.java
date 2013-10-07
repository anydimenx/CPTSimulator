package pkagent;


import java.awt.geom.Point2D;
import java.util.List;

import pkfield.Field;
import pkparameter.DefNum;
import pksimulator.CPTSimulator;


public class Moth implements Agent, DefNum {
	private int myState;	//state
	
	private BrainMap bm;
	private Tactics tactics;
	private Field fd;
	private OdorSensor antennaL, antennaR;
	
	private double angle;
	private Point2D.Double gp;	
	private double vel; // velocity
	private double angvel; // angular velocity
	private boolean stimuL = false;
	private boolean stimuR = false;
	private boolean sensor = false;

	
	public Moth(Field fd_) {
		fd = fd_;
		myState = STAY;
		bm = new BrainMap();
		gp = new Point2D.Double(INIT_AX, INIT_AY);
		angle = INIT_AANG;
		antennaL = new LeftAntennaWithTh(fd, gp, angle);
		antennaR = new RightAntennaWithTh(fd, gp, angle);
//		antennaL = new LeftAntennaWithFE(fd, gp, angle);
//		antennaR = new RightAntennaWithFE(fd, gp, angle);
	}

	public void move() {
		//System.out.println(CPTSimulator.count/10.0 + "	" + gp.x + "	" + gp.y);
		
		boolean is = (stimuL | stimuR) & inArea();
		if(is){
			bm.setHitXY(gp);
			//System.out.println(bm.getHitXY());
		}
		//System.out.println(angle);
		
		//update position & angle
		angle += angvel * CTRLCYCLEMS / 1000;
		gp.setLocation(gp.x + vel * Math.cos(angle) * CTRLCYCLEMS / 1000, gp.y + vel * Math.sin(angle) * CTRLCYCLEMS / 1000);
		antennaL.move(gp, angle);
		antennaR.move(gp, angle);
		
		bm.setGridXY(gp);
		bm.setXY(gp);
		bm.hitsCnt(is);
	}

	//flapping effect
	public boolean inArea() {
		if (Math.cos(angle - fd.getAngle()) <= Math.cos(FLAPPING_EFFECT) || (myState == STAY)) {
			sensor = true;
		} else {
			sensor = false;
		}
		return sensor;
	}

	public boolean isSense() {
		double rand = Math.random();
		stimuL = antennaL.isSense(rand);
		stimuR = antennaR.isSense(rand);
		return (stimuL | stimuR) & inArea();
	}

	public int getOdorDir() {
		double potL = antennaL.getPotential();
		double potR = antennaR.getPotential();
	
		if(potL > potR){
			return LEFT;
		}else if(potR > potL){
			return RIGHT;
		}else{
			return (int) (Math.random() * 10) % 2;
		}
		
/*		if (stimuL && stimuR) {
			int ran = (int) (Math.random() * 10) % 2;
			return ran;
		} else if (stimuL) {
			return LEFT;
		} else if (stimuR) {
			return RIGHT;
		} else {
			int ran = (int) (Math.random() * 10) % 2;
			return ran;
		}*/
	}
	
	public boolean isStimuL() {
		return stimuL & inArea();
	}

	public boolean isStimuR() {
		return stimuR & inArea();
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
		return antennaL.getap().x;
	}

	public double getspLy() {
		return antennaL.getap().y;
	}

	public double getspRx() {
		return antennaR.getap().x;
	}

	public double getspRy() {
		return antennaR.getap().y;
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
