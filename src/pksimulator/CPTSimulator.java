package pksimulator;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

import org.apache.commons.lang3.BooleanUtils;

import pkagent.*;
import pkfield.*;
import pkgui.*;
import pkparameter.*;

public class CPTSimulator extends JFrame implements ActionListener,
MouseListener, DefNum {
	
	public static int count = 0;

	private Field fld = ProbabilisticWindTunnel.getInstance();
	//private Field fld = DynamicPulse.getInstance();
	
	public Agent agt = new Moth(fld);

	TrajPanel tp;
	ParaPanel pp;
	ResultPanel rp;
	JPanel bp = new JPanel();

	private JButton bt1 = new JButton("START");
	private JButton bt2 = new JButton("STOP");
	private JButton bt3 = new JButton("Run simulations");
	private JButton bt4 = new JButton("Test tactics");
	private JButton bt5 = new JButton("Output Graph");
	
	private Timer timer;
	private boolean simFlag = false;
	
	public CPTSimulator() {
		super("CPTSimulator");

		tp = new TrajPanel(agt);
		rp = new ResultPanel(agt);
		pp = new ParaPanel(agt);
		bp.setBounds(TRAJP_W + 50, 200, 150, 200);
		// bp.setBackground(Color.GRAY);

		Container container = getContentPane();
		container.setLayout(null);
		container.add(tp);
		container.add(pp);
		container.add(bp);
		container.add(rp);

		addButton(bt1, bp, this);
		addButton(bt2, bp, this);
		addButton(bt3, bp, this);
		addButton(bt4, bp, this);
		addButton(bt5, bp, this);
		addMouseListener(this);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		// コンテントペインに合わせる
		container.setPreferredSize(new Dimension(1000, 600));
		pack();
		setVisible(true);

		timer = new Timer((int) CTRLCYCLEMS, this); // actionListenerをスケジューリング
	}

	void addButton(JButton button, JPanel pn12, ActionListener lolistener) {
		pn12.add(button);
		button.addActionListener(lolistener);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bt1) {
			timer.start();
			simFlag = true;
			agt.setTactics(new MothTactics4());
		}
		if (e.getSource() == bt2) {
			timer.stop();
			simFlag = false;
		}
		if (e.getSource() == bt3) {
			int sumSuccess = 0;

			// simulation loop
			OutputCSV of = new OutputCSV();
			of.mkdir();

			rp.init();
			for (int i = 0; i < SIM_NUM; i++) {
				count = 0;
				fld.init();
				agt = new Moth(fld);
				agt.setTactics(new MothTactics4());
				// output file
				of.mkfile("data" + i + ".csv");
				of.writeData(0);
				of.writeData(agt.getGx());
				of.writeData(agt.getGy());
				of.writeData(agt.getAng());
				of.writeData(BooleanUtils.toInteger(agt.isStimuL()));
				of.writeData(BooleanUtils.toInteger(agt.isStimuR()));
				of.writeData(agt.getGridXY().get(0).x);
				of.writeData(agt.getGridXY().get(0).y);
				of.writeLine();
				// run simulation
				for (int j = 0; j < LIM_TIME; j++) {
					count++;
					agt.getTactics().update(agt);
					agt.move();
					// output file
					of.writeData(CPTSimulator.count / (double)SEC2CNT);
					of.writeData(agt.getGx());
					of.writeData(agt.getGy());
					of.writeData(agt.getAng());
					of.writeData(BooleanUtils.toInteger(agt.isStimuL()));
					of.writeData(BooleanUtils.toInteger(agt.isStimuR()));
					of.writeData(agt.getGridXY().get(j+1).x);
					of.writeData(agt.getGridXY().get(j+1).y);
					of.writeLine();
					// success
					if (agt.localization()) {
						sumSuccess++;
						rp.addTime(CPTSimulator.count / (double)SEC2CNT);
						break;
					}
				}
				of.close();
				of.resultWrite();
			}
			rp.addSuccess(sumSuccess);
			repaint();
			of.memoWrite(rp.getSRate(), rp.getAvgTime(), rp.getStdTime(), rp.getMinTime(), rp.getMedTime());	//record parameters
			java.awt.Toolkit.getDefaultToolkit().beep();	//beep sound
		}
		if(e.getSource() == bt4){
			agt = new TestMoth();
			tp.setAgt(agt);
			pp.setAgt(agt);
			timer.start();
			simFlag = true;
			agt.setTactics(new MothTactics4());
		}

		if (simFlag) {
			agt.getTactics().update(agt);
			agt.move();
			count++;
			repaint();
			if (count == LIM_TIME || agt.localization()) {
				simFlag = false;
			}
		}
		if(e.getSource() == bt5){
			
		    JFileChooser filechooser = new JFileChooser("C:\\Users\\yosuke\\git\\CPTSimulator\\data");
		    filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		    int selected = filechooser.showOpenDialog(this);
		    if (selected == JFileChooser.APPROVE_OPTION){
		      File file = filechooser.getSelectedFile();
			  System.out.println(file.getAbsolutePath());
			  OutputGraph og = new OutputGraph(file.getAbsolutePath());
			  og.plotAll();
		    }
		}
		
	}

	public void mouseClicked(MouseEvent e) {
//		Point point = e.getPoint();
//		System.out.println("x:" + point.x + ",y:" + point.y);
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		int btn = e.getButton();

		if (btn == MouseEvent.BUTTON1){	//left click
			agt.setStimu(true, false);
		}
		if (btn == MouseEvent.BUTTON2){	//middle click
			agt.setStimu(true, true);
		}
		if (btn == MouseEvent.BUTTON3){	//right click
			agt.setStimu(false, true);
		}
	}

	public void mouseReleased(MouseEvent e) {
		int btn = e.getButton();

		if (btn == MouseEvent.BUTTON1){	//left click
			agt.setStimu(false, false);
		}
		if (btn == MouseEvent.BUTTON2){	//middle click
			agt.setStimu(false, false);
		}
		if (btn == MouseEvent.BUTTON3){	//right click
			agt.setStimu(false, false);
		}

	}

	public static void main(String[] args) {
		CPTSimulator cpt = new CPTSimulator();
	}
}
