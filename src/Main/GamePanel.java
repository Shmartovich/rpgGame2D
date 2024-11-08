package Main;

import Entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // Screen settings
    private final int originalTileSize = 16; // 16x16 px
    private final int scale = 3;
    public final int tileSize = originalTileSize * scale;

    private final int maxScreenCol = 16;
    private final int maxScreenRow = 12;
    private final int screenWidth = maxScreenCol * tileSize;
    private final int screenHeight = maxScreenRow * tileSize;

    private final int FPS = 60;

    KeyHandler keyHandler = new KeyHandler();
    Player player = new Player(this, keyHandler);
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
        this.setFocusable(true); // to focus the Main.GamePanel to receive key input
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
        player.update(keyHandler);
    }
    public void paintComponent(Graphics g) { // from JComponent
        super.paintComponent(g); // parent is JPanel

        Graphics2D g2 = (Graphics2D) g; // subclass of Graphics that is used for drawing

        player.draw(g2);



        g2.dispose(); // optimisation
    }
}
