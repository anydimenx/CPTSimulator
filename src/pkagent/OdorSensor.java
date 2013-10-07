package pkagent;

import java.awt.geom.Point2D;

public interface OdorSensor {
	public void move(Point2D.Double gp_, double angle_);
	public boolean isSense(double rand);
	public double getPotential();
	public Point2D.Double getap();
}
