package pkagent;
import pkparameter.DefNum;

//Original moth tactics class

public class MothTactics4 implements Tactics, DefNum {
	private final double S_VEL = VPARA; // velocity
	private final double ZT_ANGVEL = AVPARA; // angular velocity

	private final int S_TIME = 5;	//surge time
	private int Z_TIME = 10;	//zigzag time
	private int L_TIME = 70;	//loop time
	
	private int cnt = 0;	//state time count
	public int zState;
	private int zDir;

	// state update
	public void update(Agent agt_) {
	
		if (agt_.isSense()) {
			agt_.setState(SURGE);
			zDir = agt_.getOdorDir();
			cnt = 0; // reset
			zState = 0; // reset
			Z_TIME = 10; // reset
		}
	
		switch (agt_.getState()) {
		case STAY:
			break;
		case SURGE:
			cnt++;
			if (cnt > S_TIME) {
				agt_.setState(ZIGZAG);
				cnt = 1;
				zState = 1;
				Z_TIME = 10;
				//zDir = 1 - zDir;
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
					Z_TIME = 10;
					break;
				case 2:
					Z_TIME = 12;
					break;
				case 3:
					Z_TIME = 14;
					break;
				case 4:
					Z_TIME = 16;
					break;
				case 5:
					agt_.setState(TURN);
					break;
				}
			}
			break;
		case TURN:
			cnt++;
			/*
			if (cnt > L_TIME) {
				switch (zDir) {
				case LEFT: // L to R
					zDir = RIGHT;
					break;
				case RIGHT: // R to L
					zDir = LEFT;
					break;
				}
				cnt = 1;
			}*/
			if(cnt > L_TIME){
				//agt_.setState(STAY);
				cnt = 1;
			}
			break;
		}

		// change parameters
		switch (agt_.getState()) {
		case STAY:
			agt_.setPara(0, 0);
			break;
		case SURGE:
			if (zDir == LEFT) {
				agt_.setPara(S_VEL, 0);
			}
			if (zDir == RIGHT) {
				agt_.setPara(S_VEL, 0);
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
		//System.out.println(zState);
	}
}

