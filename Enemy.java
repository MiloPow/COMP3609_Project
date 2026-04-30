import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Enemy extends JPanel {
    
    private int initialX, initialY ,dx = 1, dy;
    protected int x, y, width, height, scoreValue, collHealthReduction, currHealth, maxHealth, collWithPlayerDamage, collWithBulletDamage;
    private boolean isActive;

    protected Image sprite;
    private Notification notif;

    public Enemy(){
        
        this.x = initialX; this.y = initialY;
        dy = 8;
        width = 44; height = 36;
        initialX = -width; initialY = -height;
        x=initialX; y = initialY;
        scoreValue = 100;
        isActive = false;
        collHealthReduction = 1;
        this.maxHealth = 1;
        currHealth = maxHealth;
        collWithPlayerDamage = maxHealth;
        collWithBulletDamage = maxHealth;

        sprite = new ImageIcon("Images/Enemy 1.png").getImage();
        notif = new Notification();

    }

    public int getWidth(){return width;}

    protected void setScoreValue(int scoreVal){
        scoreValue = scoreVal;
        notif.setText("+" + Integer.toString(scoreVal));
    }

    public boolean isActive(){return isActive;}

    public void activate(int x, int y){
        this.x = x; this.y = y;
        isActive = true;
    }

    public void deactivate(){
        x = initialX;
        y = initialY;
        isActive = false;
        currHealth = maxHealth;
        // WaveTracker.instance.enemyDeactivated();
    }

    public void draw(Graphics2D imageContext){

        imageContext.drawImage(sprite, x, y, width, height, this);

        // imageContext.drawString("+100", x, y);
        
        notif.draw(imageContext);

    }

    public void move(){

        if(isActive){

            y = y + dy;

            if(y == 600){
                deactivate();
            }

            intersectsPlayer();


        }

        notif.move();

    }
    
    public void move(int type){
        if(isActive){
            x = x + dx;

            if(x > 600){
                dx = -1;
            }
            if(x < 0){
                dx = 1;
            }

            intersectsPlayer();


        }

        notif.move();

    }

    private void intersectsPlayer(){
        Rectangle2D.Double playerBounds = PlayerTracker.instance.getPlayerBounds();
        Rectangle2D.Double enemyBounds = getBoundingRect();

        if(enemyBounds.intersects(playerBounds)){

            PlayerTracker.instance.updatePlayerHealth(-collHealthReduction);

            currHealth = currHealth - collWithPlayerDamage;
            if(currHealth <= 0)
                deactivate();

        }

        ArrayList<PlayerBullet> playerBullets = PlayerTracker.instance.getPlayerBullets();

        for(int i = 0;i<playerBullets.size();i++){

            PlayerBullet bullet = playerBullets.get(i);
            Rectangle2D.Double bulletBounds = bullet.getBoundingRect();

            if(enemyBounds.intersects(bulletBounds)){


                bullet.deactivate();
                
                currHealth = currHealth - collWithBulletDamage;

                if(currHealth <= 0){
                    
                    notif.activate(x, y);
                    ScoreTracker.instance.increaseScore(scoreValue);
                    deactivate();
                }

            }

        }
    }

    public Rectangle2D.Double getBoundingRect(){
        return new Rectangle2D.Double(x, y, width, height);
    }

}
