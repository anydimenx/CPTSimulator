package pkagent;
import pkparameter.DefNum;

//original + mr.fukuda tactics class

public class MothTactics2 implements Tactics, DefNum {
	private final double S_VEL = VPARA; //velocity
	private final double ZT_ANGVEL = AVPARA; //angular velocity

	private final int S_TIME = 5;
	private int Z_TIME = 12;

	private int cnt = 0;
	public int zState;
	private int zDir;

	// state update
	public void update(Agent agt_) {
	
		if (agt_.isSense()) {
			if(agt_.getState() == STAY || agt_.getState() == SURGE){
				agt_.setState(SURGE);
				zDir = agt_.getOdorDir();
				cnt = 0; //reset
				zState = 0; //reset
				Z_TIME = 12; //reset
			}
			if(agt_.getState() == ZIGZAG || agt_.getState() == TURN){
				if(zDir == LEFT && agt_.getOdorDir() == RIGHT){
					agt_.setState(SURGE);
					zDir = agt_.getOdorDir();
					cnt = 0; //reset
					zState = 0; //reset
					Z_TIME = 12; //reset
				}
				if(zDir == RIGHT && agt_.getOdorDir() == LEFT){
					agt_.setState(SURGE);
					zDir = agt_.getOdorDir();
					cnt = 0; //reset
					zState = 0; //reset
					Z_TIME = 12; //reset
				}
			}
			
		}
		//System.out.println(agt_.isStimuL() + " " + agt_.isStimuR());
	
		switch (agt_.getState()) {
		case STAY:
			break;
		case SURGE:
			cnt++;
			if (cnt > S_TIME) {
				agt_.setState(ZIGZAG);
				cnt = 1;
				zState = 1;
				Z_TIME = 12;
				zDir = 1 - zDir;
			}
			break;
		case ZIGZAG:
			cnt++;
			if (cnt > Z_TIME) {
				switch (zDir) {
				case LEFT: // L to R
					zDir = RIGHT;
					break;
				case RIGHT: // R to L
					zDir = LEFT;
					break;
				}
				cnt = 1;
				++zState;
				switch (zState) {
				case 1:
					Z_TIME = 12;
					break;
				case 2:
					Z_TIME = 19;
					break;
				case 3:
					Z_TIME = 21;
					break;
				case 4:
					agt_.setState(TURN);
					break;
				}
			}
			break;
		case TURN:
			break;
		}

		//change parameters
		switch (agt_.getState()) {
		case STAY:
			agt_.setPara(0, 0);
			break;
		case SURGE:
			if (zDir == LEFT) {
				agt_.setPara(S_VEL, 0.6);	//shigaki's data 0.6
			}
			if (zDir == RIGHT) {
				agt_.setPara(S_VEL, -0.6);
			}
			break;
		case ZIGZAG:
			switch (zDir) {
			case LEFT:
				agt_.setPara(0, ZT_ANGVEL);
				break;
			case RIGHT:
				agt_.setPara(0, -ZT_ANGVEL);
				break;
			default:
				agt_.setPara(0, 0);
				break;
			}
			break;
		case TURN:
			switch (zDir) {
			case LEFT:
				agt_.setPara(0, ZT_ANGVEL);
				break;
			case RIGHT:
				agt_.setPara(0, -ZT_ANGVEL);
				break;
			}
			break;
		}
	}
}
