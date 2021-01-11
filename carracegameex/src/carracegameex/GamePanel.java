package carracegameex;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private int w;
	private Timer tmr;
	private int DELAY = 30;
	private int INITIAL_DELAY = 500;
	private boolean gameover;
	private boolean start;
	private Cars myCar;
	private Cars othercar;
	private RoadStrips strip;
	private int myyCarSpeed = 5;
	private ImageSourceManagement gameoverimage;
	private ImageSourceManagement gamerestart;
	private ImageSourceManagement gamestart;
	private ArrayList<Cars> carslist = new ArrayList<Cars>();
	private ArrayList<RoadStrips> stripslist = new ArrayList<RoadStrips>();
	private long time;
	private long lastTime;

	private int positions[][] = { { 164, -250 }, { 164, -950 }, { 263, -600 }, { 263, -1350 }, { 362, -500 },
			{ 362, -1250 }, { 461, -900 },// {461,-1500},

	};
	private int cars = 0;

	public GamePanel(int w, int h) {
		this.w = w;
		initUI();
		carsload();
		imagemanage();
		stripLoad();
		othercarsLoad();
	}

	public void imagemanage() {
		this.gameoverimage = ImageSourceStore.get().getImageUpload("images/gameover.jpg");
		this.gamerestart = ImageSourceStore.get().getImageUpload("images/restart.png");
		this.gamestart = ImageSourceStore.get().getImageUpload("images/start.png");
	}

	private void othercarsLoad() {
		for (int[] item : positions) {
			carLoad(item[0], item[1]);
		}

	}

	private void carLoad(int x, int y) {
		if (cars % 3 == 0) {
			othercar = new OtherCars(this, "images/stripcar.jpg", x, y);
		} else if (cars % 3 == 1) {
			othercar = new OtherCars(this, "images/lightbluecar.jpg", x, y);
		} else if (cars % 3 == 2) {
			othercar = new OtherCars(this, "images/minibuscar.png", x, y);
		}
		carslist.add(othercar);
		cars++;

	}

	private void stripLoad() {
		for (int i = 0; i < 6; i++) {
			for (int j = 1; j < 4; j++) {
				strip = new RoadStrips(this, 140 + (j * 100), (i * 144));
				stripslist.add(strip);

			}
		}
	}

	private void carsload() {
		myCar = new MainCar(this, 260, 480);
		carslist.add(myCar);
	}

	private void initUI() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		tmr = new Timer(DELAY, this);
		tmr.setInitialDelay(INITIAL_DELAY);
		tmr.start();
		this.gameover = false;
		this.start = true;

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		yolKenarlari(g2d);
		if (start) {
			drawStart(g2d);

		} else {
			if (!gameover) {
				doDraw(g2d);
				speedometer(g2d);
			} else {
				gameoverscreen(g2d);

			}
		}
	}

	private void drawStart(Graphics2D g2d) {
		BufferedImage buf = new BufferedImage(300, 600, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gbi = buf.createGraphics();
		// gbi.setColor(Color.red);
		// gbi.fillRect(0, 0, buf.getWidth(), buf.getHeight());
		gbi.drawImage(this.gamestart.getImage(), (buf.getWidth() - this.gamestart.getWidth()) / 2, 100, null);
		gbi.setFont(new Font("Arial", Font.BOLD, 25));
		gbi.setColor(Color.white);
		String str = "Press Enter for start";
		FontMetrics fm = gbi.getFontMetrics();
		gbi.drawString(str, (buf.getWidth() - fm.stringWidth(str)) / 2, 310);
		g2d.drawImage(buf, (w - buf.getWidth()) / 2 - 10, 50, null);

	}

	private void gameoverscreen(Graphics2D g2d) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Time tm = new Time(lastTime);

		BufferedImage buf = new BufferedImage(300, 600, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gbi = buf.createGraphics();
//		gbi.setColor(Color.red);
//		gbi.fillRect(0, 0, buf.getWidth(), buf.getHeight());
		gbi.drawImage(this.gameoverimage.getImage(), (buf.getWidth() - this.gameoverimage.getWidth()) / 2, 20, null);
		gbi.setFont(new Font("Arial", Font.BOLD, 25));
		gbi.setColor(Color.white);
		String str = "Press Space for restart";
		FontMetrics fm = gbi.getFontMetrics();
		gbi.drawImage(this.gamerestart.getImage(), (buf.getWidth() - this.gamerestart.getWidth()) / 2, 120, null);

		gbi.drawString(str, (buf.getWidth() - fm.stringWidth(str)) / 2, 310);

		str = sdf.format(tm);
		str = "Time : " + str.substring(3);
		gbi.drawString(str, (buf.getWidth() - fm.stringWidth(str)) / 2, 350);

		g2d.drawImage(buf, (w - buf.getWidth()) / 2 - 10, 50, null);
	}

	private void speedometer(Graphics2D g2d) {
		BufferedImage speedometer = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gbi = speedometer.createGraphics();
		gbi.setColor(Color.gray);
		gbi.fillOval(0, 0, 100, 100);
		gbi.setColor(Color.black);
		gbi.setFont(new Font("Arial", Font.BOLD, 25));
		String speed = "" + this.strip.roadspeedTemp + " km/h";
		FontMetrics fm = gbi.getFontMetrics();
		gbi.drawString(speed, (speedometer.getWidth() - fm.stringWidth(speed)) / 2, speedometer.getHeight() / 2 + 5);
		g2d.drawImage(speedometer, (w - 125), 50, null);
	}

	public void yolKenarlari(Graphics2D g) {
		g.setColor(Color.cyan);
		g.fillRect(0, 0, 700, 800);
		g.setColor(Color.gray);
		g.fillRect(130, 0, 10, 800);
		g.fillRect(530, 0, 10, 800);
		g.setColor(Color.black);
		g.fillRect(140, 0, 390, 800);
	}

	private void doDraw(Graphics2D g2d) {
		for (int i = 0; i < stripslist.size(); i++) {
			RoadStrips get = stripslist.get(i);
			get.drawCars(g2d);
		}
		for (int i = 0; i < carslist.size(); i++) {
			Cars get = carslist.get(i);

			get.drawCars(g2d);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!start) {
			if (!gameover) {
				stripsMove();
				carsMove();

			}
		}

		repaint();
	}

	private void stripsMove() {
		for (int i = 0; i < stripslist.size(); i++) {
			RoadStrips get = stripslist.get(i);

			get.move();
		}

	}

	private void carsMove() {
		for (int i = 0; i < carslist.size(); i++) {
			Cars get = carslist.get(i);

			get.move();
		}
		collisioncontrol();
	}

	private void collisioncontrol() {
		for (int i = 0; i < carslist.size(); i++) {
			for (int j = i + 1; j < carslist.size(); j++) {
				Cars myCar = carslist.get(i);
				Cars otherCars = carslist.get(j);

				if (myCar.collisioncontrol(otherCars)) {
					myCar.collisioncontrols(otherCars);
				}
			}
		}

	}

	public Timer getTimer() {
		return this.tmr;
	}

	public void setgameover(boolean gameover) {

		myCar.setX(260);
		for (int i = 1; i < carslist.size(); i++) {
			Cars allcars = carslist.get(i);
			allcars.setY(positions[i - 1][1]);
		}
		this.gameover = gameover;

		
		lastTime = System.currentTimeMillis() - time;
	}

	private class TAdapter implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			int code = e.getKeyCode();

			if (code == KeyEvent.VK_LEFT) {
				myCar.setHorizontalSpeed(-myyCarSpeed, strip.dy);
			}
			if (code == KeyEvent.VK_RIGHT) {
				myCar.setHorizontalSpeed(myyCarSpeed, strip.dy);
			}
			if (code == KeyEvent.VK_UP) {// up
				for (int i = 0; i < stripslist.size(); i++) {
					Cars serit = stripslist.get(i);
					serit.setVerticalSpeed(2);
					

				}
			}
			if (code == KeyEvent.VK_DOWN) {// down
				for (int i = 0; i < stripslist.size(); i++) {
					Cars serit = stripslist.get(i);
					serit.setVerticalSpeed(-2);

				}
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {
			int code = e.getKeyCode();
			if (start) {
				if (code == KeyEvent.VK_ENTER) {
					start = false;
					time = System.currentTimeMillis();
				} else {
					return;
				}
			}
			if (code == KeyEvent.VK_LEFT) {
				myCar.setHorizontalSpeed(0, strip.dy);

			}
			if (code == KeyEvent.VK_RIGHT) {
				myCar.setHorizontalSpeed(0, strip.dy);

			}
			if (code == KeyEvent.VK_SPACE) {
				if (gameover) {
					gameover = false;
					time = System.currentTimeMillis();
				}
			}

		}

	}

}
