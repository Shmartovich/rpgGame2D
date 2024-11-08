import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // Screen settings
    final int originalTileSize = 16; // 16x16 px
    final int scale = 3;
    final int tileSize = originalTileSize * scale;

    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = maxScreenCol * tileSize;
    final int screenHeight = maxScreenRow * tileSize;

    final int FPS = 60;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;

    // Player
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 6;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // draw offscreen
        this.addKeyListener(keyHandler);
        this.setFocusable(true); // to focus the GamePanel to receive key input
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() { // the thread will use this function on start()
        //todo wenn meine Variante nicht funkionieren würde -> #2 27:55
        while (gameThread != null) {
            long startTime = System.nanoTime();
            //update
            update();
            //draw
            repaint();
            while(true){
                long endTime = System.nanoTime();
                long delta = endTime - startTime;
                if(delta >= 1000000000/FPS){
                    //System.out.println("FPS: " + Math.round(1/(delta / 1000000000.0)));
                    break;
                }
            }
        }
    }
    public void update() {
        if(keyHandler.upPressed){
            playerY -= playerSpeed;
        }else if(keyHandler.downPressed){
            playerY += playerSpeed;
        }else if(keyHandler.leftPressed){
            playerX -= playerSpeed;
        }else if(keyHandler.rightPressed){
            playerX += playerSpeed;
        }
    }
    public void paintComponent(Graphics g) { // from JComponent
        super.paintComponent(g); // parent is JPanel

        Graphics2D g2 = (Graphics2D) g; // subclass of Graphics that is used for drawing
        g2.setColor(Color.blue);
        g2.fillRect(playerX, playerY, tileSize, tileSize);
        g2.dispose(); // optimisation
    }
}
