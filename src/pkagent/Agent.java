package pkagent;

import java.awt.geom.Point2D;
import java.util.List;

public interface Agent {

	public void setTactics(Tactics tactics_);

	public Tactics getTactics();

	public void move();

	public void setPara(double vel_, double angvel_);

	public boolean inArea();

	public boolean isSense();

	public int getOdorDir();

	public void setState(int state_);

	public int getState();

	public double getGx();

	public double getGy();

	public double getspLx();

	public double getspLy();

	public double getspRx();

	public double getspRy();

	public double getAng();

	public boolean localization();

	public boolean isStimuL();

	public boolean isStimuR();

	public List<Point2D.Double> getGridXY();
	
	public List<Point2D.Double> getXY();
	
	public List<Point2D.Double> getHitXY();
	
	public double getHz();
	
	public void setStimu(boolean l, boolean r);
}
