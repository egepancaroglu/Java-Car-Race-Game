package carracegameex;

public class MainCar extends Cars {

	private GamePanel panel;

	public MainCar(GamePanel panel, int x, int y) {
		super("images/maincar.jpg", x, y);
		this.panel = panel;
	}

	@Override
	public void move() {
		if (x < 140) {
			x = 140;
			return;
		}

		if (x > 530 - this.getWidth()) {
			x = 530 - this.getWidth();
			return;
		}

		super.move();
	}

	@Override
	public void setHorizontalSpeed(int dx, int strDy) {
		if (strDy == 0) {

			super.dx = 0;
		} else {
			super.dx = dx;
		}

		super.setHorizontalSpeed(dx, strDy);

	}

	@Override
	public void collisioncontrols(Cars collision) {
		
		if (collision instanceof OtherCars) {
			panel.setgameover(true);
		}
		
	}
	
	
}
