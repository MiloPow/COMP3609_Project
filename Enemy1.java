// import java.awt.Graphics2D;
// import java.awt.Image;
// import java.awt.geom.Rectangle2D;
// import java.util.ArrayList;

// import javax.swing.ImageIcon;
// import javax.swing.JPanel;

public class Enemy1 extends Enemy {

    public Enemy1(){
        super();
        this.maxHealth = 1;
        this.currHealth = this.maxHealth;
        collHealthReduction = 5;
        collWithPlayerDamage = this.maxHealth;
        collWithBulletDamage = 1;
    }
    
    // private int x, y, initialX, initialY, dy, width, height, scoreValue;
    // // private boolean movingRight;
    // private boolean isActive;

    // private Image sprite;
    // private Notification notif;

    // public Enemy1(){
        
    //     this.x = initialX; this.y = initialY;
    //     // dx = 6; 
    //     dy = 2;
    //     width = 44; height = 36;
    //     initialX = -width; initialY = -height;
    //     x=initialX; y = initialY;
    //     scoreValue = 100;
    //     // movingRight = true;
    //     isActive = false;

    //     sprite = new ImageIcon("Images/Enemy 1.png").getImage();
    //     notif = new Notification();

    // }

    // public int getWidth(){return width;}

    // public boolean isActive(){return isActive;}

    // public void activate(int x, int y){
    //     this.x = x; this.y = y;
    //     isActive = true;
    // }

    // public void deactivate(){
    //     x = initialX;
    //     y = initialY;
    //     isActive = false;
    //     // WaveTracker.instance.enemyDeactivated();
    // }

    // public void draw(Graphics2D imageContext){

    //     imageContext.drawImage(sprite, x, y, width, height, this);

    //     // imageContext.drawString("+100", x, y);
        
    //     notif.draw(imageContext);

    // }

    // public void move(){

    //     if(isActive){

    //         // updateX();
    //         y = y + dy;

    //         if(y == 600){
    //             deactivate();
    //             // y = - height;
    //         }

    //         intersectsPlayer();


    //     }

    //     notif.move();

    // }

    // private void intersectsPlayer(){
    //     Rectangle2D.Double playerBounds = PlayerTracker.instance.getPlayerBounds();
    //     Rectangle2D.Double enemyBounds = getBoundingRect();

    //     if(enemyBounds.intersects(playerBounds)){

    //         deactivate();

    //     }

    //     ArrayList<Bullet> playerBullets = PlayerTracker.instance.getPlayerBullets();

    //     for(int i = 0;i<playerBullets.size();i++){

    //         Bullet bullet = playerBullets.get(i);
    //         Rectangle2D.Double bulletBounds = bullet.getBoundingRect();

    //         if(enemyBounds.intersects(bulletBounds)){

    //             notif.activate(x, y);
    //             y = 0 - height;
    //             ScoreTracker.instance.increaseScore(scoreValue);

    //             bullet.deactivate();
    //             deactivate();

    //         }

    //     }
    // }

    // private Rectangle2D.Double getBoundingRect(){
    //     return new Rectangle2D.Double(x, y, width, height);
    // }

}
