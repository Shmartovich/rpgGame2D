package Tile;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import Entity.Player;

public class TileManager {
    GamePanel gamePanel;
    ArrayList<ArrayList<Integer>> int2DimList = new ArrayList<>();
    int playerX;
    int playerY;
    public TileManager(GamePanel gamePanel, Player player, String mapPath) {
        this.gamePanel = gamePanel;
        this.playerX = player.x;
        this.playerY = player.y;
        loadMap(mapPath);
    }

    public void draw(Graphics2D graphics2D){
        int playerCol = playerX / gamePanel.tileSize;
        int playerRow = playerY / gamePanel.tileSize;
        int col = 0;
        int row = 0;
        int tileID;
        for (int i = playerRow - 1; i <= playerRow + 1; i++) {
            for (int j = playerCol - 1; j <= playerCol + 1; j++) {
                // Check constraints
                if (i >= 0 && i < int2DimList.size() && j >= 0 && j < int2DimList.get(0).size()) { //todo be cautious with 0
                    tileID = int2DimList.get(i).get(j);
                    String tilePath = String.format("/tilesNumbered/%d.png", tileID);
                    try {
                        BufferedImage image = ImageIO.read(getClass().getResource(tilePath));
                        graphics2D.drawImage(image, col * gamePanel.tileSize, row * gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);
                        col++;
                        row++;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private void loadMap(String path){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path)))){
            String line;
            String [] words;
            ArrayList<Integer> intList = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                words = line.split(", ");
                for(String str : words){
                    intList.add(Integer.parseInt(str));
                }
                int2DimList.add(new ArrayList<>(intList));
                intList.clear();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
