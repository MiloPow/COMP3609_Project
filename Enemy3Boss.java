import javax.swing.ImageIcon;

public class Enemy3Boss extends Enemy{

    private EnemyBullet eBullet;

    private int spawnY;

    public Enemy3Boss(){ // Needs health
        super();
        this.maxHealth = 30;
        this.currHealth = this.maxHealth;
        this.sprite = new ImageIcon("Images/Boss.png").getImage();
        this.setScoreValue(1);

        width = 200; height = 200;
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
    
    public int getHealth()
    {
        return this.currHealth;
    }

    @Override
    public void move(){
        super.move();
        eBullet.activate(x, y + 40);
    }
    
}
