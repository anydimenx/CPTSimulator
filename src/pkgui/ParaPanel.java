package pkgui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import pkagent.Agent;
import pkfield.Field;
import pkparameter.DefNum;
import pksimulator.CPTSimulator;


import java.text.DecimalFormat;

public class ParaPanel extends JPanel implements DefNum {
	private JLabel jl1 = new JLabel();
	private JLabel jl2 = new JLabel();
	private JLabel jl3 = new JLabel();
	private JLabel jl4 = new JLabel();
	private DecimalFormat df1;
	private GridBagLayout gridbag = new GridBagLayout();
	private GridBagConstraints constraints = new GridBagConstraints();
	private Field fld;
	private Agent agt;

	public ParaPanel(Agent agt_) {
		// this.setPreferredSize(new Dimension(100, 100));
		setBounds(TRAJP_W + 50, 0, 150, 200);
		// setBackground(Color.GRAY);
		setLayout(gridbag);
		insertCell(jl1, 0, 0, 1, 1);
		insertCell(jl2, 0, 1, 1, 1);
		insertCell(jl3, 0, 2, 1, 1);
		insertCell(jl4, 0, 3, 1, 1);

		agt = agt_;
		df1 = new DecimalFormat("0.0");
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		jl1.setText("t: " + df1.format(CPTSimulator.count / (double)SEC2CNT));
		jl2.setText("x: " + df1.format(agt.getGx()));
		jl3.setText("y: " + df1.format(agt.getGy()));
		jl4.setText("theta: " + df1.format(agt.getAng()));
	}

	public void insertCell(Component com, int x, int y, int w, int h) {
		constraints.gridx = x; // cell x
		constraints.gridy = y; // cell y
		constraints.gridwidth = w; // cell結合
		constraints.gridheight = h; // cell結合
		constraints.anchor = GridBagConstraints.CENTER; // セル内の配置
		constraints.fill = GridBagConstraints.BOTH; // セル内コンポーネントの拡大
		constraints.insets = new Insets(5, 0, 5, 0); // セル内コンポーネントの余白

		gridbag.setConstraints(com, constraints);
		add(com);
	}
	public void setAgt(Agent agt_){
		this.agt = agt_;
	}
}
