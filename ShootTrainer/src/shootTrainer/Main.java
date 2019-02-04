package shootTrainer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel
{
	private static final long serialVersionUID = 1L;

	public static int GetScreenWorkingWidth()
	{
		return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
	}

	public static int GetScreenWorkingHeight()
	{
		return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
	}

	static ArrayList<Target> allTargets = new ArrayList<Target>();

	static public void initTargetsList()
	{
		allTargets.removeAll(allTargets);

		// test case #1 - Stand still
		Target newTarget = new Target(new Point(30, 400), 30, Color.GREEN);
		newTarget.setDeltaMove(new Point(0, 0));
		allTargets.add(newTarget);

		// test case #2 - move
		Target newTarget2 = new Target(new Point(100, 100), 100, Color.GREEN);
		newTarget2.setDeltaMove(new Point(0, 1));
		allTargets.add(newTarget2);

		// test case #3 - stand still and change size
		Target newTarget3 = new Target(new Point(400, 400), 100, Color.GREEN);
		newTarget3.setDeltaMove(new Point(0, 0));
		newTarget3.setMaxRadius(290);
		newTarget3.setMinRadius(10);
		newTarget3.setDeltaRadius(1.001);
		allTargets.add(newTarget3);

		// test case #4 - move and change size
		Target newTarget4 = new Target(new Point(400, 600), 100, Color.GREEN);
		newTarget4.setDeltaMove(new Point(1, 1));
		newTarget4.setMaxRadius(290);
		newTarget4.setMinRadius(10);
		newTarget4.setDeltaRadius(1.001);
		allTargets.add(newTarget4);

	}

	public Main()
	{
		initTargetsList();
	}

	static LokalKoordinatsystem aScreen = new LokalKoordinatsystem(GetScreenWorkingWidth() - 400,
			GetScreenWorkingHeight());

	public void paint(Graphics g)
	{
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		this.setBackground(Color.black);

		for (Target target : allTargets)
		{
			target.draw(g2d);

		}

	}

	public static void main(String[] args) throws InterruptedException
	{
		JFrame frame = new JFrame("ShootTrainer");
		Main mainScreen = new Main();

		frame.add(mainScreen);
		frame.setSize(aScreen.maxX, aScreen.maxY);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addMouseListener(ma);

		while (true)
		{
			// Thread.sleep(10);
			mainScreen.repaint();
		}

	}

	static public MouseAdapter ma = new MouseAdapter()
	{

		public void mousePressed(MouseEvent evt)
		{
			if ((evt.getModifiers() & InputEvent.BUTTON1_MASK) != 0)
			{
				for (Target target : allTargets)
				{
					target.isHitEvent(evt);

				}

			}
			if ((evt.getModifiers() & InputEvent.BUTTON3_MASK) != 0)
			{
				initTargetsList();
			}
		}
	};
}
