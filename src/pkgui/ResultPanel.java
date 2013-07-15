package pkgui;

import java.awt.*;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

import pkagent.Agent;
import pkfield.Field;
import pkfield.DynamicPulseTurbulent;
import pkparameter.DefNum;
import pksimulator.CPTSimulator;


public class ResultPanel extends JPanel implements DefNum {
	private Agent agt;
	private Field fld;
	private List<Double> li;
	private int sumSuccess = 0;
	private double sumTime = 0;
	private double sumTimex = 0;
	private DecimalFormat df;
	private double srate = 0;
	private double avg = 0;
	private double std = 0;
	
	public ResultPanel(Agent agt_) {
		setBounds(0, 400, TRAJP_W, 200);
		fld = DynamicPulseTurbulent.getInstance();
		agt = agt_;
		li= new ArrayList<Double>();
		df = new DecimalFormat("0.000");
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		setBackground(Color.GRAY);
		for(int i=0; i<li.size(); i++){
			add(new JLabel(Double.toString(li.get(i))));
		}
		if(li.size() > 0){
			add(new JLabel("::"));
			
			JLabel jl = new JLabel("Success Rate:");
			jl.setForeground(Color.RED);
			add(jl);
			srate = getSRate();
			add(new JLabel(df.format(srate)));
			
			jl = new JLabel("Average Success Time:");
			jl.setForeground(Color.RED);
			add(jl);
			avg = getAvgTime();
			add(new JLabel(df.format(avg)));
			
			jl = new JLabel("StD:");
			jl.setForeground(Color.RED);
			add(jl);
			std = getStdTime();
			add(new JLabel(df.format(std)));
			//System.out.println(val);
		}
	}
	
	public void addTime(double time){
			li.add(time);
			sumTime += time;
			sumTimex += time * time;
		    //System.out.println(li);
	}
	
	public void addSuccess(int sum){
		sumSuccess = sum;
	}
	
	public void init(){
		sumSuccess = 0;
		sumTime = 0;
		sumTimex = 0;
		li.clear();
	}
	
	public double getSRate(){
		return sumSuccess/(double)SIM_NUM;
	}
	
	public double getAvgTime(){
		return sumTime/sumSuccess;
	}
	
	public double getStdTime(){
		//System.out.println(sumTimex/sumSuccess);
		//System.out.println(getAvgTime() * getAvgTime());
		return Math.sqrt(sumTimex/sumSuccess - getAvgTime() * getAvgTime());
	}
	
	public double getMinTime(){
        //è∏èáÇ≈É\Å[ÉgÇ∑ÇÈ
        Collections.sort(li);
        System.out.println(li);
		return li.get(0);
	}
	
	public double getMedTime(){
		if(li.size() % 2 == 0 && li.size() != 0){
			return (li.get(li.size()/2-1) + li.get(li.size()/2))/2;
		}else if(li.size() % 2 != 0){
			return li.get((li.size()-1)/2);
		}else{
			return 0;
		}
	}
}

