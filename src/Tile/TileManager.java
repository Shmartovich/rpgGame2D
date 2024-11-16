package Tile;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import Entity.Player;

public class TileManager {
    private final String pathToTiles = "tilesNumbered/";
    GamePanel gamePanel;
    ArrayList<ArrayList<Integer>> int2DimList = new ArrayList<>();
    BufferedImage [] [] tiles;
    public TileManager(GamePanel gamePanel, String mapPath) {
        this.gamePanel = gamePanel;
        loadMap(mapPath);
    }

    public void draw(Graphics2D graphics2D){
        int screenX = 0;
        int screenY = 0;

        // tile where player is right now
        int startCol = (gamePanel.player.worldX - gamePanel.player.screenX)/ gamePanel.tileSize;
        int startRow = (gamePanel.player.worldY - gamePanel.player.screenY)/ gamePanel.tileSize;
        int i = startRow;
        int j = startCol;
        while(screenY < gamePanel.screenHeight){
            while(screenX < gamePanel.screenWidth){
                BufferedImage image;
                if(i < 0 ||  i >= tiles.length - 1 || j < 0 || j >= tiles[i].length - 1){
                    image = tiles[0][0];
                }
                else{
                    image = tiles[i][j];
                }
                graphics2D.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
                screenX += gamePanel.tileSize;
                j++;
            }
            j = startCol;
            i++;
            screenX = 0;
            screenY += gamePanel.tileSize;
        }
        int x = 0;
        while (x < 5){
            System.out.println("x=" + x);
            x++;
        }
    }

    // todo wenn player in der dunkelheit bummelt
    public void drawAroundPlayer(Graphics2D graphics2D){
        int playerCol = gamePanel.player.worldX / gamePanel.tileSize;
        int playerRow = gamePanel.player.worldY / gamePanel.tileSize;
        int centerCol = gamePanel.maxScreenCol / 2 - 2;
        int centerRow = gamePanel.maxScreenRow / 2 - 2;
        int tempCenterCol = centerCol;
        int tempCenterRow = centerRow;

        for (int i = playerRow - 1; i <= playerRow + 1; i++) {
            for (int j = playerCol - 1; j <= playerCol + 1; j++) {
                // Check constraints
                if (playerRow >= 0 && playerRow < int2DimList.size() &&
                        playerCol >= 0 && playerCol < int2DimList.get(0).size()) { //todo be cautious with 0
                    int tileID = int2DimList.get(i).get(j);
                    String tilePath = String.format("/tilesNumbered/%d.png", tileID+1);
                    try {
                        BufferedImage image = ImageIO.read(getClass().getResource(tilePath));
                        graphics2D.drawImage(image, tempCenterCol*gamePanel.tileSize, tempCenterRow*gamePanel.tileSize,
                                gamePanel.tileSize, gamePanel.tileSize, null);
                        if(tempCenterCol-centerCol == 2){
                            tempCenterCol = centerCol;
                            tempCenterRow++;
                        }
                        else{
                            tempCenterCol++;
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    //todo karte grenze
                }
            }
        }
    }


    private void loadMap(String pathMap){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(pathMap)))){
            int rows = 0;
            int cols = 0;
            String line;
            String [] words;
            ArrayList<Integer> intList = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                words = line.split(",");
                cols = words.length; // todo da ich keine jagged arrays in map habe, ist es in dem moment kein problem
                for(String str : words){
                    intList.add(Integer.parseInt(str));
                }
                int2DimList.add(new ArrayList<>(intList));
                rows++;
                intList.clear();
            }
            tiles = new BufferedImage[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    String tilePath = String.format("/tilesNumbered/%d.png", int2DimList.get(i).get(j) + 1);
                    tiles[i][j] = ImageIO.read(getClass().getResource(tilePath));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
