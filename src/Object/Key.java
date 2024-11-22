package Object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Key extends GameObject {
    public Key() {
        name = "Key";
        try{
            ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
        }
        catch(IOException e){
            System.out.println("Key image not found");
            e.printStackTrace();
        }
    }
}
