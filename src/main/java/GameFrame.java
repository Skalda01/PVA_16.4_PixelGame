import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import javax.swing.*;

public class GameFrame extends JPanel implements KeyListener {
    private final Player player;
    private final Set<Integer> pressedKeys = new HashSet<>();
    private final ArrayList<Enemy> enemies = new ArrayList<>();
    private final Mapa mapa = new Mapa();

    private final int gameSpeed = 3;
    private final int gameWidth = 1500;
    private final int gameHeight = 800;
    private final int shootRange = 500;
    private final int attackRange = 400;
    private final int closeRange = 80;

    public GameFrame() {
        setPreferredSize(new Dimension(gameWidth, gameHeight));
        setFocusable(true);
        addKeyListener(this);

        player = new Player((gameWidth / 2) - 175, 358, 250, 250,
                new ImageIcon(getClass().getResource("/Soldier_3/Idle.png")).getImage(), 100, 100, 0, 0);

        int[] enemyPositions = {-1500, -1000, 1200, 1500, 2000, 2500, 3000};
        for (int x : enemyPositions) {
            enemies.add(new Enemy(x, 430, 180, 180,
                    new ImageIcon(getClass().getResource("/Zombie Man/idle.png")).getImage(), 100, 100, 0, 0));
        }
    }


    public void keyListener() {
        if (pressedKeys.contains(KeyEvent.VK_A) && !player.isReloading() && !player.isShooting()) {
            moveScene(-gameSpeed);
            player.setIsFacingLeft(true);
            player.run();
        } else if (pressedKeys.contains(KeyEvent.VK_D) && !player.isReloading() && !player.isShooting()) {
            moveScene(gameSpeed);
            player.setIsFacingLeft(false);
            player.run();
        } else if (pressedKeys.contains(KeyEvent.VK_R)) {
            player.reload();
        } else if (pressedKeys.contains(KeyEvent.VK_SPACE) && player.getMagazineSize() > 0) {
            player.shoot();
            shoot();
        } else {
            player.idle();
        }
    }

    private void moveScene(int dx) {
        mapa.updateBackgroundOffset(Math.abs(dx), dx < 0);
        moveObjects(dx);
    }

    private void moveObjects(int dx) {
        mapa.getTiles().forEach(tile -> tile.setX(tile.getX() - dx));
        mapa.getTrees().forEach(tree -> tree.setX(tree.getX() - dx));
        enemies.forEach(enemy -> enemy.setX(enemy.getX() - dx));
    }

    public void shoot() {
        Enemy closest = null;


        for (Enemy enemy : enemies) {
            boolean isValidTarget = player.isFacingLeft() != enemy.isFacingLeft();
            int dist = player.getDistance(enemy);

            if (isValidTarget && dist < shootRange) {
                closest = enemy;
            }
        }

        if (closest != null) {
            enemies.remove(closest);
        }
    }

    public void updateEnemies() {
        for (Enemy enemy : enemies) {
            int distance = player.getDistance(enemy);
            enemy.setIsFacingLeft(enemy.getX() > player.getX());
            enemy.setAttack(distance < attackRange);

            if (distance < closeRange) {
                enemy.attack();
            } else if (distance < attackRange) {
                int dir = (enemy.getX() < player.getX()) ? 1 : -1;
                enemy.setX(enemy.getX() + (gameSpeed / 2) * dir);
                enemy.run();
            } else {
                enemy.idle();
            }
        }
    }

    public void animation() {
        if (player.isReloading()) {
            player.handleReloadingAnimation();
        }
        if (player.isShooting()) {
            player.handleShootingAnimation();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawObject(g);
        drawGui(g);
    }

    private void drawObject(Graphics g) {
        mapa.drawBackground(g);
        mapa.drawTiles(g, player);
        mapa.drawTrees(g, player);
        player.drawObject(g, 0, 128, 128, player.getWidth(), player.getHeight());

        for (Enemy enemy : enemies) {
            enemy.drawObject(g, 0, 96, 96, enemy.getWidth(), enemy.getHeight());
        }
    }

    private void drawGui(Graphics g) {
        g.setColor(Color.WHITE);

        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/PixelifySans-VariableFont_wght.ttf")).deriveFont(28f);
            g.setFont(font);
        } catch (Exception e) {
            g.setFont(new Font("Arial", Font.BOLD, 20));
        }

        String text = String.format("HP: %d / %d    |    Ammo: %d / %d    |    Magazine: %d",
                player.getHealth(), player.getMaxHealth(),
                player.getAmmo(), player.getMaxAmmo(),
                player.getMagazineSize());
        g.drawString(text, 20, 770);

        g.drawString("A, D, Space, R", 20,730);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
