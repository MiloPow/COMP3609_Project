import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class PlayerTracker {
    
    public static PlayerTracker instance = new PlayerTracker();

    private Player player;

    private SoundManager soundManager = new SoundManager();

    public void registerPlayer(Player p){
        player = p;
    }

    public String getPlayer(){
        if(player != null)
            return "Player returned";
        return "No player";
    }

    public Rectangle2D.Double getPlayerBounds(){return player.getBoundingRect();}

    public ArrayList<PlayerBullet> getPlayerBullets(){return player.getBullets();}

    public void updatePlayerHealth(int diff){
        player.updateHealth(diff);

        if(diff < 0 && Integer.parseInt(player.getHealth()) > 0){
            soundManager.playClip("hit");
        }
        else if(Integer.parseInt(player.getHealth()) <= 0){
            soundManager.playClip("explosion");
        }
    }

}
