import javax.swing.*;

public class GamePanel extends JPanel {
    final int originalTileSize = 16; // 16x16 px
    final int scale = 3;
    final int tileSize = originalTileSize * scale;

    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
}
