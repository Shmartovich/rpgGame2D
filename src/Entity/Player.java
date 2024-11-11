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

    String direction = "right";
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
        screenX = gamePanel.screenWidth / 2 - gamePanel.tileSize / 2;
        screenY = gamePanel.screenHeight / 2 - gamePanel.tileSize / 2;
        worldX = 0;
        worldY = 0;

        speed = 5;
    }

    public void update(KeyHandler keyHandler) {
        if(keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed || keyHandler.upPressed){
            if(keyHandler.upPressed){
                direction = "up";
                worldY -= speed;
            }else if(keyHandler.downPressed){
                direction = "down";
                worldY += speed;
            }else if(keyHandler.leftPressed){
                direction = "left";
                worldX -= speed;
            }else if(keyHandler.rightPressed){
                direction = "right";
                worldX += speed;
            }

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

        switch (direction){
            case "up":
                imageToDraw = animationAdam.getFrames()[directionIndex * imagesInSheet + spriteNumber];
                break;
            case "down":
                imageToDraw = animationAdam.getFrames()[directionIndex * imagesInSheet + spriteNumber];
                break;
            case "left":
                imageToDraw = animationAdam.getFrames()[directionIndex * imagesInSheet + spriteNumber];
                break;
            case "right":
                imageToDraw = animationAdam.getFrames()[directionIndex * imagesInSheet + spriteNumber];
                break;
        }
        g2.drawImage(imageToDraw, screenX, screenY, gamePanel.tileSize, playerFrameHeight * 3, null);
    }
}
