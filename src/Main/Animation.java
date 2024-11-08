package Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Animation {
    private BufferedImage spriteSheet;  // Das gesamte Sprite-Sheet Bild
    private BufferedImage[] frames;     // Array für die einzelnen Frames der Animation
    private int frameWidth, frameHeight; // Breite und Höhe eines einzelnen Frames
    private int frameCount;             // Anzahl der Frames in der Animation
    private int currentFrame = 0;       // Der aktuelle Frame in der Animation
    private long frameDuration;         // Zeitdauer eines Frames (in Millisekunden)
    private long lastFrameTime;         // Zeitpunkt des letzten Framewechsels

    public Animation(String spriteSheetPath, int frameWidth, int frameHeight, int frameCount) {
        try {
            // Laden des Sprite-Sheets
            spriteSheet = ImageIO.read(getClass().getResource(spriteSheetPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.frameCount = frameCount;
        this.frames = new BufferedImage[frameCount];

        // Frames aus dem Sprite-Sheet ausschneiden
        for (int i = 0; i < frameCount; i++) {
            int x = (i * frameWidth) % spriteSheet.getWidth();
            frames[i] = spriteSheet.getSubimage(x, 0, frameWidth, frameHeight);
        }
    }

    public BufferedImage[] getFrames() {
        return frames;
    }

    //    public void update() {
//        // Wechselt den Frame, wenn die Dauer überschritten ist
//        long currentTime = System.currentTimeMillis();
//        if (currentTime - lastFrameTime >= frameDuration) {
//            currentFrame = (currentFrame + 1) % frameCount;  // Gehe zum nächsten Frame
//            lastFrameTime = currentTime;
//        }
//    }
//
//    public void draw(Graphics2D g2, int x, int y) {
//        // Zeichnet den aktuellen Frame an Position (x, y)
//        g2.drawImage(spriteSheet.getSubimage(x, y, frameWidth, frameHeight), x, y, null);
//    }
}
