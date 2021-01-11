package carracegameex;

public class OtherCars extends Cars {

	private int movementspeed = 10;
	private GamePanel panel;
	public OtherCars(GamePanel panel,String path, int x, int y) {
		super(path, x, y);
		this.panel=panel;
		dy=movementspeed;
		
	}
	@Override
	public void move() {
		if(y>panel.getHeight()) {
			y=-750;
		}
		super.move();
	}
	@Override
	public void collisioncontrols(Cars collision) {
		
		
	}
	
	
	
	

}
