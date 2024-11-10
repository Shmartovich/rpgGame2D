package Tile;

import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image;
    public boolean collision;

    Tile(BufferedImage image, boolean collision) {
        this.image = image;
        this.collision = collision;
    }
}
