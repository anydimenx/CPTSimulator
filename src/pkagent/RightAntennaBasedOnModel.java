package pkagent;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.apache.commons.lang3.BooleanUtils;

import pkfield.Field;

public class RightAntennaBasedOnModel implements OdorSensor {
	private Point2D.Double gp;
	private double angle;
	private Point2D.Double spR;
	private double k = 5; // mm
	private double f = 10; // mm
	private boolean stimuR = false;
	private Field fd;

	private double potential = 0;
	ArrayList<Double> input = new ArrayList<Double>();
	ArrayList<Double> output = new ArrayList<Double>();

	public RightAntennaBasedOnModel(Field fd_, Point2D.Double gp_, double angle_) {
		fd = fd_;
		gp = gp_;
		angle = angle_;
		spR = new Point2D.Double(gp.x + f * Math.cos(angle) + k
				* Math.cos(angle - Math.PI / 2), gp.y + f * Math.sin(angle) + k
				* Math.sin(angle - Math.PI / 2));

	}

	public void move(Point2D.Double gp_, double angle_) {
		gp = gp_;
		angle = angle_;
		spR.setLocation(
				gp.x + f * Math.cos(angle) + k * Math.cos(angle - Math.PI / 2),
				gp.y + f * Math.sin(angle) + k * Math.sin(angle - Math.PI / 2));

	}

	public boolean isSense(double rand) {
		stimuR = false;
		if (rand <= fd.getRate(spR.x, spR.y)) {
			stimuR = true;
		}
		response();

		if(output.get(output.size()-1) > 0.01){
			stimuR = true;
		}else{
			stimuR = false;
		}
		
		return stimuR;
	}

	public double getPotential() {
		return -1;
	}

	public Point2D.Double getap() {
		return spR;
	}

	private void response() {
		input.add((double) BooleanUtils.toInteger(stimuR));
		if (input.size() < 7) {
			output.add(0.0);
		} else {
			double tmp = 1.396 * output.get(output.size() - 1) - 0.5603
					* output.get(output.size() - 2) + 0.01553
					* input.get(input.size() - 6) - 0.01276
					* input.get(input.size() - 7);
			output.add(tmp);
		}
	}

}
