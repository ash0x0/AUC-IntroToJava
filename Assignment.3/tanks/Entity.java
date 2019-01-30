package tanks;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;

public class Entity {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Image mImage;
    protected boolean visible;

    public Entity(int x, int y, String imageName) {
        this.x = x;
        this.y = y;
        this.visible = true;
        loadImage(imageName);
        getImageDimensions();
        System.out.println(imageName + " x " + this.x + " y " + this.y);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean isVisible() {
        return visible;
    }

    protected void setVisible(Boolean visible) {
        this.visible = visible;
    }

    protected Image getImage() {
        return mImage;
    }

    protected void loadImage(String imageName) {
        try {
            this.mImage = new ImageIcon(ImageIO.read(getClass().getResource(imageName))).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void getImageDimensions() {
        this.width = this.mImage.getWidth(null);
        this.height = this.mImage.getHeight(null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
