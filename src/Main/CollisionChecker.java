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
        int collisionTopY = entity.worldY + entity.collisionRectangle.y;
        int collisionBottomY = collisionTopY + entity.collisionRectangle.height;

        int collisionTopRow = (collisionTopY + entity.speed) / gamePanel.tileSize;
        int collisionBottomRow = (collisionBottomY) / gamePanel.tileSize;
        int collisionLeftCol = (collisionLeftX + entity.speed) / gamePanel.tileSize;
        int collisionRightCol =  (collisionRightX + entity.speed) / gamePanel.tileSize;

        int tile1_ID;
        int tile2_ID;
        switch(entity.direction){
            case "up":
                tile1_ID = gamePanel.tileManager.parsedMap.get(collisionTopRow).get(collisionLeftCol);
                tile2_ID = gamePanel.tileManager.parsedMap.get(collisionTopRow).get(collisionRightCol);
                if(tileHasCollision(TileManager.tiles, tile1_ID) || tileHasCollision(TileManager.tiles, tile2_ID)){
                    entity.collisionOn = true;
                    printInfoCollidedTile(tile1_ID);
                    printInfoCollidedTile(tile2_ID);
                }
                break;
            case "down":
                tile1_ID = gamePanel.tileManager.parsedMap.get(collisionBottomRow).get(collisionLeftCol);
                tile2_ID = gamePanel.tileManager.parsedMap.get(collisionBottomRow).get(collisionRightCol);
                if(tileHasCollision(TileManager.tiles, tile1_ID) || tileHasCollision(TileManager.tiles, tile2_ID)){
                    entity.collisionOn = true;
                    printInfoCollidedTile(tile1_ID);
                    printInfoCollidedTile(tile2_ID);
                }
                break;
            case "left":
                tile1_ID = gamePanel.tileManager.parsedMap.get(collisionTopRow).get(collisionLeftCol);
                tile2_ID = gamePanel.tileManager.parsedMap.get(collisionBottomRow).get(collisionLeftCol);
                if(tileHasCollision(TileManager.tiles, tile1_ID) || tileHasCollision(TileManager.tiles, tile2_ID)){
                    entity.collisionOn = true;
                    printInfoCollidedTile(tile1_ID);
                    printInfoCollidedTile(tile2_ID);
                }
                break;
            case "right":
                tile1_ID = gamePanel.tileManager.parsedMap.get(collisionTopRow).get(collisionRightCol);
                tile2_ID = gamePanel.tileManager.parsedMap.get(collisionBottomRow).get(collisionRightCol);
                if(tileHasCollision(TileManager.tiles, tile1_ID) || tileHasCollision(TileManager.tiles, tile2_ID)){
                    entity.collisionOn = true;
                    printInfoCollidedTile(tile1_ID);
                    printInfoCollidedTile(tile2_ID);
                }
                break;
            default:
                break;
        }
        if(entity.collisionOn){
            System.out.println("collisionTopRow-" + collisionTopRow);
            System.out.println("collisionBottomRow-" + collisionBottomRow);
            System.out.println("collisionLeftCol-" + collisionLeftCol);
            System.out.println("collisionRightCol-" + collisionRightCol);
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
        System.out.printf("Tile %s ist solid, you cant go through %s\n", gamePanel.player.direction, TileManager.findTile(tileID).name);

    }
}
