import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
    
    // private Image gameOverImage;
    private int frameCount;
    private Thread gameThread;
    private Boolean isRunning;
    private BufferedImage bufferedImage;
    private Image bgImage;
    // private ScoreTracker scoreTracker;
    private Player player;
    private Notification winLoseNotif;

    private ArrayList<Enemy> eList;
    private ArrayList<Enemy1> e1List;
    private ArrayList<Enemy2> e2List;
    private Enemy3Boss boss;
    private ArrayList<Bullet> bList;

    private ArrayList<Integer> spawnPoints;
    private ArrayList<ArrayList<Integer>> waveQueue;
    private int waveIndex;

    private HashMap<String, Boolean> keysHeld;
    
    private DisappearFX imageFX;

    public GamePanel(){
        
        setBackground(Color.BLUE);
        // setPreferredSize(new Dimension(785, 525));
        setPreferredSize(new Dimension(800, 600));
        setLayout(null);

        // Init Variables
        imageFX = new DisappearFX();
        bgImage = new ImageIcon("Images/bg.png").getImage();
        bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        player = new Player(347, 400, this);
        winLoseNotif = new Notification();

        PlayerTracker.instance.registerPlayer(player);

        bList = new ArrayList<Bullet>();

        ArrayList<PlayerBullet> playerBullets = player.getBullets();

        for(int i = 0;i < playerBullets.size();i++)
            bList.add(playerBullets.get(i));

        initSpawnPoints();

        initEnemies();
        loadWaveQueue();
        activateWave();

        keysHeld = new HashMap<String, Boolean>();
        keysHeld.put("left", false);
        keysHeld.put("right", false);
        keysHeld.put("up", false);
        keysHeld.put("down", false);

        frameCount = 0;


    }

    private void initSpawnPoints(){

        spawnPoints = new ArrayList<Integer>();
        spawnPoints.add(100);
        spawnPoints.add(200);
        spawnPoints.add(300);
        spawnPoints.add(400);
        spawnPoints.add(500);
        spawnPoints.add(600);
        spawnPoints.add(700);
    
    }

    public void loadWaveQueue(){

        File f = new File("EnemyLayout.txt");

        String waveArrayString;
        char[] charArray = {};

        waveQueue = new ArrayList<ArrayList<Integer>>();
        waveIndex = -1;
        
        try{

            Scanner s = new Scanner(f);

            while(s.hasNext()){
                waveArrayString = s.nextLine();

                charArray = waveArrayString.toCharArray();
                
                ArrayList<Integer> waveArray = new ArrayList<Integer>();

                for(int i = 0;i < 7;i++){
                
                    waveArray.add(Integer.parseInt(Character.toString(charArray[i])));

                }

                // Add to waveQueue before emptying

                waveQueue.add(waveArray);

            }

            s.close();

        }catch(Exception e){
            e.printStackTrace();
        }

    }


    public void activateWave(){

        waveIndex++;

        if(waveQueue.size() == 0 || waveIndex >= waveQueue.size()){
            return;
        }

        ArrayList<Integer> activeWave = waveQueue.get(waveIndex);

        for(int i = 0; i < 7;i++){

            if(activeWave.get(i) == 1){
                Enemy e = e1List.get(i);
                e.activate(spawnPoints.get(i), -36);
            }
            else if(activeWave.get(i) == 2){
                Enemy e = e2List.get(i);
                e.activate(spawnPoints.get(i), -36);
            }
            else if(activeWave.get(i) == 3){
                boss.activate(300, 10);
                
            }

        }

    }

    public void initEnemies(){

        eList = new ArrayList<Enemy>();
        e1List = new ArrayList<Enemy1>();
        e2List = new ArrayList<Enemy2>();
        boss = new Enemy3Boss();
        
        for(int i = 0;i < spawnPoints.size();i++){
            // Enemy1 e = new Enemy1();
            Enemy1 e1 = new Enemy1();
            Enemy2 e2 = new Enemy2();
            e1List.add(e1);
            e2List.add(e2);
            eList.add(e1);
            eList.add(e2);
            bList.add(e2.getBullet());
        }

        eList.add(boss);
        EnemyTracker.instance.registerEList(eList);
    }

    private boolean enemiesInactive(){
        
        for(int i = 0;i < e1List.size();i++){
            if(e1List.get(i).isActive())
                return false;
        }
        
        for(int i = 0;i < e2List.size();i++){
            if(e2List.get(i).isActive())
                return false;
        }
        
        return true;
    }

    public void start(){

        gameThread = new Thread(this);
        gameThread.start();

    }
    
    public void draw(){

        Graphics2D imageContext = (Graphics2D)bufferedImage.getGraphics();

        imageContext.drawImage(bgImage, 0, 0, 1600, 1000, this);
    
        drawBullets(imageContext);
        
        drawEnemies(imageContext);

        player.draw(imageContext);
        
        
        if(EnemyTracker.instance.wasBossKilled())
        {
            winLoseNotif.drawGameOver(imageContext, "Images/YouWin.png");
        }

        if (Integer.parseInt(player.getHealth()) <= 0)
        {
            player.playerDie();
            winLoseNotif.drawGameOver(imageContext, "Images/GameOver.png");
        }

        Font font = new Font("Courier", Font.PLAIN, 18);

        imageContext.setColor(Color.WHITE);
        imageContext.setFont(font);
        imageContext.drawString("Health: " + player.getHealth(), 10, 20);
        imageContext.drawString("Score: " + ScoreTracker.instance.getScore(), 10, 50);
        
        imageFX.draw(imageContext);
        
        imageContext.dispose();


        Graphics2D g2 = (Graphics2D)getGraphics();
    
        g2.drawImage(bufferedImage, 0, 0, this);
        g2.dispose();
    }

    public void update(){
        processInput();

        if(frameCount % 7 == 0){
            moveEnemies();
            moveBullets();
        }
        
        moveBullets();
        draw();
        updateFrameCount();

        // When a wave of enemies deactivate we need to activate the next wave in queue

        if(enemiesInactive() && waveIndex < waveQueue.size()){
            activateWave();
        }

    }

    public void run(){
        int counter = 0;
        try{
            isRunning = true;
            while(isRunning){
                update();
                Thread.sleep(2);
                if(Integer.parseInt(player.getHealth()) <= 0)
                {
                    counter = counter + 1;
                    Graphics2D imageContext = (Graphics2D)bufferedImage.getGraphics();
                    imageContext.drawImage(bgImage, 0, 0, 1600, 1000, this);
                    imageFX.draw(imageContext);
                    imageFX.update();
                    if (counter > 120)
                    {
                        isRunning = false;
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void drawEnemies(Graphics2D imageContext){
        for(int i = 0; i < eList.size();i++)
            eList.get(i).draw(imageContext);
    }

    public void drawBullets(Graphics2D imageContext){
        for(int i = 0;i < bList.size();i++){
            bList.get(i).draw(imageContext);
        }
    }

    public void moveEnemies(){
        for(int i = 0; i < eList.size();i++)
            eList.get(i).move();
    }

    public void moveBullets(){
        for(int i = 0;i < bList.size();i++){
            bList.get(i).move();
        }
    }

    private void updateFrameCount(){
        frameCount++;
        if(frameCount > 60)
            frameCount = 0;
    }

    public void processInput(){

        // Player movement

        if(keysHeld.get("left"))
            player.move("left");
        else if(keysHeld.get("right"))
            player.move("right");
        else if(keysHeld.get("up"))
            player.move("up");
        else if(keysHeld.get("down"))
            player.move("down");
        

    }

    public void keyPressed(KeyEvent e){

        if(e.getKeyCode() == KeyEvent.VK_Z){

            player.activateBullet();

        }

        if(e.getKeyCode() == KeyEvent.VK_LEFT){

            if(keysHeld.get("left") == false)
                keysHeld.put("left", true);

        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT){

            if(keysHeld.get("right") == false)
                keysHeld.put("right", true);
        }
        else if(e.getKeyCode() == KeyEvent.VK_UP){

            if(keysHeld.get("up") == false)
                keysHeld.put("up", true);
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN){

            if(keysHeld.get("down") == false)
                keysHeld.put("down", true);
        }

    }

    public void keyReleased(KeyEvent e){

        if(e.getKeyCode() == KeyEvent.VK_LEFT){

            if(keysHeld.get("left") == true)
                keysHeld.put("left", false);

        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT){

            if(keysHeld.get("right") == true)
                keysHeld.put("right", false);
        }
        else if(e.getKeyCode() == KeyEvent.VK_UP){

            if(keysHeld.get("up") == true)
                keysHeld.put("up", false);
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN){

            if(keysHeld.get("down") == true)
                keysHeld.put("down", false);
        }

    }

}
