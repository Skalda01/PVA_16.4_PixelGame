import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Object {
    private int x, y, width, height;
    private boolean isAnimated;
    private Image image;

    public Object(int x, int y, int width, int height, Image image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
        this.isAnimated = false;
    }

    public void drawObject(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

    public void drawObject(Graphics g, int frameIndex, int spriteWidth, int spriteHeight, int drawWidth, int drawHeight) {
        g.drawImage(image, x, y, drawWidth, drawHeight, null);
    }

    public int getDistance(Object object) {
        double centerX1 = x + width / 2.0;
        double centerY1 = y + height / 2.0;

        double centerX2 = object.getX() + object.getWidth() / 2.0;
        double centerY2 = object.getY() + object.getHeight() / 2.0;

        double deltaX = centerX1 - centerX2;
        double deltaY = centerY1 - centerY2;

        return (int) Math.round(Math.sqrt(deltaX * deltaX + deltaY * deltaY));
    }

    public boolean isCollision(Object object) {
        return new Rectangle(x, y, width, height).intersects(new Rectangle(object.x, object.y, object.width, object.height));
    }

    // Zjednodušené gettery a settery na jeden řádek
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public Image getImage() { return image; }
    public void setImage(Image image) { this.image = image; }

    public boolean isAnimated() { return isAnimated; }
}
