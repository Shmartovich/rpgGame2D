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
    public ArrayList<ArrayList<Integer>> parsedMap = new ArrayList<>();
    static public final ArrayList<Tile> tiles = new ArrayList<Tile>();

    public TileManager(GamePanel gamePanel, String mapPath) {
        this.gamePanel = gamePanel;
        loadTiles();
        loadMap(mapPath);
    }

    public void draw(Graphics2D graphics2D){
       int worldCol = 0;
       int worldRow = 0;

       while(worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow){
           int tileID = parsedMap.get(worldRow).get(worldCol);

           int worldX = worldCol * gamePanel.tileSize;
           int worldY = worldRow * gamePanel.tileSize;
           int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenCenterX;
           int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenCenterY;

           if(worldX > gamePanel.player.worldX - gamePanel.player.screenCenterX &&
                   worldX < gamePanel.player.worldX + gamePanel.player.screenCenterX &&
                   worldY > gamePanel.player.worldY - gamePanel.player.screenCenterY &&
                   worldY < gamePanel.player.worldY + gamePanel.player.screenCenterY
           ){
               graphics2D.drawImage(findTile(tileID).image, screenX, screenY, gamePanel.tileSize,gamePanel.tileSize, null);
           }

           worldCol++;
           if(worldCol == gamePanel.maxWorldCol){
               worldRow++;
               worldCol = 0;
           }
       }
    }

    // todo wenn player in der dunkelheit bummelt (bewegung soll hüpfartig sein, oder ändere hier draw implemtierung)
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
                BufferedImage image;
                if (playerRow >= 0 && playerRow < parsedMap.size() &&
                        playerCol >= 0 && playerCol < parsedMap.get(0).size()) { //todo be cautious with 0
                    int imageID = parsedMap.get(i).get(j);
                        image = tiles.stream()
                                .filter(tile -> tile.id == imageID)
                                .findFirst()
                                .get().image;
                        graphics2D.drawImage(image, tempCenterCol*gamePanel.tileSize, tempCenterRow*gamePanel.tileSize,
                                gamePanel.tileSize, gamePanel.tileSize, null);
                        if(tempCenterCol-centerCol == 2){
                            tempCenterCol = centerCol;
                            tempCenterRow++;
                        }
                        else{
                            tempCenterCol++;
                        }

                }
                else{
                    //todo karte grenze
                        image = tiles.get(0).image; // get default solid Tile
                    graphics2D.drawImage(image, tempCenterCol*gamePanel.tileSize, tempCenterRow*gamePanel.tileSize,
                            gamePanel.tileSize, gamePanel.tileSize, null);
                }
            }
        }
    }


    private void loadMap(String pathMap){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(pathMap)))){
            String line;
            String [] words;
            ArrayList<Integer> intList = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                words = line.split(",");
                for(String str : words){
                    intList.add(Integer.parseInt(str));
                }
                parsedMap.add(new ArrayList<>(intList));
                intList.clear();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTiles(){
        try (Stream<Path> paths = Files.walk(Paths.get("/home/shmart/dimag/MyProjects/rpgGame2D/theGame/resources/Tiles/tilesNumberedWithCollision"))) {
            paths
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .forEach(file -> createTilesArray(file, true));
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        try (Stream<Path> paths = Files.walk(Paths.get("/home/shmart/dimag/MyProjects/rpgGame2D/theGame/resources/Tiles/tilesNumberedWithoutCollision"))) {
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
            // todo ich muss was mit Paths machen, damit ich absolute pfade nicht mehr benutze
            int lastCommaIndex = file.getPath().lastIndexOf("/");
            String tileName = file.getPath().substring(lastCommaIndex + 1);
            Tile tile = new Tile(ImageIO.read(file), collision, tileName);
            tiles.add(tile);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static Tile findTile(int id){
        for(Tile tile : tiles){
            if(tile.id == id){
                return tile;
            }
        }
        return tiles.get(0);
    }
}
