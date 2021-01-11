package carracegameex;

import java.awt.Graphics2D;
import java.awt.Rectangle;


public abstract class Cars {

	public int x;
	public int y;
	public int dy, roadspeedTemp;
	protected int dx;
	private ImageSourceManagement manage;

    private Rectangle myCar=new Rectangle();
    private Rectangle otherCars=new Rectangle();

	public Cars(String path, int x, int y) {
		this.manage = ImageSourceStore.get().getImageUpload(path);
		this.x = x;
		this.y = y;
	}
	
	
	public void move() {
		x += dx;
		y += dy;
	}

	public void drawCars(Graphics2D g2d) {
		this.manage.draw(g2d, x, y); 
	}
	
	
    public void setHorizontalSpeed(int dx,int strDy) {
    	
    	
    }
    public boolean collisioncontrol(Cars collision) {
    	myCar.setBounds((int)x, (int)y, (int)manage.getWidth(), (int)manage.getHeight());
    	otherCars.setBounds((int)collision.x, (int)collision.y, (int)collision.manage.getWidth(), (int)collision.manage.getHeight());		
    	return myCar.intersects(otherCars);
    }
    public abstract void collisioncontrols(Cars collision);
    	
    
    
    	

    
    public void setVerticalSpeed(int dy) {   
    
    }

    public int getWidth(){
        return this.manage.getWidth();
    }
    
    public int getHeight(){
        return this.manage.getHeight();
    }
    public void setX(int x) {
		this.x=x;

	}
    public void setY(int y) {
    	this.y=y;
    }
    
}