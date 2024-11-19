package Tile;

import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.UUID;

public class Tile {
    public static int counter = 0;
    public BufferedImage image;
    public boolean collision;
    public int id;

    Tile(BufferedImage image, boolean collision) {
        this.image = image;
        this.collision = collision;
        id = counter++;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "image=" + image +
                ", collision=" + collision +
                '}';
    }
}
