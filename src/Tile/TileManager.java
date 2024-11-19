package Tile;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class TileManager {
    private final String pathToTilesWithoutCollision = "Tiles/tilesNumberedWithoutCollision/";
    private final String pathToTilesWithCollision = "Tiles/tilesNumberedWithCollision/";
    GamePanel gamePanel;
    ArrayList<ArrayList<Integer>> int2DimList = new ArrayList<>();
    static private final ArrayList<Tile> tiles = new ArrayList<Tile>();

    public TileManager(GamePanel gamePanel, String mapPath) {
        this.gamePanel = gamePanel;
        loadTiles();
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

                if(i < 0 ||  i >= int2DimList.size() - 1 || j < 0 || j >= int2DimList.get(i).size() - 1){
                    image = tiles.get(0).image;
                }
                else{
                    int imageID = int2DimList.get(i).get(j);
                    image = tiles.stream()
                            .filter(tile -> tile.id == imageID)
                            .findFirst()
                            .get().image;
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
                    String tilePath = String.format("/Tiles/tilesNumberedWithoutCollision/%d.png", tileID+1);
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTiles(){
        try (Stream<Path> paths = Files.walk(Paths.get("Tiles/tilesNumberedWithCollision"))) {
            paths
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .forEach(file -> createTilesArray(file, true));
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        try (Stream<Path> paths = Files.walk(Paths.get("Tiles/tilesNumberedWithoutCollision"))) {
            paths
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .forEach(file -> createTilesArray(file, false));
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    private static void createTilesArray(File file, boolean collision){
        try{
            Tile tile = new Tile(ImageIO.read(file), collision);
            tiles.add(tile);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
