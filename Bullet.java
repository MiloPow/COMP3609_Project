import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Bullet extends JPanel {
    
    private int initialX, initialY;
    protected int x, y, width, height, dy;
    protected int dir;
    protected Image sprite;
    private boolean isActive;

    // Bullets spawn out of screen and require activation to move. The player should only be able to activate 3 bullets at once

    public Bullet(){

        sprite = new ImageIcon("Images/Player Bullet.png").getImage();

        initialX = -10; initialY = 0;
        x = initialX; y = initialY;
        width = 8; height = 18;
        dy = 4; dir=-1;
        isActive = true;

    }

    public boolean isActive(){return isActive;}

    public void draw(Graphics2D imageContext){

        imageContext.drawImage(sprite, x, y, width, height, null);

    }

    public void move(){

        if(isActive)
            y = y + (dy * dir);

        if(isOutOfBounds() && isActive){
            deactivate();
            // System.out.println("Out of bounds and active. Deactivating...");
            // x = initialX;
            // y = initialY;
            // isActive = false;
            // System.out.println("X: " + Integer.toString(x) + " Y: " + Integer.toString(y));
        }

    }

    public void activate(int x, int y){
        this.x = x; this.y = y;
        isActive = true;
    }

    public void deactivate(){
        x = initialX;
        y = initialY;
        isActive = false;
    }

    public boolean isOutOfBounds(){

        if(x < 0 || x > 800 || y < 0 || y > 600)
            return true;

        return false;

    }

    public Rectangle2D.Double getBoundingRect(){
        return new Rectangle2D.Double(x, y, width, height);
    }

}
