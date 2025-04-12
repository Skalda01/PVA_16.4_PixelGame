import javax.swing.JFrame;

public class Game implements Runnable {
    private GameFrame frame;

    public Game(GameFrame frame) {
        this.frame = frame;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerFrame = 1_000_000_000.0 / 60.0; // 60 FPS
        double delta = 0;

        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerFrame;
            lastTime = now;

            while (delta >= 1) {

                frame.keyListener();
                frame.animation();
                frame.updateEnemies();
                frame.repaint();
                delta--;



            }
            try {
                Thread.sleep(1); // Zabrání přetížení CPU
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }




    public static void main(String[] args) {
        GameFrame frame = new GameFrame();
        JFrame window = new JFrame("Game");
        window.add(frame);
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        new Thread(new Game(frame)).start();
    }
}
