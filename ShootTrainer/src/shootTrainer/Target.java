package shootTrainer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Random;

public final class Target
{
	private Point drawP;

	private Point deltaMove;
	// Radius attributes
	private double radius;
	private double deltaRadius;
	private int minRadius;
	private int maxRadius = 200;

	private int hitsToKillTarget = 2;
	private int hitsSoFar = 0;

	private Color c;

	public Color getC()
	{
		return c;
	}

	static public final int randInt(int min, int max)
	{
		Random rand = new Random();
		if (max <= 0)
		{
			max = 1;
		}
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	// Constructor
	public Target(Point inP, int radius, Color c)
	{
		super();
		init(inP, radius, c);
	}

	// Constructor - no input = random
	public Target()
	{
		// generate random positions
		c = Color.green;
		deltaRadius = 1;
		minRadius = 10;
		maxRadius = randInt(10, 200);
		deltaMove = new Point(0, 0);

		drawP = new Point(randInt(300, 1300), randInt(200, 700));
		if (randInt(1, 10) >= 3)
		{
			deltaMove = new Point(randInt(1, 4) - 2, randInt(1, 4) - 2);
		}
		radius = randInt(10, 250);
		if (randInt(1, 10) >= 5)
		{
			deltaRadius = 1.0005;
			maxRadius = randInt(10, 50);
			;
		} else
		{
			deltaRadius = 0.99;
		}
		hitsSoFar = 0;
		hitsToKillTarget = 1;
	}

	// Methods

	// Getters
	public Point getDrawP()
	{
		return drawP;
	}

	public void changeSizeOfTarget()
	{

		if (deltaRadius != 1)
		{
			this.radius *= deltaRadius;
		}
		if (radius > maxRadius)
		{
			radius = maxRadius;
		}
		if (radius < minRadius)
		{
			radius = minRadius;
		}

	}

	public void debugDetermineIfTargetIsHit(int shootX, int shootY, boolean retVal)
	{
		System.out.print("Shoot(" + shootX + "," + shootY + ")");
		if (retVal)
		{
			System.out.println(" Hit! Radius :" + radius + " ");
		} else
		{
			System.out.println(" No hit...");
		}

		System.out.println("Distance : " + drawP.distance(shootY, shootY));

	}

	public boolean determineIfTargetIsHit(int shootX, int shootY)
	{
		boolean retVal = false;

		double distance = drawP.distance(shootX, shootY);
		if (distance < radius)
		{
			retVal = true;
		} else
		{
			retVal = false;
		}

		// debugDetermineIfTargetIsHit(shootX, shootY, retVal);

		return retVal;
	}

	public void drawDebugInfo(Graphics2D g)
	{
		// debug info
		g.setColor(Color.MAGENTA);
		g.drawRect(drawP.x - (int) radius, drawP.y - (int) radius, (int) getDiameter(), (int) getDiameter());

	}

	public void draw(Graphics2D g)
	{
		modifyTarget();

		// Draw target

		g.setColor(this.c);
		if (isDead() == false)
		{
			g.fillArc(drawP.x - (int) radius, drawP.y - (int) radius, (int) getDiameter(), (int) getDiameter(), 0, 360);
		}
		if (isDead() == true)
		{
			g.drawArc(drawP.x - (int) radius, drawP.y - (int) radius, (int) getDiameter(), (int) getDiameter(), 0, 360);
		}

		// drawDebugInfo(g);
	}

	private void modifyTarget()
	{
		// Change target size
		if (isDead() == false)
		{
			changeSizeOfTarget();
		}
		// move target
		moveTarget();

	}

	public double getDiameter()
	{
		return radius * 2.0;
	}

	public void hit()
	{
		hitsSoFar += 1;

		if (hitsSoFar >= hitsToKillTarget)
		{
			dead(true);
			this.c = Color.blue;
		}

	}

	public boolean isDead()
	{
		if (hitsSoFar >= hitsToKillTarget)
		{

			return true;
		} else
			return false;
	}

	public void dead(boolean b)
	{
		if (b)
		{
			if (hitsSoFar < hitsToKillTarget) this.c = Color.red;
		}
	}

	private void init(Point inP, int radius, Color c)
	{
		drawP = inP;
		this.radius = radius;
		this.c = c;

		// Defaults
		// no move for target
		deltaMove = new Point(0, 0);
		// move size change
		deltaRadius = 1;
		minRadius = 10;
		maxRadius = 200;

	}

	public void isHitEvent(MouseEvent evt)
	{
		if (determineIfTargetIsHit(evt.getX() - 8, evt.getY() - 31))
		{
			hit();
		}
	}

	// public methods
	public void moveTarget()
	{
		// update both points for target
		drawP.x += deltaMove.x;
		drawP.y += deltaMove.y;

	}

	public void setDeltaMove(Point deltaMove)
	{
		this.deltaMove = deltaMove;
	}

	public void setDeltaRadius(double d)
	{
		this.deltaRadius = d;
	}

	public void setMaxRadius(int maxRadius)
	{
		this.maxRadius = maxRadius;
	}

	// Setters
	public void setMinRadius(int minRadius)
	{
		this.minRadius = minRadius;
	}

}
