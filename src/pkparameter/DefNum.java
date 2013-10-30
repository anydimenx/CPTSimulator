package pkparameter;
public interface DefNum {
	//agent parameters
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int INIT_AX = 500;
	public static final int INIT_AY = 0;
	public static final int CTRLCYCLEMS = 100;	//ms
	public static final int SEC2CNT = 1000 / CTRLCYCLEMS;
	public static final double FLAPPING_EFFECT = 1*Math.PI/2;
	
	//MothTactics parameters
	public static final double INIT_AANG = Math.PI;//2*Math.PI*Math.random();//Math.PI;
	public static final double VPARA = 26;
	public static final double AVPARA = 0.5;
	public static final int STAY = 0;
	public static final int SURGE = 1;
	public static final int ZIGZAG = 2;
	public static final int TURN = 3;
	
	//field parameters
	public static final int OX = 0;
	public static final int OY = 0;
	public static final int NUM_X = 425;	//picture px
	public static final int NUM_Y = 130;	//picture px
	//simulator parameters
	public static final int SIM_NUM = 100;
	public static final int LIM_TIME = 300 * SEC2CNT;
	
	//gui parameters
	public static final int TRAJP_W = 700;
	public static final int TRAJP_H = 400;
	public static final int GW = 10;	//pixel/grid
	public static final int GH = 10;
	public static final int NUM_GW = 50;
	public static final int NUM_GH = 20;	
}
