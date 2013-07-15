package pkagent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import pkfield.ProbabilisticWindTunnel;
import pkparameter.DefNum;


// entropy calculation class

public class BrainMap implements DefNum {
	List<Point2D.Double> gridXY;
	List<Point2D.Double> hitXY;
	List<Point2D.Double> xy;
	
	List<Integer> hits;

	double[][] pXY, ppXY;	//distribution
	
	int hitCnt = 0;
	
	public BrainMap() {
		gridXY = new ArrayList<Point2D.Double>();
		gridXY.add(new Point2D.Double((int)(Math.round(INIT_AX/GW-0.5)), (int)(Math.round(INIT_AY/GH))));
		
		xy= new ArrayList<Point2D.Double>();
		xy.add(new Point2D.Double(INIT_AX, INIT_AY));
		
		hitXY = new ArrayList<Point2D.Double>();
		hits = new ArrayList<Integer>();
		
		pXY = new double[NUM_GW][NUM_GH];
		ppXY = new double[NUM_GW][NUM_GH];
		
		for(int j=0; j<NUM_GH; j++){
			for(int i=0; i<NUM_GW; i++){
				pXY[i][j] = 1; //prior distribution
				ppXY[i][j] = 1; //Posterior probability distribution
			}
		}
		
		//calcS();
	}
	
	public void setGridXY(Point2D.Double xy_) {
			gridXY.add(new Point2D.Double((int)(Math.round(xy_.x/GW-0.5)),(int)(Math.round(xy_.y/GH))));	//addには引数ではなくオブジェクトを渡さないとダメ
			//System.out.println(gridXY.get(gridXY.size()-1));
	}
	
	public void setXY(Point2D.Double xy_) {
		xy.add(new Point2D.Double(xy_.x, xy_.y));
		//System.out.println(p);
	}
	
	public void setHitXY(Point2D.Double xy_){
		hitXY.add(new Point2D.Double(xy_.x, xy_.y));
		//setHitCnt();
		//System.out.println(xy);
		//System.out.println((double)hitCnt/CPTSimulator.count);
	}
	
	public void hitsCnt(boolean is){
		if(hits.size() == 5*SEC2CNT){
			hits.remove(0);
		}
		if(is){
			hits.add(1);
		}else{
			hits.add(0);
		}
		//System.out.println(hits);
		//System.out.println(getHz());

		calcS(getHz());
	}
	
	public double getHz(){
		int sumHits=0;
		for(int i=0; i<hits.size(); i++){
			sumHits += hits.get(i);
		}
		return (double)sumHits/hits.size();
	}
	
	public List<Point2D.Double> getGridXY(){
		return gridXY;
	}

	public List<Point2D.Double> getXY(){
		return xy;
	}
	
	public List<Point2D.Double> getHitXY(){
		return hitXY;
	}
	
	public void calcS(double hz){
		double sig = 0.3;
		double mu = 0.7;
		
		for(int j=0; j<NUM_GH; j++){
			for(int i=0; i<NUM_GW; i++){
				ppXY[i][j] = 1 / Math.sqrt(2*Math.PI*sig*sig)
						* Math.exp(-(hz-ProbabilisticWindTunnel.getInstance().getRate(5+GW*i, -95+GH*j))*(hz-ProbabilisticWindTunnel.getInstance().getRate(5+GW*i, -95+GH*j))/2/sig/sig) * pXY[i][j];
				//System.out.print(ppXY[i][j] + ",");
			}
			//System.out.println();
		}
		for(int j=0; j<NUM_GH; j++){
			for(int i=0; i<NUM_GW; i++){
				pXY[i][j] = ppXY[i][j];
			}
		}
	}
	
	
}
