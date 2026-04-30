import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class EnemyTracker {

    public static EnemyTracker instance = new EnemyTracker();

    private ArrayList<Enemy> eList = new ArrayList<Enemy>();

    public void registerEList(ArrayList<Enemy> eL){eList = eL;}

    public ArrayList<Rectangle2D.Double> getEnemyBounds(){

        if(eList.size() == 0)
            return null;

        ArrayList<Rectangle2D.Double> enemyBounds = new ArrayList<Rectangle2D.Double>();

        for(int i = 0;i < eList.size();i++){

            enemyBounds.add(eList.get(i).getBoundingRect());

        }

        return enemyBounds;

    }

    public boolean wasBossKilled(){
        Enemy boss = eList.get(eList.size() - 1);
        // return boss.getKilled();

        Enemy3Boss b = (Enemy3Boss)boss;

        return b.getKilled();
    }
    
}
