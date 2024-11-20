package Entity;

import java.awt.*;

public class Entity {
    public int screenX;
    public int screenY;
    public int worldX;
    public int worldY;

    public String direction = "right";
    public int speed;

    public Rectangle collisionRectangle;
    public boolean collisionOn = false;
}
