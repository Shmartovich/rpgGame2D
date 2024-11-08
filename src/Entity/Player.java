package Entity;

import Main.Animation;
import Main.GamePanel;
import Main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;

    String direction = "right";
    int directionIndex = 0; // dependency on direction 0=right, 1=up, 2=left 3=down
    Animation animationAdam = new Animation("/res/Player/adam_run.png", 16,24,24, 60);
    public Player(GamePanel gp, KeyHandler kh){
        gamePanel = gp;
        keyHandler = kh;

        setDefaultValues();
    }
    public void setDefaultValues(){
        x = 100;
        y = 100;
        speed = 15;
    }

    public void update(KeyHandler keyHandler) {
        if(keyHandler.upPressed){
            direction = "up";
            y -= speed;
        }else if(keyHandler.downPressed){
            direction = "down";
            y += speed;
        }else if(keyHandler.leftPressed){
            direction = "left";
            x -= speed;
        }else if(keyHandler.rightPressed){
            direction = "right";
            x += speed;
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
                imageToDraw = animationAdam.getFrames()[directionIndex * imagesInSheet];
                break;
            case "down":
                imageToDraw = animationAdam.getFrames()[directionIndex * imagesInSheet];
                break;
            case "left":
                imageToDraw = animationAdam.getFrames()[directionIndex * imagesInSheet];
                break;
            case "right":
                imageToDraw = animationAdam.getFrames()[directionIndex * imagesInSheet];
                break;
        }
        g2.drawImage(imageToDraw, x,y, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
