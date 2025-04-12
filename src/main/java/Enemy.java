import java.awt.Image;
import javax.swing.ImageIcon;

public class Enemy extends Npc {
    private boolean attack, isDead, removeEnemy;

    public Enemy(int x, int y, int width, int height, Image image, int health, int maxHealth, int frameIndex, int frameCounter) {
        super(x, y, width, height, image, health, maxHealth, frameIndex, frameCounter);
        this.attack = false;
        this.isDead = false;
        this.removeEnemy = false;
    }

    private void setEnemyImage(String path) {
        setImage(new ImageIcon(getClass().getResource(path)).getImage());
    }

    @Override
    public void idle() {
        if (!isDead) {
            setEnemyImage("/Zombie Man/Idle.png");
            updateAnimation(8);
        }
    }

    @Override
    public void run() {
        if (!isDead) {
            setEnemyImage("/Zombie Man/Run.png");
            updateAnimation(7);
        }
    }

    @Override
    public void attack() {
        if (!isDead) {
            setEnemyImage("/Zombie Man/Attack_1.png");
            updateAnimation(5);
        }
    }

    public boolean isAttack() { return attack; }
    public void setAttack(boolean attack) { this.attack = attack; }

    public boolean isDead() { return isDead; }
    public void setDead(boolean isDead) { this.isDead = isDead; }

    public boolean isRemoveEnemy() { return removeEnemy; }
    public void setRemoveEnemy(boolean removeEnemy) { this.removeEnemy = removeEnemy; }
}
