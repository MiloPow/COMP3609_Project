import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

public class EnemyBullet extends Bullet{

    public EnemyBullet(){
        super();
        this.sprite = new ImageIcon("Images/Enemy Bullet.png").getImage();
        this.dir = 1;
        dy = 8;
    }

    private void intersectsPlayer(){
        Rectangle2D.Double playerBounds = PlayerTracker.instance.getPlayerBounds();
        Rectangle2D.Double bulletBounds = getBoundingRect();

        if(bulletBounds.intersects(playerBounds)){

            PlayerTracker.instance.updatePlayerHealth(-3);
            deactivate();

        }

    }

    @Override
    public void move(){
        super.move();
        intersectsPlayer();
    }

    public Rectangle2D.Double getBoundingRect(){
        return new Rectangle2D.Double(x, y, width, height);
    }
    
}
