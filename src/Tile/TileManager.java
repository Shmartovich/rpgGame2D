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
    GamePanel gamePanel;
    ArrayList<ArrayList<Integer>> int2DimList = new ArrayList<>();
    ArrayList<ArrayList<BufferedImage>> tiles = new ArrayList<>();
    public TileManager(GamePanel gamePanel, String mapPath) {
        this.gamePanel = gamePanel;
        loadMap(mapPath);
    }

    public void draw(Graphics2D graphics2D){
        int playerCol = Player.x / gamePanel.tileSize;
        int playerRow = Player.y / gamePanel.tileSize;
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
                    String tilePath = String.format("/tilesNumbered/%d.png", tileID);
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

    private void prepareImages(BufferedImage image, int playerCol, int playerRow){

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
