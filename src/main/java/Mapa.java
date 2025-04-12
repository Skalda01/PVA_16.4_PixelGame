import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
    

import javax.swing.ImageIcon;

public class Mapa {

    private int gameSpeed = 1;
    private final double[] layerSpeeds = {0.2, 0.4, 0.6, 0.8};
    private Image[] backgroundLayers;
    private int backgroundOffset = 0;
    private int backgroundWidth = 800;
    private int backgroundHeight = 500;
    private int mapMinusBorder = -5000;
    private int mapPlusBorder = 5000;
    private ArrayList<Tile> tiles = new ArrayList<>();
    private ArrayList<Tree> trees = new ArrayList<>();
    private Random random = new Random();
    private Image[] treeImages;
    private final int renderDistance = 1500;
    
    public Mapa() {
        loadResources();
        generateTiles();
        generateNature();
    }



    private void loadResources() {
        backgroundLayers = new Image[4];
        for (int i = 0; i < 4; i++) {
            backgroundLayers[i] = new ImageIcon(getClass().getResource("/Background/Day/layer_" + (i + 1) + ".png")).getImage();
        }
        backgroundWidth = backgroundLayers[0].getWidth(null);


        treeImages = new Image[12];
        for (int i = 0; i < 12; i++) {
            treeImages[i] = new ImageIcon(getClass().getResource("/Trees/" + (i + 1) + ".png")).getImage();
        }
    }


    public void drawBackground(Graphics g) {
        g.setColor(new Color(55, 68, 110));
        g.fillRect(0, 0, 1500, 1000);

        for (int layer = 0; layer < backgroundLayers.length; layer++) {
            int offset = (int) (backgroundOffset * layerSpeeds[layer]) % backgroundWidth;
            if (offset > 0) offset -= backgroundWidth;

            for (int i = 0; i <= 3; i++) {
                g.drawImage(backgroundLayers[layer], offset + i * backgroundWidth, 0, backgroundWidth, backgroundHeight, null);
            }
        }
    }


    
    private void generateTiles() {
        final int tileWidth = 64, tileHeight = 64;
        for (int i = mapMinusBorder; i < mapPlusBorder; i++) {
            int x = i * tileWidth;

            // Top surface tile
            String topTilePath = random.nextInt(8) == 0 ? "Tile_16.png" : "Tile_02.png";
            tiles.add(new Tile(x, 608, tileWidth, tileHeight, loadTileImage(topTilePath)));

            // Underground tiles
            for (int j = 0; j < 2; j++) {
                int y =  672 + j * tileHeight;
                String undergroundTile = random.nextInt(10) == 0 ? "Tile_12.png" : "Tile_11.png";
                tiles.add(new Tile(x, y, tileWidth, tileHeight, loadTileImage(undergroundTile)));
            }
        }
    }

    private Image loadTileImage(String filename) {
        return new ImageIcon(getClass().getResource("/Tiles/" + filename)).getImage();
    }



    private void generateNature() {
        final int treeWidth = 120, treeHeight = 200;
        for (int i = mapMinusBorder; i < mapPlusBorder; i++) {
            int chance = random.nextInt(1500);
            if (chance < treeImages.length) {
                trees.add(new Tree(i, 410, treeWidth, treeHeight, treeImages[chance]));
            }
        }
    }




    public void drawTiles(Graphics g, Player player) {
        for (Tile tile : tiles) {
            if (player.getDistance(tile) < renderDistance) {
                g.drawImage(tile.getImage(), tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight(), null);
            }
        }
    }


    public void drawTrees(Graphics g, Player player) {
        for (Tree tree : trees) {
            if (player.getDistance(tree) < renderDistance) {
                g.drawImage(tree.getImage(), tree.getX(), tree.getY(), tree.getWidth(), tree.getHeight(), null);
            }
        }
    }
   

    public void updateBackgroundOffset(int gameSpeed, boolean isFacingLeft) {
        if(isFacingLeft){
            setBackgroundOffset(getBackgroundOffset() + gameSpeed);
        } else {
            setBackgroundOffset(getBackgroundOffset() - gameSpeed);
        }
    }


    // Zkrácené gettery a settery pro přístup k hodnotám
    public int getBackgroundOffset() { return backgroundOffset; }
    public void setBackgroundOffset(int backgroundOffset) { this.backgroundOffset = backgroundOffset; }
    public List<Tile> getTiles() { return tiles; }
    public List<Tree> getTrees() { return trees; }
}
