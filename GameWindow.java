import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class GameWindow extends JFrame implements ActionListener, KeyListener{

    JPanel mainPanel;

    private HashMap<String, Boolean> keysHeld;

    JPanel infoPanel;
        JLabel healthLabel;
        JLabel enemyLabel;

    JPanel buttPanel;
        JButton startButt;
        JButton pauseButt;
        JButton quitButt;
    
    GamePanel gamePanel;

    public GameWindow(){

        // Window attributes

        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        // setLayout(new OverlayLayout(getContentPane()));
        // setLayout(null);
        getContentPane().setBackground(Color.BLACK);
        setResizable(false);

        // Elements

        mainPanel = new JPanel();
            // mainPanel.setBackground(Color.WHITE);
            mainPanel.setOpaque(false);
            mainPanel.setPreferredSize(new Dimension(800, 600));
            // mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            mainPanel.setLayout(null);
            // mainPanel.setLayout(new OverlayLayout(getContentPane()));

        infoPanel = new JPanel();
            // infoPanel.setBackground(Color.RED);
            infoPanel.setOpaque(false);
            infoPanel.setPreferredSize(new Dimension(784, 50));
            infoPanel.setLayout(new GridLayout(2, 1));
            // infoPanel.setLayout(null);
            infoPanel.setBounds(10, 0, 100, 50);
            

            healthLabel = new JLabel("Health: 5");
            enemyLabel = new JLabel("Enemies: 10");

            infoPanel.add(healthLabel);
            infoPanel.add(enemyLabel);

            // mainPanel.add(infoPanel);

        buttPanel = new JPanel();
            // buttPanel.setBackground(Color.GREEN);
            buttPanel.setOpaque(false);
            buttPanel.setPreferredSize(new Dimension(787, 25));
            // buttPanel.setLayout(new GridLayout(1, 3));
            buttPanel.setLayout(null);
            buttPanel.setBounds(0, 50, 800, 50);

            startButt = new JButton("Start");
            pauseButt = new JButton("Pause");
            quitButt = new JButton("Quit");

            startButt.setBounds(0, 0, 100, 20);
            pauseButt.setBounds(100, 0, 100, 20);
            quitButt.setBounds(200, 0, 100, 20);

            startButt.addActionListener(this);
            pauseButt.addActionListener(this);
            quitButt.addActionListener(this);

            buttPanel.add(startButt);
            buttPanel.add(pauseButt);
            buttPanel.add(quitButt);

            // mainPanel.add(buttPanel);

        gamePanel = new GamePanel();
            gamePanel.addKeyListener(this);
            gamePanel.setBounds(0, 0, 800, 600);
            mainPanel.add(gamePanel);

        getContentPane().add(mainPanel);

        keysHeld = new HashMap<String, Boolean>();
        keysHeld.put("left", false);
        keysHeld.put("right", false);
        keysHeld.put("up", false);
        keysHeld.put("down", false);

        // Make visible and allow to close properly

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        gamePanel.start();
        gamePanel.requestFocus();
    }

    public void actionPerformed(ActionEvent e) {
        
        if(e.getActionCommand().equals("Start")){
            gamePanel.requestFocus();
            gamePanel.start();
        }
        else if(e.getActionCommand().equals("Quit"))
            System.exit(0);

    }

    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        gamePanel.keyPressed(e);
        // gamePanel.toggleKeyHeld(e);
    }

    public void keyReleased(KeyEvent e) {
        gamePanel.keyReleased(e);
        // gamePanel.toggleKeyHeld(e);
    }

}