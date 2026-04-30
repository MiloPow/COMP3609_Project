import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Player extends JPanel{
    
    private int x, y, dx, dy, width, height, health;
    private Image sprite;

    private HashMap<String, Boolean> keysHeld;

    private ArrayList<PlayerBullet> bullets;
    private int bulletAmount;

    public Player(int x, int y, JPanel parentPanel){

        setPreferredSize(new Dimension(50, 50));
        setBackground(Color.BLACK);

        // Init variables

        this.x = x; this.y = y;
        width = 60; height = 40;
        dx = 3; dy = 3;
        health = 20;

        initBullets();

        sprite = new ImageIcon("Images/Player.png").getImage();

        keysHeld = new HashMap<String, Boolean>();
        keysHeld.put("left", false);
        keysHeld.put("right", false);
        keysHeld.put("up", false);
        keysHeld.put("down", false);

    }

    public void updateHealth(int n){
        health = health + n;

        if(health > 20)
            health = 20;

        // Handle death and trigger game over

        if(health < 0)
            health = 0;
    }

    private void initBullets(){
        
        bulletAmount = 3;

        bullets = new ArrayList<PlayerBullet>();

        for(int i = 0;i < bulletAmount;i++){
            PlayerBullet b = new PlayerBullet();
            bullets.add(b);
        }
    }

    public void activateBullet(){

        boolean bulletActivated = false;

        for(int i = 0;i < bullets.size();i++){
            if(bulletActivated == false){
                if(!bullets.get(i).isActive()){
                    bullets.get(i).activate(x+25, y-10);
                    bulletActivated = true;
                    return;
                }
            }
        }

    }

    public ArrayList<PlayerBullet> getBullets(){return bullets;}

    public String getHealth(){return Integer.toString(health);}
    
    public void playerDie()
    {
        sprite = new ImageIcon("Images/explosion.gif").getImage();
    }
    public void draw(Graphics2D g2){

        g2.drawImage(sprite, x, y, width, height, this);

        // intersectsWithEnemy(); // Should really put into an update function
    }

    public void move(String dir){
        if(health > 0)
        {
            if(dir.equals("left")){
            x = x - dx;
            if(x < 5)
                x = 5;
            }
            else if(dir.equals("right")){
                x = x + dx;
                if((x + width) > 790)
                    x = 790 - width;
            }
            else if(dir.equals("up")){
                y = y - dy;
                if(y < 0)
                    y = 0;
            }
            else if(dir.equals("down")){
                y = y + dy;
                if((y + height) > 560)
                    y = 560 - height;
            }
        }
    }

    public void intersectsWithEnemy(){

        Rectangle2D.Double playerBounds = getBoundingRect();
        ArrayList<Rectangle2D.Double> enemyBoundsList = EnemyTracker.instance.getEnemyBounds();

        if(enemyBoundsList == null){

            System.out.println("enemyBoundsList empty. Returning...");
            return;

        }

        for(int i = 0; i < enemyBoundsList.size();i++){

            Rectangle2D.Double enemyBounds = enemyBoundsList.get(i);

            if(playerBounds.intersects(enemyBounds))
                System.out.println("Enemy collided with player");

        }

    }

    public Rectangle2D.Double getBoundingRect(){
        return new Rectangle2D.Double(x, y, width, height);
    }

}
