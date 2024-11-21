package Main;

import Entity.Entity;
import Tile.Tile;
import Tile.TileManager;

import java.util.ArrayList;

public class CollisionChecker {
    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    public void checkTile(Entity entity){
        int collisionLeftX = entity.worldX + entity.collisionRectangle.x;
        int collisionRightX = collisionLeftX + entity.collisionRectangle.width;
        int collisionUpY = entity.worldY + entity.collisionRectangle.y;
        int collisionDownY = collisionUpY + entity.collisionRectangle.height;
        int collisionRow;
        int collisionCol;
        int tileID;
        switch(entity.direction){
            case "up":
                collisionRow = collisionUpY / gamePanel.tileSize;

                collisionCol = collisionLeftX / gamePanel.tileSize;
                tileID = gamePanel.tileManager.parsedMap.get(collisionRow).get(collisionCol);
                if(tileHasCollision(TileManager.tiles, tileID)){
                    entity.collisionOn = true;
                    printInfoCollidedTile(tileID);

                }
                // wahrscheinlich ist, dass der Tile daneben solid ist
                else{
                    collisionCol = collisionRightX / gamePanel.tileSize;
                    tileID = gamePanel.tileManager.parsedMap.get(collisionRow).get(collisionCol);
                    if(tileHasCollision(TileManager.tiles, tileID)){
                        entity.collisionOn = true;
                        printInfoCollidedTile(tileID);

                    }
                }
                break;
            case "down":
                collisionRow = collisionDownY / gamePanel.tileSize;

                collisionCol = collisionLeftX / gamePanel.tileSize;
                tileID = gamePanel.tileManager.parsedMap.get(collisionRow).get(collisionCol);
                if(tileHasCollision(TileManager.tiles, tileID)){
                    entity.collisionOn = true;
                    printInfoCollidedTile(tileID);

                }
                // wahrscheinlich ist, dass der Tile daneben solid ist
                else{
                    collisionCol = collisionRightX / gamePanel.tileSize;
                    tileID = gamePanel.tileManager.parsedMap.get(collisionRow).get(collisionCol);
                    if(tileHasCollision(TileManager.tiles, tileID)){
                        entity.collisionOn = true;
                        printInfoCollidedTile(tileID);

                    }
                }
                break;
            case "left":
                collisionCol = collisionLeftX / gamePanel.tileSize;

                collisionRow = collisionUpY / gamePanel.tileSize;
                tileID = gamePanel.tileManager.parsedMap.get(collisionRow).get(collisionCol);
                if(tileHasCollision(TileManager.tiles, tileID)){
                    entity.collisionOn = true;
                    printInfoCollidedTile(tileID);
                }
                // wahrscheinlich ist, dass der Tile daneben solid ist
                else{
                    collisionRow = collisionDownY / gamePanel.tileSize;
                    tileID = gamePanel.tileManager.parsedMap.get(collisionRow).get(collisionCol);
                    if(tileHasCollision(TileManager.tiles, tileID)){
                        entity.collisionOn = true;
                        printInfoCollidedTile(tileID);
                    }
                }
                break;
            case "right":
                collisionCol = collisionRightX / gamePanel.tileSize;

                collisionRow = collisionUpY / gamePanel.tileSize;
                tileID = gamePanel.tileManager.parsedMap.get(collisionRow).get(collisionCol);
                if(tileHasCollision(TileManager.tiles, tileID)){
                    entity.collisionOn = true;
                    printInfoCollidedTile(tileID);
                }
                // wahrscheinlich ist, dass der Tile daneben solid ist
                else{
                    collisionRow = collisionDownY / gamePanel.tileSize;
                    tileID = gamePanel.tileManager.parsedMap.get(collisionRow).get(collisionCol);
                    if(tileHasCollision(TileManager.tiles, tileID)){
                        entity.collisionOn = true;
                        printInfoCollidedTile(tileID);
                    }
                }
                break;
            default:
                break;
        }
    }
    private boolean tileHasCollision(ArrayList<Tile> tiles, int id){
        for(Tile tile : tiles){
            if(tile.id == id){
                return tile.collision;
            }
        }
        return false;
    }
    private void printInfoCollidedTile(int tileID){
        System.out.printf("Tile rechts ist solid, you cant go through %s\n", TileManager.findTile(tileID).name);
    }
}
