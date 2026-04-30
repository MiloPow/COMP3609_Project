
public class Enemy1 extends Enemy {

    public Enemy1(){
        super();
        this.maxHealth = 1;
        this.currHealth = this.maxHealth;
        collHealthReduction = 5;
        collWithPlayerDamage = this.maxHealth;
        collWithBulletDamage = 1;
    }

}
