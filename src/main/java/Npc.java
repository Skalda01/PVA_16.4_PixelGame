import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;

public class Npc extends Object {

    private int health, maxHealth, frameIndex = 0, frameCounter = 0;
    private boolean isFacingLeft = true, isAlive = true;

    public Npc(int x, int y, int width, int height, Image image, int health, int maxHealth, int frameIndex, int frameCounter) {
        super(x, y, width, height, image);
        this.health = health;
        this.maxHealth = maxHealth;
    }

    public void idle() { setImage(new ImageIcon(getClass().getResource(null)).getImage()); updateAnimation(0); }
    public void run() { setImage(new ImageIcon(getClass().getResource(null)).getImage()); updateAnimation(0); }
    public void attack() { setImage(new ImageIcon(getClass().getResource(null)).getImage()); updateAnimation(0); }
    public void die() { setImage(new ImageIcon(getClass().getResource(null)).getImage()); updateAnimation(0); }
    public void reload() { setImage(new ImageIcon(getClass().getResource(null)).getImage()); updateAnimation(0); }
    public void shoot() { setImage(new ImageIcon(getClass().getResource(null)).getImage()); updateAnimation(0); }

    public void updateAnimation(int totalFrames) {
        if (++frameCounter >= 10) {
            setFrameIndex((frameIndex + 1) % totalFrames);
            frameCounter = 0;
        }
    }

    @Override
    public void drawObject(Graphics g, int getFrameIndex, int spriteWidth, int spriteHeight, int drawWidth, int drawHeight) {
        if (getImage() != null) {
            int columns = getImage().getWidth(null) / spriteWidth;
            int spriteX = (frameIndex % columns) * spriteWidth;
            int spriteY = (frameIndex / columns) * spriteHeight;
            Graphics2D g2d = (Graphics2D) g.create();

            if (isFacingLeft()) {
                g2d.drawImage(getImage(), getX() + drawWidth, getY(), getX(), getY() + drawHeight, spriteX, spriteY, spriteX + spriteWidth, spriteY + spriteHeight, null);
            } else {
                g2d.drawImage(getImage(), getX(), getY(), getX() + drawWidth, getY() + drawHeight, spriteX, spriteY, spriteX + spriteWidth, spriteY + spriteHeight, null);
            }

            g2d.dispose();
        } else {
            g.fillRect(getX(), getY(), drawWidth, drawHeight);
        }
    }

    // Zjednodušené gettery a settery
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public boolean isFacingLeft() { return isFacingLeft; }
    public boolean isAlive() { return isAlive; }
    public int getFrameIndex() { return frameIndex; }
    public int getFrameCounter() { return frameCounter; }

    public void setHealth(int health) { this.health = health; }
    public void setMaxHealth(int maxHealth) { this.maxHealth = maxHealth; }
    public void setFrameIndex(int frameIndex) { this.frameIndex = frameIndex; }
    public void setFrameCounter(int frameCounter) { this.frameCounter = frameCounter; }
    public void setIsFacingLeft(boolean isFacingLeft) { this.isFacingLeft = isFacingLeft; }
    public void setIsAlive(boolean isAlive) { this.isAlive = isAlive; }
}
