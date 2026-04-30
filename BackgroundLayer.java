import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BackgroundLayer extends JPanel{

    private Image sprite1;
    private Image sprite2;

    private int sprite1x, sprite1y;
    private int sprite2x, sprite2y;
    private int dy, width, height;

    public BackgroundLayer(){
        
        dy = 2;
        width = 800; height = 600;

        sprite1x = 0; sprite1y = 0;
        sprite2x = 0; sprite2y = -height;

        sprite1 = new ImageIcon("Images/Small Stars BG.png").getImage();
        sprite2 = new ImageIcon("Images/Small Stars BG.png").getImage();

    }

    public void setImage(String fileName){

        sprite1 = new ImageIcon("Images/" + fileName).getImage();
        sprite2 = new ImageIcon("Images/" + fileName).getImage();

    }

    public void setDY(int newDY){

        dy = newDY;

    }

    public void move(){
        moveSprite1();
        moveSprite2();
    }

    public void moveSprite1(){
        sprite1y = sprite1y + dy;
        if(sprite1y >= 600){
            sprite1y=-height;
        }
    }

    public void moveSprite2(){
        sprite2y = sprite2y + dy;
        if(sprite2y >= 600)
            sprite2y=-height;
    }

    public void draw(Graphics2D imageContext){
        imageContext.drawImage(sprite1, sprite1x, sprite1y, width, height, this);
        imageContext.drawImage(sprite2, sprite2x, sprite2y, width, height, this);
    }
    
}
