package carracegameex;

import java.awt.Graphics2D;
import java.awt.Image;

public class ImageSourceManagement {
	
	private Image image;

	public ImageSourceManagement(Image goruntu) {
		this.image = goruntu;
	}

	public int getWidth() {
		return this.image.getWidth(null);
	}

	public int getHeight() {
		return this.image.getHeight(null);
	}

	
	public void draw(Graphics2D g, int x, int y) {
		g.drawImage(image, x, y, null);
	}
	
	public Image getImage() {
		return image;
		
	}

}
