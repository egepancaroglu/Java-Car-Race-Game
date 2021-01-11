package carracegameex;

public class RoadStrips extends Cars {

	private int stripspeed = 10;
	private GamePanel panel;

	public RoadStrips(GamePanel panel, int x, int y) {
		super("images/RoadStrips.png", x, y);
		this.panel = panel;
		roadspeedTemp = stripspeed;
		dy = stripspeed;
	}

	@Override
	public void move() {
		
		if (y > panel.getHeight()) {
			y = -144;
		}

		super.move();
	}

	@Override
	public void setVerticalSpeed(int dy) {

		if (roadspeedTemp == 0) {
			super.dy = 0;
			if (dy >= 0) {
				super.dy += dy;
				roadspeedTemp = super.dy;
			}
		} else {
			super.dy += dy;
			roadspeedTemp = super.dy;
		}
		super.setVerticalSpeed(dy);

	}
	
	
	@Override
	public void collisioncontrols(Cars collision) {
		
		
	}
}
