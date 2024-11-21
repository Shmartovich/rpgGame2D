package Entity;

import Main.Animation;
import Main.GamePanel;
import Main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;
    int spriteNumber = 0;
    int timerToChangeSprite = 0; // in frames

    int directionIndex = 0; // dependency on direction 0=right, 1=up, 2=left 3=down
    //todo rename player*
    final int playerFrameWidth = 16; // for correct cut of frames sheet
    final int playerFrameHeight = 24;
    final int playerAnimationRunFrames = 24;
    Animation animationAdam = new Animation("/Player/adam_run.png", playerFrameWidth, playerFrameHeight, playerAnimationRunFrames);
    public Player(GamePanel gp, KeyHandler kh){
        gamePanel = gp;
        keyHandler = kh;
        setDefaultValues();
    }
    public void setDefaultValues(){
        screenCenterX = gamePanel.screenWidth / 2 - gamePanel.tileSize / 2;
        screenCenterY = gamePanel.screenHeight / 2 - gamePanel.tileSize / 2;
        worldX = 48;
        worldY = 48;
        collisionRectangle = new Rectangle();
        collisionRectangle.x = 8;
        collisionRectangle.y = 16;
        collisionRectangle.width = playerFrameWidth * gamePanel.scale - 16;
        collisionRectangle.height = playerFrameHeight * gamePanel.scale - 16;
        speed = 8;
    }

    public void update(KeyHandler keyHandler) {
        if(keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed || keyHandler.upPressed){
            if(keyHandler.upPressed){
                direction = "up";
            }else if(keyHandler.downPressed){
                direction = "down";
            }else if(keyHandler.leftPressed){
                direction = "left";
            }else if(keyHandler.rightPressed){
                direction = "right";
            }

            // ACTIVATE COLLISION ON PLAYER IF ON SOLID TILE
            collisionOn = false;
            System.out.printf("ich stehe hier: col %d, row %d\n", worldX/ gamePanel.tileSize, worldY/ gamePanel.tileSize);
            printCollisionCoordinates();
            gamePanel.collisionChecker.checkTile(this);
            if(collisionOn == false){
                switch (direction){
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                    default:
                        break;
                }
            }

            //todo soll ich die animation nicht abspielen wenn player sich nicht bewegen kann?
            timerToChangeSprite++;
            if(timerToChangeSprite > 10){
                timerToChangeSprite = 0;
                if (spriteNumber == 5){
                    spriteNumber = 0;
                }
                spriteNumber++;
            }
        }
        else{
            //todo idle animation
            spriteNumber = 0;
        }

    }

    public void draw(Graphics2D g2){
        BufferedImage imageToDraw = animationAdam.getFrames()[0];
        final int imagesInSheet = 6; // animation of running has 6 frames to each direction

        switch (direction){
            case "up":
                directionIndex = 1;
                break;
            case "down":
                directionIndex = 3;
                break;
            case "left":
                directionIndex = 2;
                break;
            case "right":
                directionIndex = 0;
                break;
            default:
                break;
        }
        imageToDraw = animationAdam.getFrames()[directionIndex * imagesInSheet + spriteNumber];
        g2.drawImage(imageToDraw, screenCenterX, screenCenterY, gamePanel.tileSize, playerFrameHeight * gamePanel.scale, null);
    }

    private void printCollisionCoordinates(){
        System.out.printf("Player coordinates: x-%d,y-%d,x+width-%d,y+height-%d\n",
                this.worldX,
                this.worldY,
                this.worldX + playerFrameWidth * gamePanel.scale,
                this.worldY + playerFrameHeight * gamePanel.scale);
        System.out.printf("Collision coordinates: x-%d,y-%d,x+width-%d,y+height-%d\n",
                this.collisionRectangle.x + worldX,
                this.collisionRectangle.y + worldY,
                this.collisionRectangle.x + worldX + collisionRectangle.width,
                this.collisionRectangle.y + worldY + collisionRectangle.height);
    }
}
