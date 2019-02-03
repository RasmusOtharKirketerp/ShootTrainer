package shootTrainer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

public final class Target
{
	private Point drawP;
	private Point actualP;
	private Point deltaMove;
	// Radius attributes
	private double radius;
	private double deltaRadius;
	private int minRadius;
	private int maxRadius = 200;

	private Color c;

	// Constructor
	public Target(Point inP, int radius, Color c)
	{
		super();
		init(inP, radius, c);
	}

	// Methods

	// Getters
	public int getMaxRadius()
	{
		return maxRadius;
	}

	public double getDiameter()
	{
		return radius * 2.0;
	}

	public Point getDeltaMove()
	{
		return deltaMove;
	}

	public int getMinRadius()
	{
		return minRadius;
	}

	public double getDeltaRadius()
	{
		return deltaRadius;
	}

	// Setters
	public void setMinRadius(int minRadius)
	{
		this.minRadius = minRadius;
	}

	public void setMaxRadius(int maxRadius)
	{
		this.maxRadius = maxRadius;
	}

	private void reCalcDrawPoint()
	{
		this.drawP.x = (int) (drawP.getX() - (radius));
		this.drawP.y = (int) (drawP.getY() - (radius));
	}

	private void init(Point inP, int radius, Color c)
	{
		drawP = inP;
		actualP = (Point) inP.clone();
		this.radius = radius;
		this.c = c;
		reCalcDrawPoint();

		// Defaults
		// no move for target
		deltaMove = new Point(0, 0);
		// move size change
		deltaRadius = 1;
		minRadius = 0;
		maxRadius = 200;

	}

	public void setDeltaRadius(double d)
	{
		this.deltaRadius = d;
	}

	public void setDeltaMove(Point deltaMove)
	{
		this.deltaMove = deltaMove;
	}

	// public methods
	public void moveTarget()
	{
		// update both points for target
		drawP.x += deltaMove.x;
		drawP.y += deltaMove.y;

		actualP.x += deltaMove.x;
		actualP.y += deltaMove.y;

	}

	public void changeSizeOfTarget()
	{

		if (deltaRadius != 1)
		{
			this.radius *= deltaRadius;
		}

	}

	public void hit()
	{
		if (this.c == Color.blue)
		{
			this.c = Color.GREEN;
		} else
		{
			this.c = Color.blue;
		}

	}

	public void draw(Graphics2D g)
	{
		// Change target size
		changeSizeOfTarget();
		// move target
		moveTarget();

		// Draw target
		g.setColor(this.c);
		g.fillArc(drawP.x, drawP.y, (int) getDiameter(), (int) getDiameter(), 0, 360);
		g.setColor(Color.MAGENTA);
		g.drawLine(drawP.x, drawP.y, actualP.x, actualP.y);
	}

	public boolean determineIfTargetIsHit(int shootX, int shootY)
	{
		boolean retval = false;

		double distance = actualP.distance(shootX, shootY);
		if (distance < radius)
		{
			retval = true;
		} else
		{
			retval = false;
		}
		System.out.print("Shoot(" + shootX + "," + shootY + ")");
		if (retval)
		{
			System.out.println(" Hit!");
		} else
		{
			System.out.println(" No hit...");
		}

		System.out.println("Distance : " + actualP.distance(shootY, shootY));
		return retval;
	}

	public void isHitEvent(MouseEvent evt)
	{
		if (determineIfTargetIsHit(evt.getX() - 8, evt.getY() - 31))
		{
			hit();
		}

	}

}
