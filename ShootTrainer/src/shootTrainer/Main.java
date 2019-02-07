package shootTrainer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel
	{

		private static int maxTargets = 38;
		public static int numberOfTargets = 1;
		public static int totalNumberOfTargetIsHit = 0;

		public Main()
			{
				// initTargetsList();
				initRandomTargetList();
			}

		private static final long serialVersionUID = 1L;

		public static int getScreenWorkingWidth()
			{
				return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
			}

		public static int getScreenWorkingHeight()
			{
				return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
			}

		public static ArrayList<Target> allTargets = new ArrayList<Target>();

		static public void initRandomTargetList()
			{
				allTargets.removeAll(allTargets);
				int count = numberOfTargets;
				totalNumberOfTargetIsHit = 0;

				//
				for (int i = 1; i <= count; i++)
					{
						Target newTarget = new Target();
						allTargets.add(newTarget);
					}

			}

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

				// test case #3 - stand still and change to bigger size
				Target newTarget3 = new Target(new Point(400, 400), 10, Color.GREEN);
				newTarget3.setDeltaMove(new Point(0, 0));
				newTarget3.setMaxRadius(60);
				newTarget3.setMinRadius(10);
				newTarget3.setDeltaRadius(1.005);
				allTargets.add(newTarget3);

				// test case #3.1 - stand still and change to smaller size
				Target newTarget5 = new Target(new Point(600, 800), 100, Color.GREEN);
				newTarget5.setDeltaMove(new Point(0, 0));
				newTarget5.setMaxRadius(190);
				newTarget5.setMinRadius(10);
				newTarget5.setDeltaRadius(0.995);
				allTargets.add(newTarget5);

				// test case #4 - move and change size
				Target newTarget4 = new Target(new Point(400, 600), 100, Color.GREEN);
				newTarget4.setDeltaMove(new Point(2, -1));
				newTarget4.setMaxRadius(130);
				newTarget4.setMinRadius(10);
				newTarget4.setDeltaRadius(1.003);
				allTargets.add(newTarget4);

				// test case ## - move and change size
				Target newTarget6 = new Target(new Point(1100, 600), 50, Color.MAGENTA);
				newTarget6.setDeltaMove(new Point(-2, 0));
				newTarget6.setMaxRadius(230);
				newTarget6.setMinRadius(10);
				newTarget6.setDeltaRadius(1.003);
				allTargets.add(newTarget6);

			}

		static LokalKoordinatsystem aScreen = new LokalKoordinatsystem(getScreenWorkingWidth(),
				getScreenWorkingHeight());

		public void paint(Graphics g)
			{
				super.paint(g);

				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				this.setBackground(Color.black);
				totalNumberOfTargetIsHit = 0;

				for (Target target : allTargets)
					{

						// remove target if it is too close to border
						if (target.getDrawP().x < 100)
							target.closeToBorder(true);
						if (target.getDrawP().x > getScreenWorkingWidth() - 100)
							target.closeToBorder(true);

						if (target.getDrawP().y < 100)
							target.closeToBorder(true);
						if (target.getDrawP().y > getScreenWorkingHeight() - 100)
							target.closeToBorder(true);

						target.draw(g2d);
						if (target.getShootsLeft() <= 0)
							{
								totalNumberOfTargetIsHit++;
							}

					}
				if (totalNumberOfTargetIsHit == numberOfTargets)
					{
						numberOfTargets += 1;
						initRandomTargetList();
					}

				g.setColor(Color.blue);
				g.drawRect(100, 100, getScreenWorkingWidth() - 200, getScreenWorkingHeight() - 200);
				drawScoreBoard(g);
				// drawLinesToTarges(g);

			}

		private void drawScoreBoard(Graphics g)
			{
				int i = 0;
				int s = 45;

				for (Target target : allTargets)
					{
						g.setColor(target.getC());
						g.fillArc(s * i + 100, 20, s - 2, s - 2, 0, 360);
						i++;
					}
				g.setColor(Color.darkGray);
				for (int theRest = i; theRest < maxTargets; theRest++)
					{
						g.drawArc(s * theRest + 100, 20, s - 2, s - 2, 0, 360);
					}

			}

		@SuppressWarnings("unused")
		private void drawLinesToTarges(Graphics g)
			{
				for (Target target : allTargets)
					{
						g.setColor(Color.GRAY);
						g.drawLine(100, 100, target.getDrawP().x, target.getDrawP().y);
						g.drawLine(getScreenWorkingWidth() - 100, getScreenWorkingHeight() - 100, target.getDrawP().x,
								target.getDrawP().y);
						g.drawLine(100, getScreenWorkingHeight() - 100, target.getDrawP().x, target.getDrawP().y);
						g.drawLine(getScreenWorkingWidth() - 100, 100, target.getDrawP().x, target.getDrawP().y);
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
				frame.addKeyListener(myKL);

				while (true)
					{
						mainScreen.repaint();
					}

			}

		static public KeyListener myKL = new KeyListener()
			{

				@Override

				public void keyTyped(KeyEvent e)
					{

						System.out.println("Code : " + e.getKeyCode());
						System.out.println("Char : " + e.getKeyChar());
						if (e.getKeyChar() == '+')
							{
								numberOfTargets += 1;
								if (numberOfTargets > maxTargets)
									numberOfTargets = maxTargets;
								initRandomTargetList();
							}
						if (e.getKeyChar() == '-')
							{
								numberOfTargets -= 1;
								initRandomTargetList();
							}

					}

				@Override
				public void keyPressed(KeyEvent arg0)
					{
						// TODO Auto-generated method stub

					}

				@Override
				public void keyReleased(KeyEvent arg0)
					{
						// TODO Auto-generated method stub

					}

			};

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
								// initTargetsList();
								initRandomTargetList();

							}
					}
			};
	}
