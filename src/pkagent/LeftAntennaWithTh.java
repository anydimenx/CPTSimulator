package pkagent;

import java.awt.geom.Point2D;

import pkfield.Field;

public class LeftAntennaWithTh implements OdorSensor {
	private Point2D.Double gp;
	private double angle;
	private Point2D.Double spL;
	private double k = 5; // mm
	private double f = 10; //mm
	private boolean stimuL = false;
	private Field fd;
	private double potential = 0;
	private double volt = 0;
	private double C = 0.1;
	private double R = 10;
	
	public LeftAntennaWithTh(Field fd_, Point2D.Double gp_, double angle_){
		fd = fd_;
		gp = gp_;
		angle = angle_;
		spL = new Point2D.Double(gp.x + f * Math.cos(angle) + k
				* Math.cos(angle + Math.PI / 2), gp.y + f * Math.sin(angle) + k
				* Math.sin(angle + Math.PI / 2));
	}
	
	
	public void move(Point2D.Double gp_, double angle_){
		gp = gp_;
		angle = angle_;
		spL.setLocation(gp.x + f * Math.cos(angle) + k * Math.cos(angle + Math.PI / 2),
				gp.y  + f * Math.sin(angle)+ k * Math.sin(angle + Math.PI / 2));
		
	}
	
	public boolean isSense(double rand){
		stimuL = false;
		potential = fd.getRate(spL.x, spL.y);
		if (potential > 0.3) {
			stimuL = true;
		}
		System.out.print(fd.getRate(spL.x, spL.y) + " ");
		return stimuL;
	}
	
	public double getVolt(){
		if(stimuL){
			volt = potential * (1 - Math.exp(-1/C/R));
		}else{
			volt = potential * Math.exp(-1/C/R);
		}
		return volt;
	}
	
	
	public double getPotential(){
		return potential;
	}
	
	public Point2D.Double getap() {
		return spL;
	}
}
