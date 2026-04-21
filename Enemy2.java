import javax.swing.ImageIcon;

public class Enemy2 extends Enemy{

    private EnemyBullet eBullet;

    private int spawnY;

    public Enemy2(){ // Needs health
        super();
        this.maxHealth = 3;
        this.currHealth = this.maxHealth;
        this.sprite = new ImageIcon("Images/Enemy 2.png").getImage();
        this.setScoreValue(300);

        width = 56; height = 40;
        spawnY = 0;
        collHealthReduction = 10; // Damage done to player
        collWithPlayerDamage = this.maxHealth;
        collWithBulletDamage = 1;
        eBullet = new EnemyBullet();
    }

    public EnemyBullet getBullet(){return eBullet;}

    @Override
    public void activate(int x, int y){
        super.activate(x, y);
        spawnY = y;
    }

    @Override
    public void move(){
        super.move();

        if(((this.y - spawnY) == 50) || ((this.y - spawnY) == 200) || ((this.y - spawnY) == 350)){
            eBullet.activate(x+25, y);
        }
    }
    
}
