package pkgui;


import java.awt.*;

import javax.swing.*;

import pkagent.Agent;
import pkfield.DynamicPulse;
import pkfield.Field;
import pkfield.DynamicPulseTurbulent;
import pkfield.ProbabilisticInfo;
import pkfield.ProbabilisticWindTunnel;
import pkparameter.DefNum;
import pksimulator.CPTSimulator;


public class TrajPanel extends JPanel implements DefNum {

	private MyTransform sc;
	private Agent agt;
	private Field fld;

	public TrajPanel(Agent agt_) {
		// this.setPreferredSize(new Dimension(100, 100));
		setBounds(0, 0, TRAJP_W, TRAJP_H);
		setBackground(Color.WHITE);
		//fld = DynamicPulseTurbulent.getInstance();
		//fld = DynamicPulse.getInstance();
		//fld = ProbabilisticInfo.getInstance();
		fld = ProbabilisticWindTunnel.getInstance();
		agt = agt_;
		sc = new MyTransform();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		MyTransform mt = new MyTransform();

		g2.setColor(new Color(239, 117, 188, 200));
		mt.drawCircle(100, 200, 50, g2);

		for (int y = -99; y < 100; y++) {
			for (int x = 0; x < 600; x++) {
				if(CPTSimulator.count > 1){
					if (fld.getRate(x, y) > 0) {
						g2.setColor(new Color(239, 117, 188, 200));
						mt.fillCircle(mt.toScreenX(x), mt.toScreenY(y), 5, g2);
					}
					if (fld.getRate(x, y) > 0.2) {
						g2.setColor(new Color(245, 100, 100, 150));
						mt.fillCircle(mt.toScreenX(x), mt.toScreenY(y), 5, g2);
					}
					if (fld.getRate(x, y) > 0.4) {
						g2.setColor(new Color(255, 0, 0, 255));
						mt.fillCircle(mt.toScreenX(x), mt.toScreenY(y), 5, g2);
					}
				}
				x += 19;
			}
			y += 19;
		}
	
		double w, h, ux, uy;
		int nx = 7, ny = 4; // x,y ���̖ڐ����i�������j
		w = getWidth(); // �p�l����
		h = getHeight();

		ux = w / nx;
		uy = h / ny; // x,y ���̖ڐ��̒P��
		double x, y; // x,y ���̖ڐ�
		// x,y ���̌r����`��inx�{��ny�{�̍Ō�͘g�O�Ŕ�\���j
		
		// ���r��(x=ux����x=w-ux�܂�)���c��������uy�Ԋu��ny�{�����`��
		// �c�r��(y=uy����y=h-uy�܂�)������������ux�Ԋu��nx�{�����`��
		for (int i = 0; i <= ny; i++) {
			for (int j = 0; j <= nx; j++) {
				x = j * ux;
				y = i * uy;
				g2.setColor(Color.gray);
				g2.drawLine((int) x, (int) y, (int) (w - ux), (int) y);
				// g.drawString(""+i,(int)x,(int)(y-uy/2));
				g2.setColor(Color.gray);
				g2.drawLine((int) x, (int) y, (int) x, (int) (h - uy));
				// g.drawString(""+j,(int)(x-ux/2),(int)y);
			}
		}
		
		//agent trajectory
		/*g2.setColor(new Color(255, 100, 100, 100));
		for(int i=0; i<agt.getXY().size(); i++){
			g2.fillRect(mt.toScreenX((int)(agt.getXY().get(i).x)),
				mt.toScreenY((int)(agt.getXY().get(i).y)), 1, 1);
			//System.out.println(mt.toScreenY(agt.getMapY()*GW));
		}*/
	
		//agent grid trajectory
		g2.setColor(new Color(255, 100, 100, 100));		
		for(int i=0; i<agt.getGridXY().size(); i++){
			g2.fillRect(mt.toScreenX((int)(agt.getGridXY().get(i).x)*GW),
				mt.toScreenY((int)(agt.getGridXY().get(i).y)*GH)-GH/2, GW, GH);
			//System.out.println(mt.toScreenY(agt.getMapY()*GW));
		}
		
		//agent stimuli point
		g2.setColor(new Color(0, 0, 255));
		for(int i=0; i<agt.getHitXY().size(); i++){
			g2.fillRect(mt.toScreenX((int)(agt.getHitXY().get(i).x)),
				mt.toScreenY((int)(agt.getHitXY().get(i).y)), 1, 1);
				//System.out.println(agt.getHitXY().get(i));
		}
		
		// �������m�\�p��
		if (agt.inArea()) {
			g2.setColor(new Color(0, 0, 255, 100));
			mt.fillCircle(mt.toScreenX((int) agt.getGx()),
					mt.toScreenY((int) agt.getGy()), 15, g2);
		}

		// ���E�Z���T�Əd�S�̍��W
		g2.setColor(Color.BLACK);
		mt.fillCircle(mt.toScreenX((int) agt.getGx()),
				mt.toScreenY((int) agt.getGy()), 2, g2);
		g2.setColor(Color.RED);
		mt.fillCircle(mt.toScreenX((int) agt.getspLx()),
				mt.toScreenY((int) agt.getspLy()), 2, g2);
		g2.setColor(Color.GREEN);
		mt.fillCircle(mt.toScreenX((int) agt.getspRx()),
				mt.toScreenY((int) agt.getspRy()), 2, g2);

		// ���E�Z���T���m���
		if (agt.isStimuL()) {
			g2.setColor(new Color(255, 242, 55, 200));
			mt.fillCircle(mt.toScreenX((int) agt.getspLx()),
					mt.toScreenY((int) agt.getspLy()), 5, g2);
		}
		if (agt.isStimuR()) {
			g2.setColor(new Color(255, 242, 55, 200));
			mt.fillCircle(mt.toScreenX((int) agt.getspRx()),
					mt.toScreenY((int) agt.getspRy()), 5, g2);
		}

	}
	
	public void setAgt(Agent agt_){
		this.agt = agt_;
	}
}
