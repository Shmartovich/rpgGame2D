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
        int col;
        int row;

            //todo i,j are from which point?! player is a center these are "edges"
            int tileID = int2DimList.get(playerCol).get(playerRow);
            String tilePath = String.format("/tilesNumbered/%d.png", tileID);
            try {
                BufferedImage image = ImageIO.read(getClass().getResource(tilePath));
                graphics2D.drawImage(image, playerCol, playerCol, gamePanel.tileSize,gamePanel.tileSize,null);
            } catch (IOException e) {
                throw new RuntimeException(e);
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
