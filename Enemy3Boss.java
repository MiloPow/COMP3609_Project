import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Enemy3Boss extends Enemy{

    private EnemyBullet eBullet;

    private int spawnY;
    private int dx, dir;
    private boolean killed;

    public Enemy3Boss(){ // Needs health
        super();
        killed = false;
        initialX = -200; initialY=-200;
        this.x = initialX; this.y = initialY;
        
        dx = 8;
        dir = 1;
        this.maxHealth = 30;
        this.currHealth = this.maxHealth;
        this.sprite = new ImageIcon("Images/Boss.png").getImage();
        this.setScoreValue(1000);

        width = 200; height = 200;
        spawnY = 0;
        collHealthReduction = 10; // Damage done to player
        collWithPlayerDamage = this.maxHealth;
    }

    public boolean getKilled(){return killed;}

    @Override
    public void move(){

        if(this.isActive){

            x = x + (dx * dir);

            if(x <= 0 || x >= 600)
                dir = dir * -1;

            intersectsPlayer();


        }
        
        notif.move();
    }

    @Override
    protected void intersectsPlayer() {
        Rectangle2D.Double playerBounds = PlayerTracker.instance.getPlayerBounds();
        Rectangle2D.Double enemyBounds = getBoundingRect();

        if(enemyBounds.intersects(playerBounds)){

            PlayerTracker.instance.updatePlayerHealth(-collHealthReduction);

        }

        ArrayList<PlayerBullet> playerBullets = PlayerTracker.instance.getPlayerBullets();

        for(int i = 0;i<playerBullets.size();i++){

            PlayerBullet bullet = playerBullets.get(i);
            Rectangle2D.Double bulletBounds = bullet.getBoundingRect();

            if(enemyBounds.intersects(bulletBounds)){


                bullet.deactivate();
                
                currHealth = currHealth - collWithBulletDamage;

                if(currHealth <= 0){
                    killed = true;
                    notif.activate(x, y);
                    ScoreTracker.instance.increaseScore(scoreValue);
                    deactivate();
                }

            }
        }
    }
    
}
