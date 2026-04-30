import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
            mainPanel.setOpaque(false);
            mainPanel.setPreferredSize(new Dimension(800, 600));
            mainPanel.setLayout(null);


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