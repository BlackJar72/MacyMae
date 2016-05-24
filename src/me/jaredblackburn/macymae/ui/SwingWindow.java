package me.jaredblackburn.macymae.ui;

import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import me.jaredblackburn.macymae.ui.graphics.Graphic;

/**
 *
 * @author Jared Blackburn
 */
public class SwingWindow extends JFrame {
    public static final int baseFPS = 60;
    public static final int XSIZE = 800;
    public static final int YSIZE = (XSIZE * 2) / 3;
    
    private StartPanel startPanel;
    private GamePanel  gamePanel;    
    private IView      currentPanel;
    
    private static SwingWindow window;
    private final CardLayout mainLayout;
    private final JPanel     contentPane;
            
            
            
    KeyedInput userinput;
    
    
    private SwingWindow() {
        super();
        setSize(XSIZE, YSIZE);
        setTitle("Macy Mae's Eating Adventure");
        this.setIconImage(Graphic.registry.getGraphic("icon").getImage(1));
        setDefaultCloseOperation(EXIT_ON_CLOSE);        
        
        mainLayout = new CardLayout();
        contentPane = new JPanel(mainLayout);
        setContentPane(contentPane);
        
        startPanel = new StartPanel();
        startPanel.setBackground(Color.BLACK);
        contentPane.add(startPanel, "start");
        
        gamePanel = new GamePanel();
        gamePanel.setBackground(Color.BLACK);
        contentPane.add(gamePanel, "game");
        
        userinput = KeyedInput.getKeyedInput();
        addKeyListener(userinput);
        
        currentPanel = startPanel;        
        mainLayout.show(contentPane, "start");
        
        setLocationRelativeTo(null);
        requestFocus();
        setVisible(true);
    }
    
    
    public static SwingWindow getWindow() {
        if(window == null) {
            window = new SwingWindow();
        }
        return window;
    }
    
    
    public void draw() {
        currentPanel.draw();
    }
    
    
    public final void cleanup() {
        currentPanel = null;
        gamePanel = null;
        startPanel = null;
    }
    
    
    public void startGame() {
        currentPanel = gamePanel;        
        mainLayout.show(contentPane, "game");     
    }
    
    
    public void endGame() {
        currentPanel = startPanel;       
        mainLayout.show(contentPane, "start");
        startPanel.start();
    }
}
