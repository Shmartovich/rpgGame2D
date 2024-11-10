package Tile;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TileManager {
    GamePanel gamePanel;
    Tile [] tiles;
    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tiles = new Tile[9];
        for(int i = 0; i < tiles.length; i++) {
            tiles[i] = ImageIO.read(getClass().getResource());
        }
    }

    public void draw(Graphics2D graphics2D){
        int x = 0,y = 0;
        for(int i = 0; i < 9; i++){
            graphics2D.drawImage(tiles[i].image, x, y, gamePanel.tileSize,gamePanel.tileSize,null);
            x+=gamePanel.tileSize;
        }
    }

    public void loadMap(String path){
        try (BufferedReader reader = new BufferedReader(new FileReader(path))){
            String line;
            String [] lines;
            ArrayList<Integer> intList = new ArrayList<>();
            ArrayList<ArrayList<Integer>> int2DimList = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                lines = line.split(", ");
                for(String str : lines){
                    intList.add(Integer.parseInt(str));
                }
                int2DimList.add(intList);
                intList.clear();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
