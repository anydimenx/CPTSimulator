package pkagent;

import java.awt.geom.Point2D;

import pkfield.Field;

public class LeftAntenna implements OdorSensor {
	private Point2D.Double gp;
	private double angle;
	private Point2D.Double spL;
	private double k = 5; // mm
	private double f = 10; //mm
	private boolean stimuL = false;
	private Field fd;
	
	public LeftAntenna(Field fd_, Point2D.Double gp_, double angle_){
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
		if (rand <= fd.getRate(spL.x, spL.y)) {
			stimuL = true;
		}
		System.out.print(fd.getRate(spL.x, spL.y) + " ");
		return stimuL;
	}
	
	public double getPotential(){
		return -1;
	}
	
	public Point2D.Double getap() {
		return spL;
	}
}
