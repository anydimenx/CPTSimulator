package pkagent;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.apache.commons.lang3.BooleanUtils;

import pkfield.Field;
import pksimulator.CPTSimulator;

public class LeftAntennaBasedOnModel implements OdorSensor {
	private Point2D.Double gp;
	private double angle;
	private Point2D.Double spL;
	private double k = 5; // mm
	private double f = 10; //mm
	private boolean stimuL = false;
	private Field fd;
	
	private double potential = 0;
	ArrayList<Double>  input = new ArrayList<Double>();
	ArrayList<Double>  output = new ArrayList<Double>();

	public LeftAntennaBasedOnModel(Field fd_, Point2D.Double gp_, double angle_){
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
		response();
		System.out.println(CPTSimulator.count*0.1 + "," + 
				output.get(output.size()-1) + "," +
				input.get(input.size()-1));
		
		if(output.get(output.size()-1) > 0.01){
			stimuL = true;
		}else{
			stimuL = false;
		}
		
		return stimuL;
	}
	
	public double getPotential(){
		return -1;
	}
	
	public Point2D.Double getap() {
		return spL;
	}
	
	private void response(){
		input.add((double)BooleanUtils.toInteger(stimuL));
		if(input.size() < 7){
			output.add(0.0);
		}else{
			double tmp = 1.396 * output.get(output.size()-1) - 0.5603 * output.get(output.size()-2)
				+ 0.01553 * input.get(input.size()-6) - 0.01276 * input.get(input.size() - 7);
			output.add(tmp);
		}
	}
		
}
