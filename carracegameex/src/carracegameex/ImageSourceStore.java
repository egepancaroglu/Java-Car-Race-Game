package carracegameex;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageSourceStore {

    private static ImageSourceStore store = new ImageSourceStore();
    private HashMap<String, ImageSourceManagement> imageObj = new HashMap<String, ImageSourceManagement>();

    public static ImageSourceStore get() {
        return store;
    }


    public ImageSourceManagement getImageUploadRotate(String path, int rotateTour) {
    	
        if (imageObj.get(path) != null) {
            return (ImageSourceManagement) imageObj.get(path);
        }

        BufferedImage sourceImage = null;

        try {
            URL url = this.getClass().getClassLoader().getResource(path);

            if (url == null) {
                error("I can't find the source reference file path : " + path);
            }

            sourceImage = rotateImage(ImageIO.read(url),rotateTour);
           
            
        } catch (IOException e) {
            error("Source file could not be uploaded : " + path);
        }

        GraphicsConfiguration configuration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        Image image = configuration.createCompatibleImage(sourceImage.getWidth(), sourceImage.getHeight(), Transparency.BITMASK);
       
        image.getGraphics().drawImage(sourceImage, 0, 0, null);

        ImageSourceManagement rky = new ImageSourceManagement(image);
        this.imageObj.put(path, rky);

        return rky;
    }

    public ImageSourceManagement getImageUpload(String path) {
    	
        if (imageObj.get(path) != null) { 
            return (ImageSourceManagement) imageObj.get(path); 
        }

        BufferedImage sourceImage = null;

        
        try {
        	
            URL url = this.getClass().getClassLoader().getResource(path);

            if (url == null) {
                error("I can't find the source reference file path : " + path);
            }

            sourceImage = ImageIO.read(url); 
        } catch (IOException e) {
            error("Source file could not be uploaded : " + path); 
        }

        GraphicsConfiguration configuration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        Image image = configuration.createCompatibleImage(sourceImage.getWidth(), sourceImage.getHeight(), Transparency.BITMASK);

        image.getGraphics().drawImage(sourceImage, 0, 0, null); 

        ImageSourceManagement rky = new ImageSourceManagement(image); 
        this.imageObj.put(path, rky);

        return rky;
    }

    private void error(String str) {
        System.err.println(str);
        System.exit(0);
    }

    public static BufferedImage rotateImage(BufferedImage img,int rotateTour) {
    	
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage newImage = new BufferedImage(height, width, img.getType());

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                newImage.setRGB(height - 1 - j, i, img.getRGB(i, j));
            }
        }

        rotateTour--;
        if (rotateTour == 0)
            return newImage;
        else
            return rotateImage(newImage, rotateTour);
    }

}
