package pkgui;
import java.awt.Graphics2D;

public class MyTransform {
	private int x;
	private int y;

	public MyTransform() {
	}

	public int toScreenX(int x) {
		if (x < -100 || x > 600) {
			System.exit(0);
			return -1;
		} else {
			return 100 + x;
		}
	}

	public int toScreenY(int y) {
		if (y < -200 || y > 200) {
			System.exit(0);
			return -1;
		} else {
			return 200 - y;
		}
	}

	public void fillCircle(int x, int y, int r, Graphics2D g2) {
		g2.fillOval(x - r, y - r, 2 * r, 2 * r);
	}
	public void drawCircle(int x, int y, int r, Graphics2D g2) {
		g2.drawOval(x - r, y - r, 2 * r, 2 * r);
	}
}
