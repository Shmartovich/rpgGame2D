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

    Thread gameThread;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // draw offscreen
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        //update
        update();

        //draw
        repaint();
    }
    public void update() {

    }
    public void paintComponent(Graphics g) { // from JComponent
        super.paintComponent(g); // parent is JPanel

        Graphics2D g2 = (Graphics2D) g; // subclass of Graphics that is used for drawing
        g2.setColor(Color.blue);
        g2.fillRect(10, 10, tileSize, tileSize);
        g2.dispose(); // optimisation
    }
}
