package shootTrainer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

public final class Target {
	Point drawP;
	Point actualP;
	Color c;
	int radius;

	public Target(Point in_p, int radius, Color c) {
		super();
		drawP = in_p;
		actualP = (Point) in_p.clone();
		this.radius = radius;
		this.c = c;
		this.drawP.x = (int) (drawP.getX() - (radius));
		this.drawP.y = (int) (drawP.getY() - (radius));

	}

	public void hit() {
		if (this.c == Color.blue)
			this.c = Color.GREEN;
		else
			this.c = Color.blue;

	}

	public void draw(Graphics2D g) {

		// Draw target
		g.setColor(this.c);
		g.fillArc(drawP.x, drawP.y, radius * 2, radius * 2, 0, 360);
		g.setColor(Color.MAGENTA);
		g.drawLine(drawP.x, drawP.y, actualP.x, actualP.y);
	}

	public boolean determineIfTargetIsHit(int shootX, int shootY) {
		boolean retval = false;

		double distance = actualP.distance(shootX, shootY);
		if (distance < radius)
			retval = true;
		else {
			retval = false;
		}
		System.out.print("Shoot(" + shootX + "," + shootY + ")");
		if (retval)
			System.out.println(" Hit!");
		else
			System.out.println(" No hit...");

		System.out.println("Distance : " + actualP.distance(shootY, shootY));
		return retval;
	}

	public void isHitEvent(MouseEvent evt) {
		if (determineIfTargetIsHit(evt.getX() - 8, evt.getY() - 31))
			hit();

	}

}
