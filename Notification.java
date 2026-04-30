import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Notification extends JPanel{

    int x, y, initialX, initialY, spawnX, spawnY, dy, dir, lifetime;
    boolean isActive;
    private Image gameOverImage;

    String text;

    public Notification(){
        initialX = -10; initialY = 0;
        x = initialX; y = initialY;
        dy = 1; dir= -1;
        text = "+100"; // Set to +100 as a default since the most common enemy uses this notification
        lifetime = 50;

        isActive = false;

    }

    public boolean isActive(){return isActive;}

    public void setText(String s){text=s;}

    public void activate(int x, int y){
        if(!isActive){
            spawnX = x; spawnY = y;
            this.x = spawnX; this.y = spawnY;
            isActive = true;
        }
    }

    public void deactivate(){
        x = initialX;
        y = initialY;
        isActive = false;
    }

    public void draw(Graphics2D imageContext){
        
        if(isActive)
            imageContext.drawString(text, x, y);
    }
    
    public void drawGameOver(Graphics2D g2, String imageString){
        
        gameOverImage = new ImageIcon(imageString).getImage();
        g2.drawImage(gameOverImage, 190, 50, 418, 418, this);
    }

    public void move(){
        if(isActive){
            y = y + (dy * dir);

            if(y <= (spawnY - 50) || y >= (spawnY + 50))
                deactivate();
        }
    }
    
}
