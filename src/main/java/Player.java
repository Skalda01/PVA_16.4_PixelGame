import java.awt.Image;
import javax.swing.ImageIcon;

public class Player extends Npc {

    private boolean shooting;
    private boolean reloading;
    private int ammo;
    private int maxAmmo;
    private int magazineSize;
    private static final int MAX_MAGAZINE_SIZE = 5;

    public Player(int x, int y, int width, int height, Image image, int health, int maxHealth, int frameIndex, int frameCounter) {
        super(x, y, width, height, image, health, maxHealth, frameIndex, frameCounter);
        this.ammo = 10;
        this.maxAmmo = 10;
        this.magazineSize = MAX_MAGAZINE_SIZE;
    }

    private void setPlayerImage(String path) {
        setImage(new ImageIcon(getClass().getResource(path)).getImage());
    }

    @Override
    public void idle() {
        setPlayerImage("/Soldier_3/Idle.png");
        updateAnimation(7);
    }

    @Override
    public void run() {
        setPlayerImage("/Soldier_3/Run.png");
        updateAnimation(6);
    }

    @Override
    public void shoot() {
        if (!shooting && !reloading && magazineSize > 0) {
            shooting = true;
            setFrameIndex(0);
        }
    }

    public void handleShootingAnimation() {
        if (!shooting) return;

        setPlayerImage("/Soldier_3/Shot_1.png");
        updateAnimation(5);

        if (getFrameIndex() >= 4) {
            setFrameIndex(0);
            shooting = false;
            magazineSize--;
        }
    }

    @Override
    public void reload() {
        if (!reloading && !shooting && magazineSize < MAX_MAGAZINE_SIZE && ammo > 0) {
            reloading = true;
            setFrameIndex(0);
        }
    }

    public void handleReloadingAnimation() {
        if (!reloading) return;

        setPlayerImage("/Soldier_3/Recharge.png");
        updateAnimation(6);

        if (getFrameIndex() >= 5) {
            setFrameIndex(0);
            reloading = false;

            int needed = MAX_MAGAZINE_SIZE - magazineSize;
            int reloadAmount = Math.min(needed, ammo);

            magazineSize += reloadAmount;
            ammo -= reloadAmount;
        }
    }

    // Getters
    public boolean isShooting() { return shooting; }
    public boolean isReloading() { return reloading; }
    public int getAmmo() { return ammo; }
    public int getMaxAmmo() { return maxAmmo; }
    public int getMagazineSize() { return magazineSize; }

    // Setters (if needed externally)
    public void setShooting(boolean shooting) { this.shooting = shooting; }
    public void setReloading(boolean reloading) { this.reloading = reloading; }
}
