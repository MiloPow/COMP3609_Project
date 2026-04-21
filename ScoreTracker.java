public class ScoreTracker {

    public static ScoreTracker instance = new ScoreTracker();

    public static int score = 0;

    public String getScore(){return Integer.toString(score);}

    public void increaseScore(int amount){
        score = score + amount;
    }
    
}
